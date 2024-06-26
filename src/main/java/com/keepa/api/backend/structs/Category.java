package com.keepa.api.backend.structs;


import static com.keepa.api.backend.helper.Utility.gsonPretty;

public final class Category {

	/**
	 * Integer value for the Amazon locale this category belongs to.
	 * {@link AmazonLocale}
	 */
	public byte domainId;

	/**
	 * The category node id used by Amazon. Represents the identifier of the category. Also part of the Product object's categories and rootCategory fields. Always a positive Long value.
	 */
	public long catId;

	/**
	 * The name of the category.
	 */
	public String name;

	/**
	 * The context free category name.
	 */
	public String contextFreeName;

	/**
	 * The websiteDisplayGroup - available for most root categories.
	 */
	public String websiteDisplayGroup;

	/**
	 * List of all sub categories. null or [] (empty array) if the category has no sub categories.
	 */
	public long[] children;

	/**
	 * The parent category's Id. Always a positive Long value. If it is 0 the category is a root category and has no parent category.
	 */
	public long parent;
	
	/**
	 * The highest (root category) sales rank we have observed of a product that is listed in this category. Note: Estimate, as the value is from the Keepa product database and not retrieved from Amazon.
	 */
	public int highestRank;

	/**
	 * The lowest (root category) sales rank we have observed of a product that is listed in this category. Note: Estimate, as the value is from the Keepa product database and not retrieved from Amazon.
	 */
	public int lowestRank;

	/**
	 * Number of products that are listed in this category. Note: Estimate, as the value is from the Keepa product database and not retrieved from Amazon.
	 */
	public int productCount;

	/**
	 * Determines if this category functions as a standard browse node, rather than serving promotional purposes (for example, 'Specialty Stores').
	 */
	public boolean isBrowseNode;
	

	@Override
	public String toString() {
		return gsonPretty.toJson(this);
	}
}