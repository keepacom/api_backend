package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

import java.util.HashMap;

import static com.keepa.api.backend.helper.Utility.*;

/**
 * Common Request
 */
public class Request {
//	public enum RequestType {
//		REQUEST_PRODUCTS, BROWSING_DEALS, CATEGORY_LOOKUP, CATEGORY_SEARCH, PRODUCT_SEARCH, SELLER_REQUEST
//	}

	public HashMap<String, String> parameter;
	public String postData;
	public String path;

	public Request() {
		parameter = new HashMap<>(20);
	}

	/**
	 * By accessing our deals you can find products that recently changed and match your search criteria. A single request will return a maximum of 150 deals.
	 *
	 * @param dealRequest The dealRequest contains all request parameters.
	 * @return A ready to send request.
	 */
	public static Request getDealsRequest(DealRequest dealRequest) {
		Request r = new Request();
		r.path = "deal";
		r.postData = gson.toJson(dealRequest);
		return r;
	}

	/**
	 * Query our product database to find products matching your criteria. Almost all product fields can be searched for and sorted by.
	 * The product finder request provides the same core functionality as our Product Finder.
	 *
	 * @param finderRequest The FinderRequest contains all filter parameters.
	 * @return A ready to send request.
	 */
	public static Request getProductFinderRequest(final AmazonLocale domainId, ProductFinderRequest finderRequest) {
		Request r = new Request();
		r.path = "query";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("selection", gson.toJson(finderRequest));
		return r;
	}

	/**
	 * Add tracking to your list.
	 *
	 * @param trackingRequest The trackingRequest contains all request parameters.
	 * @return A ready to send request.
	 */
	public static Request getTrackingAddRequest(TrackingRequest trackingRequest) {
		return getTrackingBatchAddRequest(trackingRequest);
	}

	/**
	 * Add multiple tracking to your list. Much more efficient than individual additions.
	 *
	 * @param trackingRequests The trackingRequests (up to 1000).
	 * @return A ready to send request.
	 */
	public static Request getTrackingBatchAddRequest(TrackingRequest... trackingRequests) {
		if(trackingRequests.length > 1000) return null;
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("type", "add");
		r.postData = gson.toJson(trackingRequests);
		return r;
	}

	/**
	 * Get a single tracking from your list.
	 *
	 * @param asin The ASIN to retrieve the tracking for.
	 * @return A ready to send request.
	 */
	public static Request getTrackingGetRequest(String asin) {
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("type", "get");
		r.parameter.put("asin", asin);
		return r;
	}

	/**
	 * Get all trackings from your list. If you track a lot of products this may be a resource intensive operation. Use responsibly.
	 *
	 * @param asinsOnly Wether or not to only request an ASIN list of tracked products or to return all tracking objects (much larger response).
	 * @return A ready to send request.
	 */
	public static Request getTrackingListRequest(boolean asinsOnly) {
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("type", "list");
		if(asinsOnly)
			r.parameter.put("asins-only", "1");
		return r;
	}

	/**
	 * Get your recent notifications.
	 * @param since Retrieve all available notifications that occurred since this date, in {@link KeepaTime}. Use 0 to request all.
	 * @param revise Whether or not to request notifications already marked as read.
	 * @return A ready to send request.
	 */
	public static Request getTrackingNotificationRequest(int since, boolean revise) {
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("since", String.valueOf(since));
		r.parameter.put("revise", revise ? "1" : "0");
		r.parameter.put("type", "notification");
		return r;
	}


	/**
	 * Remove a tracking from your list.
	 *
	 * @param asin The ASIN to remove the tracking for.
	 * @return A ready to send request.
	 */
	public static Request getTrackingRemoveRequest(String asin) {
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("type", "remove");
		r.parameter.put("asin", asin);
		return r;
	}

	/**
	 * Removes all your trackings from your list.
	 *
	 * @return A ready to send request.
	 */
	public static Request getTrackingRemoveAllRequest() {
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("type", "removeAll");
		return r;
	}

	/**
	 * Updates the webhook URL our service will call whenever a notification is triggered. The URL can also be updated and tested via the website.
	 * A push notification will be a HTTP POST call with a single notification object as its content.
	 * Your server must respond with a status code of 200 to confirm the successful retrieval.
	 * If delivery fails a second attempt will be made with a 15 seconds delay.
	 *
	 * @param url The new webhook URL
	 * @return A ready to send request.
	 */
	public static Request getTrackingWebhookRequest(String url) {
		Request r = new Request();
		r.path = "tracking";
		r.parameter.put("type", "webhook");
		r.parameter.put("url", url);
		return r;
	}

	/**
	 * Retrieve an ASIN list of the most popular products based on sales in a specific category.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param category The category node id of the category you want to request the best sellers list for
	 * @return A ready to send request.
	 */
	public static Request getBestSellersRequest(final AmazonLocale domainId, long category) {
		return getBestSellersRequest(domainId, String.valueOf(category));
	}

	/**
	 * Retrieve an ASIN list of the most popular products based on sales of a specific product group.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param productGroup The productGroup you want to request the best sellers list for
	 * @return A ready to send request.
	 */
	public static Request getBestSellersRequest(final AmazonLocale domainId, String productGroup) {
		Request r = new Request();
		r.path = "bestsellers";
		r.parameter.put("category", productGroup);
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		return r;
	}

	/**
	 * Retrieve category objects using their node ids and (optional) their parent tree.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param category The category node id of the category you want to request. For batch requests a comma separated list of ids (up to 10, the token cost stays the same).
	 * @param parents  Whether or not to include the category tree for each category.
	 * @return A ready to send request.
	 */
	public static Request getCategoryLookupRequest(final AmazonLocale domainId, boolean parents, long category) {
		Request r = new Request();
		r.path = "category";
		r.parameter.put("category", String.valueOf(category));
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));

		if (parents)
			r.parameter.put("parents", "1");

		return r;
	}

	/**
	 * Search for Amazon category names. Retrieves the category objects12 and optional their parent tree.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param term     The term you want to search for. Multiple space separated keywords are possible and if provided must all match. The minimum length of a keyword is 3 characters.
	 * @param parents  Whether or not to include the category tree for each category.
	 * @return A ready to send request.
	 */
	public static Request getCategorySearchRequest(final AmazonLocale domainId, String term, boolean parents) {
		Request r = new Request();
		r.path = "search";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("type", "category");
		r.parameter.put("term", term);

		if (parents)
			r.parameter.put("parents", "1");

		return r;
	}

	/**
	 * Retrieves the seller object via the seller id. If a seller is not found no tokens will be consumed.
	 * <p>
	 * <b>Seller data is not available for Amazon China.</b>
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param seller   The seller id of the merchant you want to request. For batch requests a comma separated list of sellerIds (up to 100). The seller id is part of the offer object and can also be found on Amazon on seller profile pages in the seller parameter of the URL.
	 *                 Example: A2L77EE7U53NWQ (Amazon.com Warehouse Deals)
	 * @return A ready to send request.
	 */
	public static Request getSellerRequest(final AmazonLocale domainId, String... seller) {
		Request r = new Request();
		r.path = "seller";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("seller", arrayToCsv(seller));
		return r;
	}

	/**
	 * Retrieves the seller object via the seller id. If a seller is not found no tokens will be consumed.
	 * <p>
	 * <b>Seller data is not available for Amazon China.</b>
	 *
	 * @param domainId   Amazon locale of the product {@link AmazonLocale}
	 * @param seller     The seller id of the merchant you want to request. For batch requests a comma separated list of sellerIds (up to 100).
	 *                   The seller id is part of the offer object and can also be found on Amazon on seller profile pages in the seller parameter of the URL.
	 *                   Example: A2L77EE7U53NWQ (Amazon.com Warehouse Deals)
	 * @param storefront Valid values: 0 (false) and 1 (true). If specified the seller object will contain additional information about what items the seller is listing on Amazon.
	 *                   This includes a list of ASINs as well as the total amount of items the seller has listed.
	 *                   The following seller object fields will be set if data is available: asinList, asinListLastSeen.
	 *                   If no data is available no additional tokens will be consumed. The ASIN list can contain up to 100,000 items.
	 *                   As using the storefront parameter does not trigger any new collection it does not increase the processing time
	 *                   of the request, though the response may be much bigger in size.
	 * @return A ready to send request.
	 */
	public static Request getSellerRequest(final AmazonLocale domainId, String seller, boolean storefront) {
		Request r = new Request();
		r.path = "seller";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("seller", seller);

		if (storefront)
			r.parameter.put("storefront", "1");

		return r;
	}

	/**
	 * Retrieves the seller object via the seller id. If a seller is not found no tokens will be consumed.
	 * <p>
	 * <b>Seller data is not available for Amazon China.</b>
	 *
	 * @param domainId   Amazon locale of the product {@link AmazonLocale}
	 * @param seller     The seller id of the merchant you want to request. For batch requests a comma separated list of sellerIds (up to 100).
	 *                   The seller id is part of the offer object and can also be found on Amazon on seller profile pages in the seller parameter of the URL.
	 *                   Example: A2L77EE7U53NWQ (Amazon.com Warehouse Deals)
	 * @param storefront Valid values: 0 (false) and 1 (true). If specified the seller object will contain additional information about what items the seller is listing on Amazon.
	 *                   This includes a list of ASINs as well as the total amount of items the seller has listed.
	 *                   The following seller object fields will be set if data is available: asinList, asinListLastSeen.
	 *                   If no data is available no additional tokens will be consumed. The ASIN list can contain up to 100,000 items.
	 *                   As using the storefront parameter does not trigger any new collection it does not increase the processing time
	 *                   of the request, though the response may be much bigger in size.
	 * @param update     Positive integer value. If the last live data collection from the Amazon storefront page is older than update hours force a new collection. Use this parameter in conjunction with the
	 *                   storefront parameter. Token cost will only be applied if a new collection is triggered.
	 * @return A ready to send request.
	 */
	public static Request getSellerRequest(final AmazonLocale domainId, String seller, boolean storefront, int update) {
		Request r = new Request();
		r.path = "seller";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("seller", seller);
		if (update >= 0)
			r.parameter.put("update", String.valueOf(update));

		if (storefront || update >= 0)
			r.parameter.put("storefront", "1");

		return r;
	}

	/**
	 * Retrieve a Seller ID list of the most rated Amazon marketplace sellers.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}. China is not supported.
	 * @return A ready to send request.
	 */
	public static Request getTopSellerRequest(final AmazonLocale domainId) {
		Request r = new Request();
		r.path = "topseller";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		return r;
	}

	/**
	 * Search for Amazon products using keywords with a maximum of 50 results per search term.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param term     The term you want to search for.
	 * @param stats    If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values of the last x days, where x is the value of the stats parameter.
	 * @return A ready to send request.
	 */
	public static Request getProductSearchRequest(final AmazonLocale domainId, String term, Integer stats) {
		Request r = new Request();
		r.path = "search";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("type", "product");
		r.parameter.put("term", term);

		if (stats != null && stats > 0)
			r.parameter.put("stats", String.valueOf(stats));

		return r;
	}

	/**
	 * Search for Amazon products using keywords with a maximum of 50 results per search term.
	 *
	 * @param domainId  Amazon locale of the product {@link AmazonLocale}
	 * @param term      The term you want to search for.
	 * @param history   Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update    If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param stats     If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values of the last x days, where x is the value of the stats parameter.
	 * @param asinsOnly If true only the ASINs of the found products will be provided (instead of the product objects). If this parameter is used the token cost of the request is reduced to 5 tokens.
	 * @return A ready to send request.
	 */
	public static Request getProductSearchRequest(final AmazonLocale domainId, String term, Integer stats, int update, boolean history, boolean asinsOnly) {
		Request r = new Request();
		r.path = "search";
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("type", "product");
		r.parameter.put("term", term);
		r.parameter.put("update", String.valueOf(update));
		r.parameter.put("history", history ? "1" : "0");
		r.parameter.put("asins-only", asinsOnly ? "1" : "0");

		if (stats != null && stats > 0)
			r.parameter.put("stats", String.valueOf(stats));

		return r;
	}

	/**
	 * Retrieves the product object for the specified ASIN and domain.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param asins    ASINs to request, must contain between 1 and 100 ASINs - or max 20 ASINs if the offers parameter is used.
	 * @param stats    If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values of the last x days, where x is the value of the stats parameter.
	 * @param offers   If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductRequest(final AmazonLocale domainId, Integer stats, Integer offers, final String... asins) {
		Request r = new Request();
		r.path = "product";
		r.parameter.put("asin", arrayToCsv(asins));
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		if (stats != null && stats > 0)
			r.parameter.put("stats", String.valueOf(stats));

		if (offers != null && offers > 0)
			r.parameter.put("offers", String.valueOf(offers));

		return r;
	}

	/**
	 * Retrieves the product object(s) for the specified product code and domain.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param codes    The product code of the product you want to request. We currently allow UPC, EAN and ISBN-13 codes. For batch requests a comma separated list of codes (up to 100). Multiple ASINs can have the same product code, so requesting a product code can return multiple products.
	 * @param stats    If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values of the last x days, where x is the value of the stats parameter.
	 * @param offers   If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductByCodeRequest(final AmazonLocale domainId, Integer stats, Integer offers, final String... codes) {
		Request r = new Request();
		r.path = "product";
		r.parameter.put("code", arrayToCsv(codes));
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		if (stats != null && stats > 0)
			r.parameter.put("stats", String.valueOf(stats));

		if (offers != null && offers > 0)
			r.parameter.put("offers", String.valueOf(offers));

		return r;
	}

	/**
	 * Retrieves the product object for the specified ASIN and domain.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param asins    ASINs to request, must contain between 1 and 100 ASINs - or max 20 ASINs if the offers parameter is used.
	 * @param stats    If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values of the last x days, where x is the value of the stats parameter.
	 * @param history  Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update   If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers   If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductRequest(final AmazonLocale domainId, Integer stats, Integer offers, int update, boolean history, final String... asins) {
		if (stats == null) {
			return getProductRequest(domainId, offers, null, null, update, history, asins);
		} else {
			long now = System.currentTimeMillis();
			return getProductRequest(domainId, offers, String.valueOf((now - (stats * 24 * 60 * 60 * 1000L))), String.valueOf(now), update, history, asins);
		}
	}

	/**
	 * Retrieves the product object(s) for the specified product code and domain.
	 *
	 * @param domainId Amazon locale of the product {@link AmazonLocale}
	 * @param codes    The product code of the product you want to request. We currently allow UPC, EAN and ISBN-13 codes. For batch requests a comma separated list of codes (up to 100). Multiple ASINs can have the same product code, so requesting a product code can return multiple products.
	 * @param stats    If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values of the last x days, where x is the value of the stats parameter.
	 * @param history  Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update   If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers   If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductByCodeRequest(final AmazonLocale domainId, Integer stats, Integer offers, int update, boolean history, final String... codes) {
		if (stats == null) {
			return getProductByCodeRequest(domainId, offers, null, null, update, history, codes);
		} else {
			long now = System.currentTimeMillis();
			return getProductByCodeRequest(domainId, offers, String.valueOf((now - (stats * 24 * 60 * 60 * 1000L))), String.valueOf(now), update, history, codes);
		}
	}

	/**
	 * Retrieves the product object for the specified ASIN and domain.
	 *
	 * @param domainId       Amazon locale of the product {@link AmazonLocale}
	 * @param asins          ASINs to request, must contain between 1 and 100 ASINs - or max 20 ASINs if the offers parameter is used.
	 * @param statsStartDate Must be Unix epoch time milliseconds. If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values in the interval specified statsStartDate to statsEndDate. .
	 * @param statsEndDate   the end of the stats interval. See statsStartDate.
	 * @param history        Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update         If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers         If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductRequest(final AmazonLocale domainId, Long statsStartDate, Long statsEndDate, Integer offers, int update, boolean history, final String... asins) {
		if (statsStartDate == null) {
			return getProductRequest(domainId, offers, null, null, update, history, asins);
		} else {
			return getProductRequest(domainId, offers, String.valueOf(statsStartDate), String.valueOf(statsEndDate),  update, history, asins);
		}
	}


	/**
	 * Retrieves the product object(s) for the specified product code and domain.
	 *
	 * @param domainId       Amazon locale of the product {@link AmazonLocale}
	 * @param codes          The product code of the product you want to request. We currently allow UPC, EAN and ISBN-13 codes. For batch requests a comma separated list of codes (up to 100). Multiple ASINs can have the same product code, so requesting a product code can return multiple products.
	 * @param statsStartDate Must be Unix epoch time milliseconds. If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values in the interval specified statsStartDate to statsEndDate. .
	 * @param statsEndDate   the end of the stats interval. See statsStartDate.
	 * @param history        Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update         If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers         If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductByCodeRequest(final AmazonLocale domainId, Long statsStartDate, Long statsEndDate, Integer offers, int update, boolean history, final String... codes) {
		if (statsStartDate == null) {
			return getProductByCodeRequest(domainId, offers, null, null, update, history, codes);
		} else {
			return getProductByCodeRequest(domainId, offers, String.valueOf(statsStartDate), String.valueOf(statsEndDate),  update, history, codes);
		}
	}

	/**
	 * Retrieves the product object for the specified ASIN and domain.
	 *
	 * @param domainId       Amazon locale of the product {@link AmazonLocale}
	 * @param asins          ASINs to request, must contain between 1 and 100 ASINs - or max 20 ASINs if the offers parameter is used.
	 * @param statsStartDate Must ISO8601 coded date (with or without time in UTC). Example: 2015-12-31 or 2015-12-31T14:51Z. If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values in the interval specified statsStartDate to statsEndDate. .
	 * @param statsEndDate   the end of the stats interval. See statsStartDate.
	 * @param history        Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update         If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers         If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductRequest(final AmazonLocale domainId, Integer offers, String statsStartDate, String statsEndDate, int update, boolean history, final String... asins) {
		Request r = new Request();
		r.path = "product";
		r.parameter.put("asin", arrayToCsv(asins));
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("update", String.valueOf(update));
		r.parameter.put("history", history ? "1" : "0");

		if (statsStartDate != null && statsEndDate != null)
			r.parameter.put("stats", statsStartDate + "," + statsEndDate);

		if (offers != null && offers > 0)
			r.parameter.put("offers", String.valueOf(offers));

		return r;
	}

	/**
	 * Retrieves the product object(s) for the specified product code and domain.
	 *
	 * @param domainId       Amazon locale of the product {@link AmazonLocale}
	 * @param codes          The product code of the product you want to request. We currently allow UPC, EAN and ISBN-13 codes. For batch requests a comma separated list of codes (up to 100). Multiple ASINs can have the same product code, so requesting a product code can return multiple products.
	 * @param statsStartDate Must ISO8601 coded date (with or without time in UTC). Example: 2015-12-31 or 2015-12-31T14:51Z. If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values in the interval specified statsStartDate to statsEndDate. .
	 * @param statsEndDate   the end of the stats interval. See statsStartDate.
	 * @param history        Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update         If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers         If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductByCodeRequest(final AmazonLocale domainId, Integer offers, String statsStartDate, String statsEndDate, int update, boolean history, final String... codes) {
		Request r = new Request();
		r.path = "product";
		r.parameter.put("code", arrayToCsv(codes));
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("update", String.valueOf(update));
		r.parameter.put("history", history ? "1" : "0");

		if (statsStartDate != null && statsEndDate != null)
			r.parameter.put("stats", statsStartDate + "," + statsEndDate);

		if (offers != null && offers > 0)
			r.parameter.put("offers", String.valueOf(offers));

		return r;
	}

	/**
	 * Retrieves the product object(s) for the specified product code and domain.
	 *
	 * @param domainId       Amazon locale of the product {@link AmazonLocale}
	 * @param codes          The product code of the product you want to request. We currently allow UPC, EAN and ISBN-13 codes. For batch requests a comma separated list of codes (up to 100). Multiple ASINs can have the same product code, so requesting a product code can return multiple products.
	 * @param statsStartDate Must ISO8601 coded date (with or without time in UTC). Example: 2015-12-31 or 2015-12-31T14:51Z. If specified (= not null) the product object will have a stats field with quick access to current prices, min/max prices and the weighted mean values in the interval specified statsStartDate to statsEndDate. .
	 * @param statsEndDate   the end of the stats interval. See statsStartDate.
	 * @param history        Whether or not to include the product's history data (csv field). If you do not evaluate the csv field set to false to speed up the request and reduce traffic.
	 * @param update         If the product's last refresh is older than <i>update</i>-hours force a refresh. Use this to speed up requests if up-to-date data is not required. Might cost an extra token if 0 (= live data). Default 1.
	 * @param offers         If specified (= not null) determines the number of marketplace offers to retrieve. <b>Not available for Amazon China.</b>
	 * @param stock          If true stock will be collected for all retrieved live offers. <b>Can only be used in conjunction with the offers parameter.  Not available for Amazon China.</b>
	 * @param rental         If true the rental price will be collected when available. <b>Can only be used in conjunction with the offers parameter.  Not available for Amazon China.</b>
	 * @param rating         If true the product object will include our existing RATING and COUNT_REVIEWS history of the csv field, regardless if the offers parameter is used <b>Not available for Amazon China.</b>
	 * @param fbafees        If true fbaFees will be retrieved. <b>Can only be used in conjunction with the offers parameter. Not available for Amazon China, India and Brazil.</b>
	 * @return A ready to send request.
	 */
	public static Request getProductByCodeRequest(final AmazonLocale domainId, Integer offers, String statsStartDate, String statsEndDate, int update, boolean history,
	                                              boolean stock, boolean rental, boolean rating, boolean fbafees, final String... codes) {
		Request r = new Request();
		r.path = "product";
		r.parameter.put("code", arrayToCsv(codes));
		r.parameter.put("domain", String.valueOf(domainId.ordinal()));
		r.parameter.put("update", String.valueOf(update));
		r.parameter.put("history", history ? "1" : "0");
		r.parameter.put("stock", stock ? "1" : "0");
		r.parameter.put("rental", rental ? "1" : "0");
		r.parameter.put("rating", rating ? "1" : "0");
		r.parameter.put("fbafees", fbafees ? "1" : "0");

		if (statsStartDate != null && statsEndDate != null)
			r.parameter.put("stats", statsStartDate + "," + statsEndDate);

		if (offers != null && offers > 0)
			r.parameter.put("offers", String.valueOf(offers));

		return r;
	}

	@Override
	public String toString() {
		return gsonPretty.toJson(this);
	}
}