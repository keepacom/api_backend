package com.keepa.api.backend;

import com.google.gson.stream.JsonReader;
import com.keepa.api.backend.helper.BasicNameFactory;
import com.keepa.api.backend.structs.Request;
import com.keepa.api.backend.structs.Response;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import static com.keepa.api.backend.helper.Utility.gson;
import static com.keepa.api.backend.helper.Utility.urlEncodeUTF8;

public final class KeepaAPI {
	/**
	 * Thread pool size determines degree of asynchronization.
	 */
	final private ExecutorService executorDeferred;
	final private ExecutorService executorRetry;

	final private String accessKey;
	final private String userAgent;
	final private int maxDelay = 60000;

	public enum ResponseStatus {
		PENDING, OK, FAIL, NOT_ENOUGH_TOKEN, REQUEST_REJECTED, NOT_FOUND, PAYMENT_REQUIRED, METHOD_NOT_ALLOWED, INTERNAL_SERVER_ERROR
	}

	/**
	 * @param key     Your private API Access Token
	 * @param threads Thread pool size determines degree of asynchronization. Higher thread count allows more requests in parallel to be made. Default 4
	 */
	public KeepaAPI(String key, int threads) {
		this(key, Executors.newFixedThreadPool(threads, new BasicNameFactory("KeepaAPI-%d")), Executors.newFixedThreadPool(threads, new BasicNameFactory("KeepaAPI-RetryScheduler")));
	}

	/**
	 * @param key     Your private API Access Token
	 * @param executorRetry provide a custom executor service for request retry attempts
	 * @param executorDeferred provide a custom executor service for deferred request processing
	 */
	public KeepaAPI(String key, ExecutorService executorRetry, ExecutorService executorDeferred) {
		this.accessKey = key;
		String apiVersion = getClass().getPackage().getImplementationVersion();
		if (apiVersion != null) {
			userAgent = "KEEPA-JAVA Framework-" + apiVersion;
		} else {
			userAgent = "KEEPA-JAVA Framework-";
		}

		this.executorDeferred = executorDeferred;
		this.executorRetry = executorRetry;
	}

	/**
	 * @param key Your private API Access Token
	 */
	public KeepaAPI(String key) {
		this(key, 4);
	}

	/**
	*  Shutdown internal executor services (thread pools)
	*  @param shutdownNow if true issue a shutdownNow()
	*/
	public void shutdown(boolean shutdownNow) {
		if(shutdownNow){
			executorDeferred.shutdownNow();
			executorRetry.shutdownNow();
		} else {
			executorDeferred.shutdown();
			executorRetry.shutdown();
		}
	}

	/**
	 * Issue a request to the Keepa Price Data API.
	 * If your tokens are depleted, this method will fail.
	 *
	 * @param r the API Request {@link Request}
	 * @return Promise for {@link Response}
	 */
	public Promise<Response, Response, Void> sendRequest(Request r) {
		return sendRequest(r, 30000, 120000);
	}

	/**
	 * Issue a request to the Keepa Price Data API.
	 * If your tokens are depleted, this method will fail.
	 *
	 * @param r the API Request {@link Request}
	 * @param connectTimeout the timeout value, in milliseconds, to be used when opening a connection to the API
	 * @param readTimeout the read timeout value, in milliseconds, for receiving an API response
	 * @return Promise for {@link Response}
	 */
	public Promise<Response, Response, Void> sendRequest(Request r, int connectTimeout, int readTimeout) {
		Deferred<Response, Response, Void> d = new DeferredObject<>();

		if(r == null){
			d.reject(null);
			return d.promise();
		}

		executorDeferred.execute(() -> {
			long responseTime = System.nanoTime();
			Response response;

			String query = r.parameter.entrySet().stream()
					.map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue()))
					.reduce((p1, p2) -> p1 + "&" + p2)
					.orElse("");

			String url = "https://api.keepa.com/" + r.path + "?key=" + accessKey + "&" + query;

			try {
				URL obj = new URL(url);
				HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
				con.setUseCaches(false);
				con.setRequestProperty("User-Agent", this.userAgent);
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("Accept-Encoding", "gzip");
				con.setConnectTimeout(connectTimeout);
				con.setReadTimeout(readTimeout);
				if (r.postData != null) {
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
					con.setDoOutput(true);
					try (OutputStream os = con.getOutputStream()) {
						os.write(r.postData.getBytes(StandardCharsets.UTF_8));
					}
				} else
					con.setRequestMethod("GET");

				int responseCode = con.getResponseCode();

				if (responseCode == 200) {
					try (InputStream is = con.getInputStream();
					     GZIPInputStream gis = new GZIPInputStream(is)) {
						JsonReader reader = new JsonReader(new InputStreamReader(gis, "UTF-8"));
						response = gson.fromJson(reader, Response.class);
						response.status = ResponseStatus.OK;
					} catch (Exception e) {
						response = new Response();
						response.status = ResponseStatus.FAIL;
						response.exception = e;
					}
				} else {
					try (InputStream is = con.getErrorStream();
					     GZIPInputStream gis = new GZIPInputStream(is)) {
						JsonReader reader = new JsonReader(new InputStreamReader(gis, "UTF-8"));
						response = gson.fromJson(reader, Response.class);
					} catch (Exception e) {
						response = new Response();
						response.status = ResponseStatus.FAIL;
						response.exception = e;
					}

                    response.statusCode = responseCode;

					switch (responseCode) {
						case 400:
							response.status = ResponseStatus.REQUEST_REJECTED;
							break;
						case 402:
							response.status = ResponseStatus.PAYMENT_REQUIRED;
							break;
						case 404:
							response.status = ResponseStatus.NOT_FOUND;
							break;
						case 405:
							response.status = ResponseStatus.METHOD_NOT_ALLOWED;
							break;
						case 429:
							response.status = ResponseStatus.NOT_ENOUGH_TOKEN;
							break;
						case 500:
							response.status = ResponseStatus.INTERNAL_SERVER_ERROR;
							break;
						default:
							if (response.status != ResponseStatus.FAIL)
								response.status = ResponseStatus.FAIL;
							break;
					}
				}
			} catch (IOException e) {
				response = new Response();
				response.status = ResponseStatus.FAIL;
				response.exception = e;
			}

			response.requestTime = (System.nanoTime() - responseTime) / 1000000;
			if (response.status == ResponseStatus.OK)
				d.resolve(response);
			else
				d.reject(response);
		});
		return d.promise();
	}

	/**
	 * Issue a request to the Keepa Price Data API.
	 * If your API contingent is depleted, this method will retry the request as soon as there are new tokens available. May take minutes.
	 * Will fail it the request failed too many times.
	 *
	 * @param r the API Request {@link Request}
	 * @param connectTimeout the timeout value, in milliseconds, to be used when opening a connection to the API
	 * @param readTimeout the read timeout value, in milliseconds, for receiving an API response
	 * @return Promise for {@link Response}
	 */
	public Promise<Response, Response, Void> sendRequestWithRetry(Request r, int connectTimeout, int readTimeout) {
		Deferred<Response, Response, Void> deferred = new DeferredObject<>();
		AtomicInteger expoDelay = new AtomicInteger(0);

		executorRetry.execute(() -> {
			int retry = 0;
			Response[] lastResponse = {null};
			final boolean[] solved = {false};

			try {
				int delay = 0;

				while (!solved[0]) {
					if (lastResponse[0] != null && lastResponse[0].status == ResponseStatus.NOT_ENOUGH_TOKEN && lastResponse[0].refillIn > 0)
						delay = lastResponse[0].refillIn + 100;

					if (retry > 0)
						Thread.sleep(delay);

					Promise<Response, Response, Void> p1 = sendRequest(r, connectTimeout, readTimeout);
					p1
							.done(result -> {
								deferred.resolve(result);
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
										deferred.reject(result);
										solved[0] = true;
								}
							})
							.waitSafely();

					if (p1.isRejected()) {
						retry++;
						delay = expoDelay.getAndUpdate(operand -> Math.min(2 * operand + 100, maxDelay));
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				deferred.reject(null);
			}
		});

		return deferred.promise();
	}

	/**
	 * Issue a request to the Keepa Price Data API.
	 * If your API contingent is depleted, this method will retry the request as soon as there are new tokens available. May take minutes.
	 * Will fail it the request failed too many times.
	 *
	 * @param r the API Request {@link Request}
	 * @return Promise for {@link Response}
	 */
	public Promise<Response, Response, Void> sendRequestWithRetry(Request r) {
		return sendRequestWithRetry(r, 30000, 120000);
	}

}
