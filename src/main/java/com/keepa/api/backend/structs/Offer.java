package com.keepa.api.backend.structs;

import static com.keepa.api.backend.helper.Utility.gson;

/**
 * About:
 * The offer object represents a marketplace offer.
 * <p>
 * Returned by:
 * The offer object is returned by the Product Request using the optional offers parameter and is part of the Product Object.
 * <p>
 * Important to know:
 * It is impossible to update billions of marketplace offers on a regular basis. The product request's offers parameter determines how many offers we retrieve / update. We always fetch the best offers, as sorted by Amazon, in all conditions. If a product has more offers than requested, those will not be retrieved.
 * The order of offers constantly changes and we can retrieve a different amount of offers with each data retrieval. Because of this as well as the fact that we do keep a history of offers you will almost certainly encounter outdated offers. So the following is very important:
 * <p>
 * Evaluate the lastSeen field - only process fresh and active offers if you are not interested in past offers.
 * The history of an offer (its past prices and shipping costs) is often not without gaps. Evaluate the EXTRA_INFO_UPDATES csv-type of the product object to find out when we updated the offers. If you need complete coverage of (all) offers of a product you have to request it on a regular basis.
 * If there are almost identical offers - same seller, same condition, same shipping type and same condition text - we only provide access to the one with the cheapest price. We do not list duplicates.
 */
public class Offer {

	/**
	 * Unique id of this offer (in the scope of the product).
	 * Not related to the offerIds used by Amazon, as those are user specific and only valid for a short time.
	 * The offerId can be used to identify the same offers throughout requests.
	 * <p>
	 * Example: 4
	 */
	public int offerId;

	/**
	 * States the last time we have seen (and updated) this offer, in Keepa Time minutes.
	 * <p>
	 * Example: 2700145
	 */
	public int lastSeen = 0;

	/**
	 * The seller id of the merchant.
	 * <p>
	 * Example: A2L77EE7U53NWQ (Amazon.com Warehouse Deals)
	 */
	public String sellerId = null;

	/**
	 * Contains the current price and shipping costs of the offer as well as, if available, the offer's history.
	 * It has the format Keepa time minutes, price, shipping cost, [...].
	 * <p>
	 * The price and shipping cost are integers of the respective Amazon locale's smallest currency unit (e.g. euro cents or yen).
	 * If we were unable to determine the price or shipping cost they have the value -2.
	 * Free shipping has the shipping cost of 0.
	 * If an offer is not shippable or has unspecified shipping costs the shipping cost will be -1.
	 * To get the newest price and shipping cost access the last two entries of the array.<br>
	 * Most recent price: offerCSV[offerCSV.length - 2]<br>
	 * Most recent shipping cost: offerCSV[offerCSV.length - 1]
	 */
	public int[] offerCSV = null;

	/**
	 * The {@link OfferCondition} condition of the offered product. Integer value:
	 * <br>0 - Unknown: We were unable to determine the condition.
	 * <br>1 - New
	 * <br> 2 - Used - Like New
	 * <br>3 - Used - Very Good
	 * <br>4 - Used - Good
	 * <br>5 - Used - Acceptable
	 * <br>6 - Refurbished
	 * <br>7 - Collectible - Like New
	 * <br>8 - Collectible - Very Good
	 * <br>9 - Collectible - Good
	 * <br>10 - Collectible - Acceptable
	 * <br>Note: Open Box conditions will be coded as Used conditions.
	 */
	public byte condition = 0;

	/**
	 * The describing text of the condition.
	 * <p>
	 * Example: The item may come repackaged. Small cosmetic imperfection on top, [...]
	 */
	public String conditionComment = null;

	/**
	 * Whether or not this offer is available via Prime shipping. Can be used as a FBA ("Fulfillment by Amazon") indicator as well.
	 */
	public boolean isPrime;

	/**
	 * If the price of this offer is hidden on Amazon due to a MAP ("minimum advertised price") restriction.
	 * Even if so, the offer object will contain the price and shipping costs.
	 */
	public boolean isMAP;

	/**
	 * Indicating whether or not the offer is currently shippable.
	 * If not this could mean for example that it is temporarily out of stock or a pre-order.
	 */
	public boolean isShippable;

	/**
	 * Indicating whether or not the offer is an Add-on item.
	 */
	public boolean isAddonItem;

	/**
	 * Indicating whether or not the offer is a pre-order.
	 */
	public boolean isPreorder;

	/**
	 * Indicating whether or not the offer is a Warehouse Deal.
	 */
	public boolean isWarehouseDeal;

	/**
	 * Indicating whether or not our system identified that the offering merchant attempts to scam users.
	 */
	public boolean isScam;

	/**
	 * True if the seller is Amazon (e.g. "Amazon.com").
	 * <p>
	 * <b>Note:</b> Amazon's Warehouse Deals seller account or other accounts Amazon is maintaining under a different name are not considered to be Amazon.
	 */
	public boolean isAmazon;

	/**
	 * Whether or not this offer is fulfilled by Amazon.
	 */
	public boolean isFBA;

	/**
	 * A Prime exclusive offer can only be ordered if the buyer has an active Prime subscription.
	 */
	public boolean isPrimeExcl;

	/**
	 * Contains the available stock of this offer as well as, if available, the stock's history.
	 * It has the format Keepa time minutes, stock, [...].
	 * <p>
	 * Most recent stock: stockCSV[stockCSV.length - 1]
	 */
	public int[] stockCSV;


	public enum OfferCondition {
		UNKNOWN(0),
		NEW(1),
		USED_NEW(2), USED_VERY_GOOD(3), USED_GOOD(4), USED_ACCEPTABLE(5),
		REFURBISHED(6),
		COLLECTIBLE_NEW(7), COLLECTIBLE_VERY_GOOD(8), COLLECTIBLE_GOOD(9), COLLECTIBLE_ACCEPTABLE(10);

		public byte code;
		public static final OfferCondition[] values = OfferCondition.values();

		public static Product.CsvType getCorrespondingCsvType(OfferCondition oc) {
			Product.CsvType type = null;
			switch (oc) {
				case UNKNOWN:
				case NEW:
					break;
				case USED_NEW:
					type = Product.CsvType.USED_NEW_SHIPPING;
					break;
				case USED_VERY_GOOD:
					type = Product.CsvType.USED_VERY_GOOD_SHIPPING;
					break;
				case USED_GOOD:
					type = Product.CsvType.USED_GOOD_SHIPPING;
					break;
				case USED_ACCEPTABLE:
					type = Product.CsvType.USED_ACCEPTABLE_SHIPPING;
					break;
				case REFURBISHED:
					type = Product.CsvType.REFURBISHED_SHIPPING;
					break;
				case COLLECTIBLE_NEW:
					type = Product.CsvType.COLLECTIBLE_NEW_SHIPPING;
					break;
				case COLLECTIBLE_VERY_GOOD:
					type = Product.CsvType.COLLECTIBLE_VERY_GOOD_SHIPPING;
					break;
				case COLLECTIBLE_GOOD:
					type = Product.CsvType.COLLECTIBLE_GOOD_SHIPPING;
					break;
				case COLLECTIBLE_ACCEPTABLE:
					type = Product.CsvType.COLLECTIBLE_ACCEPTABLE_SHIPPING;
					break;
			}

			return type;
		}

		OfferCondition(int index) {
			code = (byte) index;
		}
	}

	@Override
	public String toString() {
		return gson.toJson(this);
	}
}
