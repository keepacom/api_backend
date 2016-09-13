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
	 * List of all sub categories. null or [] (empty array) if the category has no sub categories.
	 */
	public long[] children;

	/**
	 * The parent category's Id. Always a positive Long value. If it is 0 the category is a root category and has no parent category.
	 */
	public long parent;


	@Override
	public String toString() {
		return gsonPretty.toJson(this);
	}
}