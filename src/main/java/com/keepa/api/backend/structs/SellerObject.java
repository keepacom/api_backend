package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

/**
 * About:
 * The seller object provides information about a Amazon marketplace seller.
 * Returned by:
 * The seller object is returned by the following request:
 * Request Seller Information
 */
public class SellerObject {

	/**
	 * States the time we have started tracking this seller, in Keepa Time minutes.
	 * <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 */
	public int trackedSince; // keepa minutes

	/**
	 * The domainId of the products Amazon locale
	 * {@link AmazonLocale}
	 */
	public byte domainId;

	/**
	 * The seller id of the merchant.
	 * <p>
	 * Example: A2L77EE7U53NWQ (Amazon.com Warehouse Deals)
	 */
	public String sellerId;

	/**
	 * The name of seller.
	 * <p>
	 * Example: Amazon Warehouse Deals
	 */
	public String sellerName;

	/**
	 * Two dimensional history array that contains history data for this seller. First dimension index:
	 * <p>{@link MerchantCsvType}</p>
	 * 0 - RATING: The merchant's rating in percent, Integer from 0 to 100.
	 * 1 - RATING_COUNT: The merchant's total rating count, Integer.
	 */
	public int[][] csv;

	/**
	 * States the time of our last update of this seller, in Keepa Time minutes.
	 * <br>Example: 2711319
	 */
	public int lastUpdate; // keepa minutes

	/**
	 * Indicating whether or not our system identified that this seller attempts to scam users.
	 */
	public boolean isScammer;

	public enum MerchantCsvType {
		RATING(0, false),
		RATING_COUNT(1, false);

		/**
		 * If the values are prices.
		 */
		public final boolean isPrice;

		public final int index;
		public static final MerchantCsvType[] values = MerchantCsvType.values();

		MerchantCsvType(int i, boolean price) {
			index = i;
			isPrice = price;
		}

		public static MerchantCsvType getCSVTypeFromIndex(int index) {
			for (MerchantCsvType type : MerchantCsvType.values) {
				if (type.index == index) return type;
			}
			return RATING;
		}
	}
}
