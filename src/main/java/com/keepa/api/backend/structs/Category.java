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

	/**
	 * Average current buy box price of all products in this category.
	 * Value is in the currency's smallest unit (e.g., cents for USD/EUR). May be null.
	 */
	public Integer avgBuyBox;

	/**
	 * Average 90 day buy box price of all products in this category.
	 * Value is in the currency's smallest unit (e.g., cents for USD/EUR). May be null.
	 */
	public Integer avgBuyBox90;

	/**
	 * Average 365 day buy box price of all products in this category.
	 * Value is in the currency's smallest unit (e.g., cents for USD/EUR). May be null.
	 */
	public Integer avgBuyBox365;

	/**
	 * Average 30 day buy box deviation (standard deviation) of all products in this category.
	 * Value is in the currency's smallest unit (e.g., cents for USD/EUR). May be null.
	 */
	public Integer avgBuyBoxDeviation;

	/**
	 * Average number of reviews of all products in this category. May be null.
	 */
	public Integer avgReviewCount;

	/**
	 * Average rating of all products in this category.
	 * Value is multiplied by 10 (e.g., 45 means 4.5 stars). May be null.
	 */
	public Integer avgRating;

	/**
	 * Percentage of products fulfilled by Amazon (FBA) in this category. May be null.
	 * Represents the distribution of FBA vs. third-party sellers.
	 */
	public Float isFBAPercent;

	/**
	 * Percentage of products sold directly by Amazon in this category. May be null.
	 */
	public Float soldByAmazonPercent;

	/**
	 * Percentage of products that have an active coupon in this category. May be null.
	 */
	public Float hasCouponPercent;

	/**
	 * Average number of new offers of all products in this category. May be null.
	 */
	public Float avgOfferCountNew;

	/**
	 * Average number of used offers of all products in this category. May be null.
	 */
	public Float avgOfferCountUsed;

	/**
	 * Number of distinct sellers with at least one active offer in this category. May be null.
	 */
	public Integer sellerCount;

	/**
	 * Number of distinct brands present in this category. May be null.
	 */
	public Integer brandCount;
	

	@Override
	public String toString() {
		return gsonPretty.toJson(this);
	}
}