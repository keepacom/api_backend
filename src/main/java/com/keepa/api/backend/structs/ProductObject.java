package com.keepa.api.backend.structs;


import com.keepa.api.backend.helper.KeepaTime;
import com.google.gson.Gson;

public final class ProductObject {

	/**
	 * The ASIN of the product
	 */
	public String asin = null;

	/**
	 * The domainId of the products Amazon locale
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
	 * Comma separated list of image names of the product. Full Amazon image path:
	 * https://images-na.ssl-images-amazon.com/images/I/<image name>
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
	 * States the time we have started tracking this product, in Keepa Time minutes.
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
	 * States the last time we have updated the information for this product, in Keepa Time minutes.
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastUpdate = 0;

	/**
	 * States the last time we have registered a price change (any price kind), in Keepa Time minutes.
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastPriceChange = 0; // minutes Keepa Epoch

	/**
	 * Keepa product type {@link ProductObject.ProductType}. Must always be evaluated first.
	 */
	public byte productType = 0;

	/**
	 * Whether or not the product has reviews.
	 */
	public boolean hasReviews = false;

	/**
	 * Integer[][] - two dimensional price history array.
	 * First dimension: {@link ProductObject.CsvType}
	 * Second dimension:
	 * Each array has the format timestamp, price, […]. To get an uncompressed timestamp use {@link KeepaTime#keepaMinuteToUnixInMillis(int)}.
	 * Example: "csv[0]": [411180,4900, ... ]
	 * timestamp: 411180 => 1318510800000
	 * price: 4900 => $ 49.00 (if domainId is 5, Japan, then price: 4900 => ¥ 4900)
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
		AMAZON(0),

		/**
		 * Marketplace/3rd party New price history - Amazon is considered to be part of the marketplace as well,
		 * so if Amazon has the overall lowest new (!) price, the marketplace new price in the corresponding time interval
		 * will be identical to the Amazon price (except if there is only one marketplace offer).
		 * Shipping and Handling costs not included!
		 */
		NEW(1),

		/**
		 * Marketplace/3rd party Used price history
		 */
		USED(2),

		/**
		 * Sales Rank history. Not every product has a Sales Rank.
		 */
		SALES(3),

		/**
		 * List Price history
		 */
		LISTPRICE(4),

		/**
		 * Collectible Price history
		 */
		COLLECTIBLE(5),

		/**
		 * Refurbished Price history
		 */
		REFURBISHED(6),

		/**
		 * Placeholder. Do not use.
		 */
		NEW_SHIPPING(7),

		/**
		 * Placeholder. Do not use.
		 */
		GOLDBOX(8),

		/**
		 * Placeholder. Do not use.
		 */
		WAREHOUSE(9),

		/**
		 * Placeholder. Do not use.
		 */
		FBA(10),

		/**
		 * New offer count history
		 */
		COUNT_NEW(11),

		/**
		 * Used offer count history
		 */
		COUNT_USED(12),

		/**
		 * Refurbished offer count history
		 */
		COUNT_REFURBISHED(13),

		/**
		 * Collectible offer count history
		 */
		COUNT_COLLECTIBLE(14);


		public final int index;
		public static final CsvType[] values = CsvType.values();

		CsvType(int i) {
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
		Gson g = new Gson();
		return g.toJson(this);
	}
}