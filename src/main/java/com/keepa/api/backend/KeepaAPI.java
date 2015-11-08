package com.keepa.api.backend;

import com.keepa.api.backend.exceptions.KeepaAPIException;
import com.keepa.api.backend.structs.AmazonLocale;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.keepa.api.backend.helper.BasicNameFactory;
import com.keepa.api.backend.structs.KeepaProductResponse;
import com.keepa.api.backend.structs.KeepaResponseStatus;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

final public class KeepaAPI {
	/**
	 * Thread pool size determines degree of asynchronization.
	 */
	final private ExecutorService executorDeferred;
	final private ExecutorService executorRetry;

	final private String accessKey;
	final private String userAgent;

    /**
     * @param key     Your private API Access Token
     * @param threads Thread pool size determines degree of asynchronization. Higher thread count allows more requests in parallel to be made. Default 1
     */
    public KeepaAPI(String key, int threads) {
        this.accessKey = key;
        String apiVersion = getClass().getPackage().getImplementationVersion();
        if (apiVersion != null) {
            userAgent = "KEEPA-JAVA Framework-" + apiVersion;
        } else {
            userAgent = "KEEPA-JAVA Framework-";
        }

        executorDeferred = Executors.newFixedThreadPool(threads, new BasicNameFactory("KeepaAPI-%d"));
        executorRetry = Executors.newFixedThreadPool(threads, new BasicNameFactory("KeepaAPI-RetryScheduler"));
    }

	/**
	 * @param key Your private API Access Token
	 */
	public KeepaAPI(String key) {
		this(key, 2);
	}

	private String ASINsToCsv(String asins[]) throws KeepaAPIException {
		if (asins.length < 1) {
			throw new KeepaAPIException("ASIN list too short (min 1)");
		} else if (asins.length > 100) {
			throw new KeepaAPIException("ASIN list too long (max 100");
		}
		StringBuilder buff = new StringBuilder();
		String sep = "";
		for (String asin : asins) {
			buff.append(sep);
			buff.append(asin);
			sep = ",";
		}
		return buff.toString();
	}

	private Promise<KeepaProductResponse, KeepaProductResponse, Void> getProductData(final AmazonLocale domainID, final String asinCSV) {
		Deferred<KeepaProductResponse, KeepaProductResponse, Void> d = new DeferredObject<>();

		executorDeferred.execute(() -> {
			long responseTime = System.nanoTime();
			KeepaProductResponse response;


			try {
				URL obj = new URL("https://dyn.keepa.com/v3/api/product/?key=" + accessKey + "&domain=" + domainID.ordinal() + "&asin=" + asinCSV);
				HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
				con.setUseCaches(false);
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", this.userAgent);
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("Accept-Encoding", "gzip");

				int responseCode = con.getResponseCode();

				switch (responseCode) {
					case 200: // everything ok
						try (InputStream is = con.getInputStream();
							 GZIPInputStream gis = new GZIPInputStream(is)) {
							JsonReader reader = new JsonReader(new InputStreamReader(gis, "UTF-8"));
							Gson gson = new Gson();
							response = gson.fromJson(reader, KeepaProductResponse.class);

							response.status = KeepaResponseStatus.OK;
						} catch (Exception e) {
							response = KeepaProductResponse.REQUEST_FAILED;
							e.printStackTrace();
						}
						break;
					case 503: // throttled
						try (InputStream is = con.getErrorStream();
							 GZIPInputStream gis = new GZIPInputStream(is)) {
							JsonReader reader = new JsonReader(new InputStreamReader(gis, "UTF-8"));
							Gson gson = new Gson();
							response = gson.fromJson(reader, KeepaProductResponse.class);
							response.status = KeepaResponseStatus.NOT_ENOUGH_TOKEN;
						} catch (Exception e) {
							response = KeepaProductResponse.REQUEST_FAILED;
							e.printStackTrace();
						}
						break;
					case 400:
						response = KeepaProductResponse.REQUEST_REJECTED;
						break;
					default:
						response = KeepaProductResponse.REQUEST_FAILED;
						break;
				}
			} catch (IOException e) {
				response = KeepaProductResponse.REQUEST_FAILED;
			}

			response.requestTime = (System.nanoTime() - responseTime) / 1000000;
			if (response.status == KeepaResponseStatus.OK)
				d.resolve(response);
			else
				d.reject(response);
		});

		return d.promise();
	}


	/**
	 * Issue a product request to the Keepa Price Data API. Will report failure if the request could not be fulfilled the first try.
	 *
	 * @param domainID Amazon locale of the product {@link AmazonLocale}
	 * @param asins    ASINs to request, must contain between 1 and 100 ASINs.
	 * @return Promise for {@link KeepaProductResponse}
	 * @throws KeepaAPIException
	 */
	final public Promise<KeepaProductResponse, KeepaProductResponse, Void> makeProductRequest(final AmazonLocale domainID, final String asins[]) throws KeepaAPIException {
		String asinCSV = ASINsToCsv(asins);
		return getProductData(domainID, asinCSV);
	}

	public final int maxDelay = 60000;

	/**
	 * Issue a product request to the Keepa Price Data API.
	 * If your API contigent is depleted, this method will retry the request as soon as there are new tokens available. May take minutes.
	 * Will fail it the request failed too many times.
	 *
	 * @param domainID Amazon locale of the product {@link AmazonLocale}
	 * @param asins    ASINs to request, must contain between 1 and 100 ASINs.
	 * @return Promise for {@link KeepaProductResponse}
	 * @throws KeepaAPIException
	 */
	final public Promise<KeepaProductResponse, KeepaProductResponse, Void> makeProductRequestWithRetry(final AmazonLocale domainID, final String asins[]) throws KeepaAPIException {
		Deferred<KeepaProductResponse, KeepaProductResponse, Void> deffered = new DeferredObject<>();
		String asinCSV = ASINsToCsv(asins);
		AtomicInteger expoDelay = new AtomicInteger(0);

		executorRetry.execute(() -> {
			int retry = 0;
			KeepaProductResponse[] lastResponse = {null};
			final boolean[] solved = {false};

			try {
				int delay = 0;

				while (!solved[0]) {
					if (lastResponse[0] != null && lastResponse[0].status == KeepaResponseStatus.NOT_ENOUGH_TOKEN && lastResponse[0].refillIn > 0) {
						delay = lastResponse[0].refillIn + 100;
					}

					if (retry > 0)
						Thread.sleep(delay);

					Promise<KeepaProductResponse, KeepaProductResponse, Void> p1 = getProductData(domainID, asinCSV);
					p1
							.done(result -> {
								deffered.resolve(result);
								solved[0] = true;
								expoDelay.set(0);
							})
							.fail(result -> {
								lastResponse[0] = result;
								switch (result.status) {
									case FAIL:
									case NOT_ENOUGH_TOKEN: // retry
										break;
									default:
										deffered.reject(result);
										solved[0] = true;
								}
							})
							.waitSafely();

					if(p1.isRejected()){
						retry++;
						delay = expoDelay.getAndUpdate(operand -> Math.min(2 * operand + 100, maxDelay));
					}
				}
			} catch (InterruptedException e) {
				deffered.reject(null);
			}
		});

		return deffered.promise();
	}
}
