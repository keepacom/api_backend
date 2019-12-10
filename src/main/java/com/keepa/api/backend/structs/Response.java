package com.keepa.api.backend.structs;

import com.keepa.api.backend.KeepaAPI;

import java.util.HashMap;

import static com.keepa.api.backend.helper.Utility.gson;
import static com.keepa.api.backend.helper.Utility.gsonPretty;

/**
 * Common Keepa API Response
 */
public class Response {
	/**
	 * Server time when response was sent.
	 */
	public long timestamp = System.currentTimeMillis();

	/**
	 * States how many ASINs may be requested before the assigned API contingent is depleted.
	 * If the contigent is depleted, HTTP status code 503 will be delivered with the message:
	 * "You are submitting requests too quickly and your requests are being throttled."
	 */
	public int tokensLeft = 0;

	/**
	 * Milliseconds till new tokens are generated. Use this if your contigent is depleted to wait before you try a new request. Tokens are generated every 5 minutes.
	 */
	public int refillIn = 0;

	/**
	 * Token refill rate per minute.
	 */
	public int refillRate = 0;

	/**
	 * total time the request took (local, including latencies and connection establishment), in milliseconds
	 */
	public long requestTime = 0;

	/**
	 * time the request's processing took (remote), in milliseconds
	 */
	public int processingTimeInMs = 0;

	/**
	 * Token flow reduction
	 */
	public double tokenFlowReduction = 0;

	/**
	 * Tokens used for call
	 */
	public int tokensConsumed = 0;

	/**
	 * Status of the request.
	 */
	public KeepaAPI.ResponseStatus status = KeepaAPI.ResponseStatus.PENDING;

	/**
	 * Results of the product request
	 */
	public Product[] products = null;

	/**
	 * Results of the category lookup and search
	 */
	public HashMap<Long, Category> categories = null;

	/**
	 * Results of the category lookup and search includeParents parameter
	 */
	public HashMap<Long, Category> categoryParents = null;

	/**
	 * Results of the deals request
	 */
	public DealResponse deals = null;

	/**
	 * Results of the best sellers request
	 */
	public BestSellers bestSellersList = null;

	/**
	 * Results of the deals request
	 */
	public HashMap<String, Seller> sellers = null;

	/**
	 * Results of get and add tracking operations
	 */
	public Tracking[] trackings = null;

	/**
	 * Results of get and add tracking operations
	 */
	public Notification[] notifications = null;

	/**
	 * A list of ASINs. Result of, but not limited to, the get tracking list operation
	 */
	public String[] asinList = null;

	/**
	 * Estimated count of all matched products.
	 */
	public Integer totalResults = null;

	/**
	 * A list of sellerIds.
	 */
	public String[] sellerIdList = null;

	/**
	 * A list of lightning deals.
	 */
	public LightningDeal[] lightningDeals = null;

	/**
	 * Contains information about any error that might have occurred.
	 */
	public RequestError error = null;

	/**
	 * Contains request specific additional output.
	 */
	public String additional = null;

	static public Response REQUEST_FAILED = new Response();
	static public Response REQUEST_REJECTED = new Response();
	static public Response NOT_ENOUGH_TOKEN = new Response();

	static {
		REQUEST_FAILED.status = KeepaAPI.ResponseStatus.FAIL;
		REQUEST_REJECTED.status = KeepaAPI.ResponseStatus.REQUEST_REJECTED;
		NOT_ENOUGH_TOKEN.status =  KeepaAPI.ResponseStatus.NOT_ENOUGH_TOKEN;
	}

	@Override
	public String toString() {
		if(status == KeepaAPI.ResponseStatus.OK)
			return gson.toJson(this);
		else
			return gsonPretty.toJson(this);
	}
}
