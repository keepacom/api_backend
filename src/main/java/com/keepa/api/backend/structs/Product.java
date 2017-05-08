package com.keepa.api.backend.structs;


import com.keepa.api.backend.helper.KeepaTime;

import static com.keepa.api.backend.helper.Utility.gson;

public final class Product {

	/**
	 * The ASIN of the product
	 */
	public String asin = null;

	/**
	 * The domainId of the products Amazon locale <br>
	 * {@link AmazonLocale}
	 */
	public byte domainId = 0;

	/**
	 * The ASIN of the parent product (if asin has variations, otherwise null)
	 */
	public String parentAsin = null;

	/**
	 * Comma separated list of variation ASINs of the product (if asin is parentAsin, otherwise null)
	 */
	public String variationCSV = null;

	/**
	 * The UPC of the product. Caution: leading zeros are truncated.
	 */
	public long upc = 0;

	/**
	 * The EAN of the product. Caution: leading zeros are truncated.
	 */
	public long ean = 0;

	/**
	 * The manufacturer’s part number.
	 */
	public String mpn = null;

	/**
	 * Comma separated list of image names of the product. Full Amazon image path:<br>
	 * https://images-na.ssl-images-amazon.com/images/I/_image name_
	 */
	public String imagesCSV = null;

	/**
	 * Array of category node ids
	 */
	public long[] categories = null;

	/**
	 * Category node id of the products' root category. 0 if no root category known
	 */
	public long rootCategory = 0L;

	/**
	 * Name of the manufacturer
	 */
	public String manufacturer = null;

	/**
	 * Title of the product. Caution: may contain HTML markup in rare cases.
	 */
	public String title = null;

	/**
	 * States the time we have started tracking this product, in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int trackingSince = 0;

	/**
	 * An item's brand. null if not available.
	 */
	public String brand = null;

	/**
	 * The item's label. null if not available.
	 */
	public String label = null;

	/**
	 * The item's department. null if not available.
	 */
	public String department = null;

	/**
	 * The item's publisher. null if not available.
	 */
	public String publisher = null;

	/**
	 * The item's productGroup. null if not available.
	 */
	public String productGroup = null;

	/**
	 * The item's partNumber. null if not available.
	 */
	public String partNumber = null;

	/**
	 * The item's studio. null if not available.
	 */
	public String studio = null;

	/**
	 * The item's genre. null if not available.
	 */
	public String genre = null;

	/**
	 * The item's model. null if not available.
	 */
	public String model = null;

	/**
	 * The item's color. null if not available.
	 */
	public String color = null;

	/**
	 * The item's size. null if not available.
	 */
	public String size = null;

	/**
	 * The item's edition. null if not available.
	 */
	public String edition = null;

	/**
	 * The item's platform. null if not available.
	 */
	public String platform = null;

	/**
	 * The item's format. null if not available.
	 */
	public String format = null;

	/**
	 * The package's height in millimeter. 0 or -1 if not available.
	 */
	public int packageHeight = -1;

	/**
	 * The package's length in millimeter. 0 or -1 if not available.
	 */
	public int packageLength = -1;

	/**
	 * The package's width in millimeter. 0 or -1 if not available.
	 */
	public int packageWidth = -1;

	/**
	 * The package's weight in gram. 0 or -1 if not available.
	 */
	public int packageWeight = -1;

	/**
	 * Quantity of items in a package. 0 or -1 if not available.
	 */
	public int packageQuantity = -1;

	/**
	 * Indicates if the item is considered to be for adults only.
	 */
	public boolean isAdultProduct = false;

	/**
	 * Whether or not the product is eligible for trade-in.
	 */
	public boolean isEligibleForTradeIn = false;

	/**
	 * Whether or not the product has reviews.
	 */
	public boolean isEligibleForSuperSaverShipping = false;

	/**
	 * States the last time we have updated the information for this product, in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastUpdate = 0;

	/**
	 * States the last time we have registered a price change (any price kind), in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastPriceChange = 0; // minutes Keepa Epoch

	/**
	 * Keepa product type {@link Product.ProductType}. Must always be evaluated first.
	 */
	public byte productType = 0;

	/**
	 * Whether or not the product has reviews.
	 */
	public boolean hasReviews = false;

	/**
	 * Optional field. Only set if the <i>stats</i> parameter was used in the Product Request. Contains statistic values.
	 */
	public Stats stats = null;

	/**
	 * Optional field. Only set if the <i>offers</i> parameter was used in the Product Request.
	 */
	public Offer[] offers = null;

	/**
	 * Optional field. Only set if the offers parameter was used in the Product Request.<br>
	 * Contains an ordered array of index positions in the offers array for all Marketplace Offer Objects114 retrieved for this call.<br>
	 * The sequence of integers reflects the ordering of the offers on the Amazon offer page (for all conditions).<br>
	 * Since the offers field contains historical offers as well as current offers, one can use this array to <br>
	 * look up all offers that are currently listed on Amazon in the correct order. <br><br>
	 * Example: [ 3, 5, 2, 18, 15 ] - The offer with the array index 3 of the offers field is currently the first <br>
	 *     one listed on the offer listings page on Amazon, followed by the offer with the index 5, and so on.
	 */
	public int[] liveOffersOrder = null;

	/**
	 * Optional field. Only set if the offers parameter was used in the Product Request.<br>
	 * Contains a history of sellerIds that held the Buy Box in the format Keepa time minutes, sellerId, [...].<br>
	 * If no seller qualified for the Buy Box the sellerId "-1" is used. If it was hold by an unknown seller (a brand new one) the sellerId is "-2".<br>
	 * Example: ["2860926","ATVPDKIKX0DER", …]
	 * <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(String)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
	 */
	public String[] buyBoxSellerIdHistory = null;

	/**
	 * Only valid if the offers parameter was used in the Product Request.
	 * Boolean indicating if the ASIN will be redirected to another one on Amazon
	 * (example: the ASIN has the color black variation, which is not available any more
	 * and is redirected on Amazon to the color red).
	 */
	public boolean isRedirectASIN = false;

	/**
	 * Only valid if the offers parameter was used in the Product Request. Boolean indicating if the product's Buy Box is available for subscribe and save.
	 */
	public boolean isSNS = false;

	/**
	 * Only valid if the offers parameter was used in the Product Request. Boolean indicating if the system was able to retrieve fresh offer information.
	 */
	public boolean offersSuccessful = false;

	/**
	 * Integer[][] - two dimensional price history array.<br>
	 * First dimension: {@link Product.CsvType}<br>
	 * Second dimension:<br>
	 * Each array has the format timestamp, price, […]. To get an uncompressed timestamp use {@link KeepaTime#keepaMinuteToUnixInMillis(int)}.<br>
	 * Example: "csv[0]": [411180,4900, ... ]<br>
	 * timestamp: 411180 => 1318510800000<br>
	 * price: 4900 => $ 49.00 (if domainId is 5, Japan, then price: 4900 => ¥ 4900)<br>
	 * A price of '-1' means that there was no offer at the given timestamp (e.g. out of stock).
	 */
	public int[][] csv = null;

	/**
	 * Amazon internal product type categorization.
	 */
	public String type = null;

	public enum CsvType {
		/**
		 * Amazon price history
		 */
		AMAZON(0, true, true, false, false),

		/**
		 * Marketplace/3rd party New price history - Amazon is considered to be part of the marketplace as well,
		 * so if Amazon has the overall lowest new (!) price, the marketplace new price in the corresponding time interval
		 * will be identical to the Amazon price (except if there is only one marketplace offer).
		 * Shipping and Handling costs not included!
		 */
		NEW(1, true, true, false, false),

		/**
		 * Marketplace/3rd party Used price history
		 */
		USED(2, true, true, false, false),

		/**
		 * Sales Rank history. Not every product has a Sales Rank.
		 */
		SALES(3, false, true, false, false),

		/**
		 * List Price history
		 */
		LISTPRICE(4, true, false, false, false),

		/**
		 * Collectible Price history
		 */
		COLLECTIBLE(5, true, true, false, false),

		/**
		 * Refurbished Price history
		 */
		REFURBISHED(6, true, true, false, false),

		/**
		 * 3rd party (not including Amazon) New price history including shipping costs, only fulfilled by merchant (FBM).
		 */
		NEW_FBM_SHIPPING(7, true, true, true, true),

		/**
		 * 3rd party (not including Amazon) New price history including shipping costs, only fulfilled by merchant (FBM).
		 */
		LIGHTNING_DEAL(8, true, true, false, false),

		/**
		 * Amazon Warehouse Deals price history. Mostly of used condition, rarely new.
		 */
		WAREHOUSE(9, true, true, false, true),

		/**
		 * Price history of the lowest 3rd party (not including Amazon/Warehouse) New offer that is fulfilled by Amazon
		 */
		NEW_FBA(10, true, true, false, true),

		/**
		 * New offer count history
		 */
		COUNT_NEW(11, false, false, false, false),

		/**
		 * Used offer count history
		 */
		COUNT_USED(12, false, false, false, false),

		/**
		 * Refurbished offer count history
		 */
		COUNT_REFURBISHED(13, false, false, false, false),

		/**
		 * Collectible offer count history
		 */
		COUNT_COLLECTIBLE(14, false, false, false, false),

		/**
		 * History of past updates to all offers-parameter related data: offers, buyBoxSellerIdHistory, isSNS, isRedirectASIN and the csv types
		 * NEW_SHIPPING, WAREHOUSE, FBA, BUY_BOX_SHIPPING, USED_*_SHIPPING, COLLECTIBLE_*_SHIPPING and REFURBISHED_SHIPPING.
		 * As updates to those fields are infrequent it is important to know when our system updated them.
		 * The absolute value indicates the amount of offers fetched at the given time.
		 * If the value is positive it means all available offers were fetched. It's negative if there were more offers than fetched.
		 */
		EXTRA_INFO_UPDATES(15, false, false, false, true),

		/**
		 * The product's rating history. A rating is an integer from 0 to 50 (e.g. 45 = 4.5 stars)
		 */
		RATING(16, false, false, false, true),
		/**
		 * The product's review count history.
		 */
		COUNT_REVIEWS(17, false, false, false, true),

		/**
		 * The price history of the buy box. If no offer qualified for the buy box the price has the value -1. Including shipping costs.
		 */
		BUY_BOX_SHIPPING(18, true, false, true, true),

		/**
		 * "Used - Like New" price history including shipping costs.
		 */
		USED_NEW_SHIPPING(19, true, true, true, true),

		/**
		 * "Used - Very Good" price history including shipping costs.
		 */
		USED_VERY_GOOD_SHIPPING(20, true, true, true, true),

		/**
		 * "Used - Good" price history including shipping costs.
		 */
		USED_GOOD_SHIPPING(21, true, true, true, true),

		/**
		 * "Used - Acceptable" price history including shipping costs.
		 */
		USED_ACCEPTABLE_SHIPPING(22, true, true, true, true),

		/**
		 * "Collectible - Like New" price history including shipping costs.
		 */
		COLLECTIBLE_NEW_SHIPPING(23, true, true, true, true),

		/**
		 * "Collectible - Very Good" price history including shipping costs.
		 */
		COLLECTIBLE_VERY_GOOD_SHIPPING(24, true, true, true, true),

		/**
		 * "Collectible - Good" price history including shipping costs.
		 */
		COLLECTIBLE_GOOD_SHIPPING(25, true, true, true, true),

		/**
		 * "Collectible - Acceptable" price history including shipping costs.
		 */
		COLLECTIBLE_ACCEPTABLE_SHIPPING(26, true, true, true, true),

		/**
		 * Refurbished price history including shipping costs.
		 */
		REFURBISHED_SHIPPING(27, true, true, true, true),

		/**
		 * reserved for future use
		 */
		RESERVED1(28, true, false, true, false),

		/**
		 * reserved for future use
		 */
		RESERVED2(29, true, false, true, false),

		/**
		 * The trade in price history. Amazon trade-in is not available for every locale.
		 */
		TRADE_IN(30, true, false, false, false);

		public final int index;

		/**
		 * If the values are prices.
		 */
		public final boolean isPrice;

		/**
		 * If the CSV contains shipping costs
		 * If true, csv format has 3 entries: time, price, shippingCosts
		 */
		public final boolean isWithShipping;

		/**
		 * If the type can be used to request deals.
		 */
		public final boolean isDealRelevant;

		/**
		 * True if the data is only accessible in conjunction with the 'offers' parameter of the product request.
		 */
		public final boolean isExtraData;

		public static final CsvType[] values = CsvType.values();

		CsvType(int i, boolean price, boolean deal, boolean shipping, boolean extra) {
			isPrice = price;
			isDealRelevant = deal;
			isExtraData = extra;
			isWithShipping = shipping;
			index = i;
		}

		public static CsvType getCSVTypeFromIndex(int index) {
			for (CsvType type : CsvType.values) {
				if (type.index == index) return type;
			}
			return AMAZON;
		}
	}

	public enum ProductType {
		/**
		 * standard product - everything accessible
		 */
		STANDARD((byte) 0),

		/**
		 * downloadable product – no marketplace price data
		 */
		DOWNLOADABLE((byte) 1),

		/**
		 * ebook – no price data and sales rank accessible
		 */
		EBOOK((byte) 2),

		/**
		 * no data accessible (hidden prices due to MAP - minimum advertised price)
		 */
		UNACCESSIBLE((byte) 3),

		/**
		 * no data available due to invalid or deprecated asin, or other issues
		 */
		INVALID((byte) 4),

		/**
		 * Product is a parent ASIN. No product data accessible, variationCSV is set
		 */
		VARIATION_PARENT((byte) 5);

		public final byte code;

		ProductType(byte code) {
			this.code = code;
		}

		public static final ProductType[] values = ProductType.values();

		public static ProductType fromValue(int value) throws IllegalArgumentException {
			try {
				return ProductType.values[value];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Unknown enum value: " + value);
			}
		}

		@Override
		public String toString() {
			return String.valueOf(code);
		}
	}


	@Override
	public String toString() {
		return gson.toJson(this);
	}
}