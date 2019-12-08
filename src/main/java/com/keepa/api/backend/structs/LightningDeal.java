package com.keepa.api.backend.structs;

public class LightningDeal {
	/**
	 * The domainId of the products Amazon locale <br>
	 * {@link AmazonLocale}
	 */
	public byte domainId;

	/**
	 * States the time of our last data collection of this lighting deal, in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastUpdate;

	/**
	 * The ASIN of the product
	 */
	public String asin;

	/**
	 * Title of the product. Caution: may contain HTML markup in rare cases.
	 */
	public String title;

	/**
	 * The seller id of the merchant offering this deal.
	 */
	public String sellerName;

	/**
	 * The name of seller offering this deal.
	 */
	public String sellerId;

	/**
	 * A unique ID for this deal.
	 */
	public String dealId;

	/**
	 * The discounted price of this deal. Available once the deal has started. -1 if the deal’s state is upcoming. The price is an integer of the respective Amazon locale’s smallest currency unit (e.g. euro cents or yen).
	 */
	public int dealPrice;

	/**
	 * The regular price of this product. Available once the deal has started. -1 if the deal’s state is upcoming. The price is an integer of the respective Amazon locale’s smallest currency unit (e.g. euro cents or yen).
	 */
	public int currentPrice;

	/**
	 * The name of the primary image of the product. null if not available.
	 */
	public String image;

	/**
	 * Whether or not the deal is Prime eligible.
	 */
	public boolean isPrimeEligible;

	/**
	 * Whether or not the deal is fulfilled by Amazon.
	 */
	public boolean isFulfilledByAmazon;

	/**
	 * Whether or not the price is restricted by MAP (Minimum Advertised Price).
	 */
	public boolean isMAP;

	/**
	 * The rating of the product. A rating is an integer from 0 to 50 (e.g. 45 = 4.5 stars).
	 */
	public int rating;

	/**
	 * The product’s review count.
	 */
	public int totalReviews;

	/**
	 * The state of the deal.
	 */
	public DealState dealState;

	/**
	 * The start time of this lighting deal, in Keepa Time minutes. Note that due to the delay in our data collection the deal price might not be available immediately once the deal has started on Amazon.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int startTime;

	/**
	 * The end time of this lighting deal, in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int endTime;

	/**
	 * The percentage claimed of the lighting deal. Since lightning deals have limited stock, this number may change fast on Amazon, but due to the delay of our data collection the provided value may be outdated.
	 */
	public int percentClaimed;

	/**
	 * The provided discount of this deal, according to Amazon. May be in reference to the list price, not the current price.
	 */
	public int percentOff;

	/**
	 * The dimension attributes of this deal.
	 */
	public Product.VariationAttributeObject[] variation;


	public enum DealState {
		AVAILABLE, UPCOMING, WAITLIST, SOLDOUT, WAITLISTFULL, EXPIRED, SUPPRESSED
}
}
