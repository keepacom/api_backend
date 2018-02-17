package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

/**
 * About:
 * A best sellers ASIN list of a specific category.
 * <p>
 * Returned by:
 * Request Best Sellers
 */
public class BestSellers {

	/**
	 * Integer value for the Amazon locale this category belongs to.
	 * {@link AmazonLocale}
	 */
	public byte domainId;

	/**
	 * States the last time we have updated the list, in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastUpdate;

	/**
	 * The category node id used by Amazon. Represents the identifier of the category. Also part of the Product object's categories and rootCategory fields. Always a positive Long value or 0 if a product group was used.
	 */
	public long categoryId;

	/**
	 * An ASIN list. The list starts with the best selling product (lowest sales rank).
	 */
	public String[] asinList;
}
