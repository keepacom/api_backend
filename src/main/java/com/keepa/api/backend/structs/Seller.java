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
	public int trackedSince;

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
	 * <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 */
	public int lastUpdate;

	/**
	 * Indicating whether or not our system identified that this seller attempts to scam users.
	 */
	public boolean isScammer;

	/**
	 * Indicating whether or not this seller ships from China.
	 */
	public boolean shipsFromChina;

	/**
	 * Boolean value indicating whether or not the seller currently has FBA listings.<br>
	 * This value is usually correct, but could be set to false even if the seller has FBA listings, since we are not always aware of all<br>
	 * seller listings. This can especially be the case with sellers with only a few listings consisting of slow-selling products.
	 */
	public boolean hasFBA;

	/**
	 * Contains the number of storefront ASINs if available and the last update of that metric.<br>
	 * Is null if not available (no storefront was ever retrieved). This field is available in the <br>
	 * default Request Seller Information (storefront parameter is not required).<br>
	 * <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p><br>
	 * Has the format: [ last update of the storefront in Keepa Time minutes, the count of storefront ASINs ]<br><br>
	 * Example: [2711319, 1200]
	 */
	public int[] totalStorefrontAsins = null;

	/**
	 * Only available if the <i>storefront</i> parameter was used and only updated if the <i>update</i> parameter was utilized.<br><br>
	 * String array containing up to 100,000 storefront ASINs, sorted by freshest first. The corresponding <br>
	 * time stamps can be found in the <i>asinListLastSeen</i> field.<br>
	 * Example: ["B00M0QVG3W", "B00M4KCH2A"]
	 */
	public String[] asinList;

	/**
	 *  Only available if the <i>storefront</i> parameter was used and only updated if the <i>update</i> parameter was utilized.<br><br>
	 *  Contains the last time (in Keepa Time minutes) we were able to verify each ASIN in the _asinList_ field.<br>
	 *  <i>asinList</i> and <i>asinListLastSeen</i> share the same indexation, so the corresponding time stamp<br>
	 *  for `asinList[10]` would be `asinListLastSeen[10]`.
	 *  <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 *  <br>
	 *  Example: [2711319, 2711311]
	 */
	public int[] asinListLastSeen;

	/**
	 * 	Only available if the <i>storefront</i> parameter was used and only updated if the <i>update</i> parameter was utilized.<br><br>
	 *  Contains the total amount of listings of this seller. Includes historical data<br>
	 *  <i>asinList</i> and <i>asinListLastSeen</i> share the same indexation, so the corresponding time stamp<br>
	 *  for `asinList[10]` would be `asinListLastSeen[10]`. Has the format: Keepa Time minutes, count, ...
	 *  <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 *  <br>
	 *  Example: [2711319, 1200, 2711719, 1187]
	 */
	public int[] totalStorefrontAsinsCSV;

	/**
	 *  Statistics about the primary categories of this seller. Based on our often incomplete and outdated product offers data.
	 */
	public MerchantCategoryStatistics[] sellerCategoryStatistics = null;

	/**
	 *  Statistics about the primary brands of this seller. Based on our often incomplete and outdated product offers data.
	 */
	public MerchantBrandStatistics[] sellerBrandStatistics = null;


	/**
	 * The business address. Each entry of the array contains one address line.
	 * The last entry contains the 2 letter country code. null if not available.
	 * Example: [123 Main Street, New York, NY, 10001, US]
	 */
	public String[] address;

	/**
	 * Contains up to 5 of the most recent customer feedbacks.
	 * Each feedback object in the array contains the following fields
	 */
	public FeedbackObject[] recentFeedback;

	/**
	 * States the time of our last rating data update of this seller, in Keepa Time minutes.
	 * <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 */
	public int lastRatingUpdate;

	/**
	 * Contains the neutral percentage ratings for the last 30 days, 90 days, 365 days and lifetime, in that order.
	 * A neutral rating is a 3 star rating.
	 * Example: [1, 1, 1, 2]
	 */
	public int[] neutralRating = null;

	/**
	 * Contains the negative percentage ratings for the last 30 days, 90 days, 365 days and lifetime, in that order.
	 * A negative rating is a 1 or 2 star rating.
	 * Example: [3, 1, 1, 3]
	 */
	public int[] negativeRating = null;

	/**
	 * Contains the positive percentage ratings for the last 30 days, 90 days, 365 days and lifetime, in that order.
	 * A positive rating is a 4 or 5 star rating.
	 * Example: [96, 98, 98, 95]
	 */
	public int[] positiveRating = null;

	/**
	 * Contains the rating counts for the last 30 days, 90 days, 365 days and lifetime, in that order.
	 * Example: [3, 10, 98, 321]
	 */
	public int[] ratingCount = null;

	/**
	 * The customer services address. Each entry of the array contains one address line.
	 * The last entry contains the 2 letter country code. null if not available.
	 * Example: [123 Main Street, New York, NY, 10001, US]
	 */
	public String[] customerServicesAddress;

	/**
	 * The Trade Register Number. null if not available.
	 * Example: HRB 123 456
	 */
	public String tradeNumber;

	/**
	 * The VAT number. null if not available.
	 * Example: DE123456789
	 */
	public String vatID;

	/**
	 * The phone number. null if not available.
	 * Example: 800 1234 567
	 */
	public String phoneNumber;

	/**
	 * The business type. null if not available.
	 * Example: Unternehmen in Privatbesitz
	 */
	public String businessType;

	/**
	 * The share capital. null if not available.
	 * Example: 25000
	 */
	public String shareCapital;

	/**
	 * The name of the business representative. null if not available.
	 * Example: Max Mustermann
	 */
	public String representative;

	public int currentRating;
	public int currentRatingCount;
	public int ratingsLast30Days;

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

	public static class MerchantBrandStatistics {

		/**
		 * the brand (in all lower-case)
		 */
		public String brand;

		/**
		 * the number of products this merchant sells with this brand
		 */
		public int productCount;

		/**
		 * the 30 day average sales rank of these products
		 */
		public int avg30SalesRank;

		/**
		 * how many of these products have an Amazon offer
		 */
		public int productCountWithAmazonOffer;
	}

	public static class MerchantCategoryStatistics {

		/**
		 * the category id
		 */
		public long catId;

		/**
		 * the number of products this merchant sells with this category
		 */
		public int productCount;

		/**
		 * the 30 day average sales rank of these products
		 */
		public int avg30SalesRank;

		/**
		 * how many of these products have an Amazon offer
		 */
		public int productCountWithAmazonOffer;
	}

	public static class FeedbackObject {
		/**
		 * the feedback star rating - value range from 10 (1 star) to 50 (5 stars)
		 */
		public int rating;

		/**
		 * timestamp of the feedback, in Keepa Time minutes
		 */
		public int date;

		/**
		 * the feedback text
		 */
		public String feedback = null;

		/**
		 * whether or not the feedback is striked
		 */
		public boolean isStriked;
	}

	@Override
	public String toString() {
		return gson.toJson(this);
	}
}
