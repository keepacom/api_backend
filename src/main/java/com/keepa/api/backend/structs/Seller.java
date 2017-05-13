package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

import static com.keepa.api.backend.helper.Utility.gson;

/**
 * About:
 * The seller object provides information about a Amazon marketplace seller.
 * Returned by:
 * The seller object is returned by the following request:
 * Request Seller Information
 */
public class Seller {

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

	/**
	 * String array containing up to 100,000 storefront ASINs, sorted by freshest first. The corresponding <br>
	 * time stamps can be found in the <i>asinListLastSeen</i> field. Only available if the <i>storefront</i> parameter was used.<br>
	 * Example: _["B00M0QVG3W", "B00M4KCH2A"]_
	 */
	public String[] asinList;

	/**
	 *  Contains the last time (in Keepa Time minutes) we were able to verify each ASIN in the _asinList_ field.<br>
	 *  <i>asinList</i> and <i>asinListLastSeen</i> share the same indexation, so the corresponding time stamp<br>
	 *  for `asinList[10]` would be `asinListLastSeen[10]`.
	 *  <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 *  <br>
	 *  Example: _[2711319, 2711311]_
	 */
	public int[] asinListLastSeen;

	/**
	 *  Contains the total amount of listings of this seller. Includes historical data<br>
	 *  <i>asinList</i> and <i>asinListLastSeen</i> share the same indexation, so the corresponding time stamp<br>
	 *  for `asinList[10]` would be `asinListLastSeen[10]`. Only available if the <i>storefront</i> parameter was used and <br>
	 *  only updated if the <i>update</i> parameter was utilized. Has the format: Keepa Time minutes, count, ...
	 *  <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 *  <br>
	 *  Example: _[2711319, 1200, 2711719, 1187]_
	 */
	public int[] totalStorefrontAsinsCSV;

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

	@Override
	public String toString() {
		return gson.toJson(this);
	}
}
