package com.keepa.api.backend.structs;


import com.keepa.api.backend.helper.KeepaTime;

import java.util.HashMap;

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
     * The ASIN of the parent product (if the product has variations, otherwise null)
     */
    public String parentAsin = null;

    /**
     * The history of the parentAsin field. This array follows the format: [Keepa time in minutes, previous parent ASIN, ...].<br>
     * The included timestamp indicates when the previously associated parent ASIN ceased to be valid.<br>
     * For the current parent ASIN, use the parentAsin field.<br><br>
     * To convert a Keepa minute timestamp into an uncompressed, Unix epoch time timestamp, use the {@link KeepaTime#keepaMinuteToUnixInMillis(int)} method.<br>
     * null if the parentAsin field never changed.
     */
    public String[] parentAsinHistory = null;

    /**
     * Comma separated list of variation ASINs of the product (if the product is a parent ASIN, otherwise null)
     * @deprecated use the field variations instead
     */
    public String variationCSV = null;

    /**
     * A list of UPC assigned to this product. The first index is the primary UPC. null if not available.
     */
    public String[] upcList = null;

    /**
     * A list of EAN assigned to this product. The first index is the primary EAN. null if not available.
     */
    public String[] eanList = null;

    /**
     * A list of GTIN assigned to this product. The first index is the primary GTIN. null if not available.
     */
    public String[] gtinList = null;



    /**
     * A list of historical or out of stock variations. Requires the historical-variations parameter.
     */
    public String[] historicalVariations = null;

    /**
     * Comma separated list of image names of the product. Full Amazon image path:<br>
     * https://m.media-amazon.com/images/I/_image name_
     * @deprecated use the field images instead
     */
    public String imagesCSV = null;

    /**
     * Provides metadata for images associated with the product.
     * <p>
     * This field is an array of {@link Image} objects, each of which may contain metadata for up to two resolutions
     * (large and medium) of the same image, if available. These images are typically used in the product image carousel
     * on platforms such as Amazon. This field is {@code null} if no image data is available.
     * <p>
     * Each {@code Image} object includes:
     * <ul>
     *     <li>{@code l} (String): Filename of the large image.</li>
     *     <li>{@code lH} (Short): Height of the large image, in pixels.</li>
     *     <li>{@code lW} (Short): Width of the large image, in pixels.</li>
     *     <li>{@code m} (String): Filename of the medium image.</li>
     *     <li>{@code mH} (Short): Height of the medium image, in pixels.</li>
     *     <li>{@code mW} (Short): Width of the medium image, in pixels.</li>
     * </ul>
     * <p>
     * Example:
     * <pre>{@code
     * "images": [{
     *     "l": "81bRlmLRyPL.jpg",
     *     "lH": 1500,
     *     "lW": 1500,
     *     "m": "g1dlEmb2mq3.jpg",
     *     "mH": 500,
     *     "mW": 500
     * }]
     * }</pre>
     * <p>
     * Full Amazon image path format: {@code https://m.media-amazon.com/images/I/<image filename>}
     */
    public Image[] images = null;

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
     * States the time the item was first listed on Amazon, in Keepa Time minutes.<br>
     * It is updated in conjunction with the offers request, but always accessible.<br>
     * This timestamp is only available for some products. If not available the field has the value 0.
     * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
     */
    public int listedSince = 0;

    /**
     * An item's brand. null if not available.
     */
    public String brand = null;

    /**
     * The item's productGroup. null if not available.
     */
    public String productGroup = null;

    /**
     * The item's partNumber. null if not available.
     */
    public String partNumber = null;

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
     * The item's format. null if not available.
     */
    public String format = null;

    /**
     * The item’s author. null if not available.
     *
     * @deprecated use the field contributors instead
     */
    public String author = null;

    /**
     * The item’s binding. null if not available. If the item is not a book it is usually the product category instead.
     */
    public String binding = null;

    /**
     * Represents the category tree as an ordered array of CategoryTreeEntry objects.
     */
    public CategoryTreeEntry[] categoryTree = null;

    /**
     * The number of items of this product. -1 if not available.
     */
    public int numberOfItems = -1;

    /**
     * The number of pages of this product. -1 if not available.
     */
    public int numberOfPages = -1;

    /**
     * The item’s publication date in one of the following three formats:<br>
     * YYYY or YYYYMM or YYYYMMDD (Y= year, M = month, D = day)<br>
     * -1 if not available.<br><br>
     * Examples:<br>
     * 1978 = the year 1978<br>
     * 200301 = January 2003<br>
     * 20150409 = April 9th, 2015
     */
    public int publicationDate = -1;

    /**
     * The item’s release date in one of the following three formats:<br>
     * YYYY or YYYYMM or YYYYMMDD (Y= year, M = month, D = day)<br>
     * -1 if not available.<br><br>
     * Examples:<br>
     * 1978 = the year 1978<br>
     * 200301 = January 2003<br>
     * 20150409 = April 9th, 2015
     */
    public int releaseDate = -1;

    /**
     * An item can have one or more languages. Each language entry has a name and a type.
     * Some also have an audio format. null if not available.<br><br>
     * Examples:<br>
     * [ [ “English”, “Published” ], [ “English”, “Original Language” ] ]<br>
     * With audio format:<br>
     * [ [ “Englisch”, “Originalsprache”, “DTS-HD 2.0” ], [ “Deutsch”, null, “DTS-HD 2.0” ] ]
     */
    public String[][] languages = null;

    /**
     * The contributors of the item. A contributor can be an author, actor, director, etc. Each contributor entry has a name and a role type.<br><br>
     * Example:<br>
     * [ [ “Vin Diesel”, “actor” ] ]
     */
    public String[][] contributors = null;

    /**
     * A list of the product features / bullet points. null if not available. <br>
     * An entry can contain HTML markup in rare cases. We currently limit each entry to a maximum of 1000 characters<br>
     * (if the feature is longer it will be cut off). This limitation may change in the future without prior notice.
     */
    public String[] features = null;

    /**
     * A description of the product. null if not available. Most description contain HTML markup.<br>
     * We limit the product description to a maximum of 2000 characters (if the description is<br>
     * longer it will be cut off). This limitation may change in the future without prior notice.
     */
    public String description = null;

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
     * The item's height in millimeter. 0 or -1 if not available.
     */
    public int itemHeight = -1;

    /**
     * The item's length in millimeter. 0 or -1 if not available.
     */
    public int itemLength = -1;

    /**
     * The item's width in millimeter. 0 or -1 if not available.
     */
    public int itemWidth = -1;

    /**
     * The item's weight in gram. 0 or -1 if not available.
     */
    public int itemWeight = -1;

    /**
     * Contains the lowest priced matching eBay listing Ids.
     * Always contains two entries, the first one is the listing id of the lowest priced listing in new condition,
     * the second in used condition. null or 0 if not available.<br>
     * Example: [ 273344490183, 0 ]
     */
    public long[] ebayListingIds = null;

    /**
     * Indicates if the item is considered to be for adults only.
     */
    public boolean isAdultProduct = false;

    /**
     * Whether or not the product is eligible for trade-in.
     */
    public boolean isEligibleForTradeIn = false;

    /**
     * @deprecated use the field referralFeePercentage instead
     */
    public Integer referralFeePercent = null;

    /**
     * The variable closing fee. Fees are integers of the respective Amazon locale’s smallest currency unit (e.g. euro cents or yen). null if not available.
     * Example: 81
     */
    public Integer variableClosingFee = null;

    /**
     * The product listing URL slug.
     * Example: Ring-Video-Doorbell-Satin-Nickel-2020-Release
     */
    public String urlSlug = null;

    /**
     * The ingredient list of the product. null if not available.
     * Example: Purified Carbonated Water, Natural Flavors
     */
    public String ingredients = null;

    /**
     * True if this product is an Amazon Haul product. null otherwise.
     * Example: true
     */
    public Boolean isHaul;

    /**
     * The referral fee percent is determined by either the current price or, in the absence of a current offer, the previous one. If neither of these prices is available for reference, the fee percent is calculated based on a standard sales price of 100.00. *null* if not available.
     * Example: 12
     */
    public Double referralFeePercentage = null;

    /**
     * States the last time we have updated the monthlySold field, in Keepa Time minutes. null if the monthlySold has no value.
     * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
     */
    public int lastSoldUpdate = 0;

    /**
     * How often this product was bought in the past month. This field represents the bought past month metric found on Amazon search result pages. It is not an estimate. null if it has no value. Most ASINs do not have this value set. The value is variation specific.
     * Example: 1000 - the ASIN was bought at least 1000 times in the past month.
     */
    public int monthlySold = 0;

    /**
     * Contains historical values of the monthlySold field. null if it has no value.
     */
    public int[] monthlySoldHistory = null;

    /**
     * Whether or not the product is eligible for super saver shipping by Amazon (not FBA).
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
    public int lastPriceChange = 0;

    /**
     * States the last time we have updated the eBay prices for this product, in Keepa Time minutes.<br>
     * If no matching products were found the integer is negative.<br>
     * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
     */
    public int lastEbayUpdate = 0;

    /**
     * The most recent update of the stock data for this product’s offers, in Keepa Time minutes.<br>
     * Has the value 0 unless the stock parameter was used and stock data was collected at least once.
     * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
     */
    public int lastStockUpdate = 0;

    /**
     * States the last time we have updated the product rating and review count, in Keepa Time minutes.<br>
     * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
     */
    public int lastRatingUpdate = 0;

    /**
     * Keepa product type {@link Product.ProductType}. Must always be evaluated first.
     */
    public byte productType = 0;

    /**
     * The item’s type. null if not available.
     */
    public String type = null;

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
     * Example:<br> [ 3, 5, 2, 18, 15 ] - The offer with the array index 3 of the offers field is currently the first <br>
     * one listed on the offer listings page on Amazon, followed by the offer with the index 5, and so on.<br><br>
     * Example with duplicates:<br> [ 3, 5, 2, 18, 5 ] - The second offer, as listed on Amazon, is a lower priced duplicate <br>
     * of the 6th offer on Amazon. The lower priced one is included in the offers field at index 5.
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
     * Optional field. Only set if the offers or buybox parameter was used in the Product Request.
     * A history of the used buy box winners, containing the sellerIds 159, offer sub-condition and FBA status in the format:
     * Keepa time minutes, seller id, condition, isFBA, […].
     * If no seller qualified for the used buy box the sellerId "" (empty String) is used.
     * <p>
     * condition can have the following values:
     * “2” - Used - Like New, “3” - Used - Very Good, “4” - Used - Good, “5” - Used - Acceptable
     * isFBA is either “1” - offer is FBA or “0” - offer is merchant fulfilled.
     * Example: [“2860926”, “ATVPDKIKX0DER”, “4”, “1”, …]
     * <p>Use {@link KeepaTime#keepaMinuteToUnixInMillis(String)} (long)} to get an uncompressed timestamp (Unix epoch time).</p>
     */
    public String[] buyBoxUsedHistory = null;

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
     * Suggested Lower Price for the Buy Box, if the buy box is suppressed.
     */
    public Integer suggestedLowerPrice = null;


    /**
     * Competitive Price Threshold (CPT) for the Buy Box, if the buy box is suppressed.
     */
    public Integer competitivePriceThreshold = null;

    /**
     * If buyBoxEligibleOfferCounts is available, it represents an array of integers, each entry indicating the total number of offers eligible for the Buy Box across specified offer conditions and fulfillment channels. This array contains eight elements, indexed as follows:<br>
     * 0: New FBA<br>
     * 1: New FBM<br>
     * 2: Used FBA<br>
     * 3: Used FBM<br>
     * 4: Collectible FBA<br>
     * 5: Collectible FBM<br>
     * 6: Refurbished FBA<br>
     * 7: Refurbished FBM<br>
     */
    public int[] buyBoxEligibleOfferCounts = null;

    /**
     * The hazardous material type of this product, if applicable.
     */
    public HazardousMaterial[] hazardousMaterials = null;

    /**
     * Only valid if the offers parameter was used in the Product Request. Boolean indicating if the system was able to retrieve fresh offer information.
     */
    public boolean offersSuccessful = false;

    /**
     * One or two “Frequently Bought Together” ASINs. null if not available. Field is updated when the offers parameter was used.
     */
    public String[] frequentlyBoughtTogether = null;

    /**
     * True if this product is an Amazon Merch on Demand product
     */
    public Boolean isMerchOnDemand = null;

    /**
     * Indicates if the item is heat sensitive (e.g. meltable).
     */
    public Boolean isHeatSensitive = null;

    /**
     * Indicates the return rate of this product.<br>
     * - `null` if the return rate is unavailable or average.<br>
     * - `1` for a low return rate.<br>
     * - `2` for a high return rate.
     */
    public Byte returnRate = null;

    /**
     * Contains current promotions for this product. Only Amazon US promotions by Amazon (not 3rd party) are collected. In rare cases data can be incomplete.
     */
    public PromotionObject[] promotions = null;

    /**
     * Contains the dimension attributes for up to 50 variations of this product. Only available on parent ASINs.
     */
    public VariationObject[] variations = null;

    /**
     * Availability of the Amazon offer {@link Product.AvailabilityType}.
     */
    public int availabilityAmazon = -1;

    /**
     * Contains coupon details if any are available for the product. null if not available.
     * Integer array with always two entries: The first entry is the discount of a one time coupon, the second is a subscribe and save coupon.
     * Entry value is either 0 if no coupon of that type is offered, positive for an absolute discount or negative for a percentage discount.
     * The coupons field is always accessible, but only updated if the offers parameter was used in the Product Request.
     * <p>Example:<br>
     * [ 200, -15 ] - Both coupon types available: $2 one time discount or 15% for subscribe and save.<br>
     * [ -7, 0 ] - Only one time coupon type is available offering a 7% discount.
     * </p>
     */
    public int[] coupon = null;

    /**
     * Whether or not the current new price is MAP restricted. Can be used to differentiate out of stock vs. MAP restricted prices (as in both cases the current price is -1).
     */
    public boolean newPriceIsMAP = false;

    /**
     * FBA fees for this product. If FBA fee information has not yet been collected for this product the field will be null.
     */
    public FBAFeesObject fbaFees = null;

    /**
     * Contains subcategory rank histories. Each key represents the categoryId of the rank with the history in the corresponding value.
     */
    public HashMap<Long, int[]> salesRanks = null;

    /**
     * The category node id of the main sales rank. -1 if not available.
     */
    public long salesRankReference = -1;

    /**
     * The category node id history of the main sales rank (format: timestamp, categoryId, […]).  null if not available.
     */
    public long[] salesRankReferenceHistory = null;


    /**
     * Rental details
     */
    public String rentalDetails = null;

    /**
     * Rental prices
     */
    public RentalObject rentalPrices = null;

    /**
     * Rental seller id
     */
    public String rentalSellerId = null;


    /**
     * Amazon offer shipping delay. Integer array with 2 entries, indicating min and max shipping delay in hours.
     */
    public int[] availabilityAmazonDelay = null;

    /**
     * Audience rating. The rating suggests the age for which the media is appropriate.
     * <p>Example: PG-13 (Parents Strongly Cautioned)</p>
     */
    public String audienceRating = null;

    /**
     * Unit Count information
     */
    public UnitCountObject unitCount = null;

    /**
     * The scent of the product. Describes the fragrance associated with the product.
     * <p>Example: "Lavender"</p>
     */
    public String scent = null;

    /**
     * A brief description of the product.
     * <p>Example: "A soothing lavender-scented candle."</p>
     */
    public String shortDescription = null;

    /**
     * Active ingredients present in the product.
     * <p>Example: "Lavender essential oil, Soy wax"</p>
     */
    public String activeIngredients = null;

    /**
     * Special ingredients used in the product that may have unique properties.
     * <p>Example: "Beeswax blend, Natural dyes"</p>
     */
    public String specialIngredients = null;

    /**
     * The form or physical state of the item.
     * <p>Example: "Liquid", "Solid", "Gel"</p>
     */
    public String itemForm = null;

    /**
     * Keywords describing the type or category of the item.
     * <p>Example: "body-lotions"</p>
     */
    public String itemTypeKeyword = null;

    /**
     * Recommended uses for the product to guide customers.
     * <p>Example: "Aromatherapy, Home Decoration"</p>
     */
    public String recommendedUsesForProduct = null;

    /**
     * The pattern or design featured on the product.
     * <p>Example: "Striped", "Floral"</p>
     */
    public String pattern = null;

    /**
     * The store name of the item’s brand. null if not available.
     * <p>Example: Hot Wheels</p>
     */
    public String brandStoreName = null;

    /**
     * The brand store URL path. null if not available. To get the full URL, prepend the Amazon domain of the respective locale (e.g. https//www.amazon.com).
     * <p>Example: /stores/LEGO/page/017EF856-965D-4B56-A171-EA61CAFF45DD</p>
     */
    public String brandStoreUrl = null;

    /**
     * The brand store Name from the URL path. null if not available.
     * <p>Example: LEGO (from the URL: /stores/LEGO/page/017EF856-965D-4B56-A171-EA61CAFF45DD)</p>
     */
    public String brandStoreUrlName = null;

    /**
     * Provides metadata for videos associated with the product.
     *
     * <p>The {@code videos} parameter is mandatory for access. Each object in the array represents
     * the metadata for a single video. Metadata can be retrieved for all image carousel videos
     * and up to 10 community videos from the product listing’s Videos section. To request live
     * data for this field, the {@code offers} parameter must also be included. Returns {@code null}
     * if unavailable.</p>
     *
     * <p>Example:</p>
     * <pre>
     * "videos": [{
     *     "title": "Compressed Air Duster",
     *     "image": "31XBcVI7oTL.jpg",
     *     "duration": 36,
     *     "creator": "Seller",
     *     "name": "Innovation",
     *     "url": "https://m.media-amazon.com/images/S/vse-vms-transcoding-artifact-us-east-1-prd/d8d8f97e-aa42-42d2-aa4c-6ab1b006edb5/default.jobtemplate.hls.m3u8"
     * }]
     * </pre>
     */
    public Video[] videos = null;

    /**
     * Provides A+ Content of this product.
     *
     * <p>The {@code aplus} parameter is mandatory for access. To request live
     * data for this field, the {@code offers} parameter must also be included. Returns {@code null}
     * if unavailable.</p>
     */
    public APlus[] aPlus = null;


    /**
     * Specific uses for the product, providing detailed applications.
     * <p>Example: {"Relaxation", "Decoration"}</p>
     */
    public String[] specificUsesForProduct = null;

    /**
     * A categorization name of products that behave similarly,<br>influencing how sales rank is calculated and displayed,<br>especially for product variations.
     * <p>Example: {"apparel", "kitchen"}</p>
     */
    public String websiteDisplayGroupName = null;

    /**
     * A categorization of products that behave similarly,<br>influencing how sales rank is calculated and displayed,<br>especially for product variations.
     * <p>Example: {"apparel_display_on_website", "kitchen_display_on_website"}</p>
     */
    public String websiteDisplayGroup = null;

    /**
     * For books only: An array listing other available formats or bindings of a book, excluding the current format.
     */
    public Format[] formats = null;

    /**
     * The highest business discount percentage, if available.
     * <p>Example: 14</p>
     */
    public Short businessDiscount = null;

    /**
     * KeepaTime timestamp of the last business discount percentage update.
     */
    public Integer lastBusinessDiscountUpdate = null;

    /**
     * Safety warnings associated with the product to inform users of potential hazards.
     * <p>Example: "Keep away from open flames."</p>
     */
    public String safetyWarning = null;

    /**
     * Benefits of using the product, highlighting its advantages.
     * <p>Example: "Promotes relaxation and stress relief."</p>
     */
    public String productBenefit = null;

    /**
     * Indicates whether batteries are required for the product to function.
     * <p>Example: true or false</p>
     */
    public Boolean batteriesRequired = null;

    /**
     * Indicates whether batteries are included with the product upon purchase.
     * <p>Example: true or false</p>
     */
    public Boolean batteriesIncluded = null;

    /**
     * Keywords describing the target audience for the product.
     * <p>Example: "Adults, Gift for Her"</p>
     */
    public String targetAudienceKeyword = null;

    /**
     * The style of the product, which may influence its aesthetic appeal.
     * <p>Example: "Modern", "Vintage"</p>
     */
    public String style = null;

    /**
     * Components included with the product, detailing what is provided upon purchase.
     * <p>Example: "Candle, Wick, Box"</p>
     */
    public String includedComponents = null;

    /**
     * Material of the product, specifying the primary substances used in its construction.
     * <p>Example: "Soy Wax, Cotton"</p>
     */
    public String material = null;


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
         * Price history of the lowest new price on the respective eBay locale, including shipping costs.
         */
        EBAY_NEW_SHIPPING(28, true, false, true, false),

        /**
         * Price history of the lowest used price on the respective eBay locale, including shipping costs.
         */
        EBAY_USED_SHIPPING(29, true, false, true, false),

        /**
         * The trade in price history. Amazon trade-in is not available for every locale.
         */
        TRADE_IN(30, true, false, false, false),

        /**
         * Rental price history. Requires use of the rental and offers parameter. Amazon Rental is only available for Amazon US.
         */
        RENT(31, true, false, false, true),


        /**
         * The price history of the Used buy box (any sub-condition). If no offer qualified for the used buy box the price has the value -1. Including shipping costs.
         */
        BUY_BOX_USED_SHIPPING(32, true, true, true, true),

        /**
         * Price history of the lowest Prime exclusive New offer.
         */
        PRIME_EXCL(33, true, true, false, true);

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

    public enum AvailabilityType {
        /**
         * No Amazon offer exists
         */
        NO_OFFER(-1),

        /**
         * Amazon offer is in stock and shippable
         */
        NOW(0),

        /**
         * Amazon offer is currently not in stock but will be in the future - pre-order
         */
        PREORDERABLE(1),

        /**
         * Amazon offer availability is "unknown"
         */
        UNKNOWN(2),

        /**
         * Amazon offer is currently not in stock but will be in the future - back-order
         */
        BACKORDERABLE(3),

        /**
         * Amazon offer availability is delay. Check availabilityAmazonDelay field for details.
         */
        DELAYED(4);

        public int code;

        AvailabilityType(int i) {
            code = i;
        }

        private static final AvailabilityType[] values = AvailabilityType.values();

        public static AvailabilityType fromValue(int value) throws IllegalArgumentException {
            try {
                return AvailabilityType.values[value];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Unknown AvailabilityType value: " + value);
            }
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


    public enum VideoCreatorType {
        Main,
        Customer,
        Seller,
        Influencer,
        Vendor,
        ThirdParty,
        Amazon,
        Merchant,
        Brand
    }

    public class CategoryTreeEntry {
        public long catId;
        public String name;
    }

    public class HazardousMaterial {
        public String aspect;
        public String value;
    }


    public static class RentalObject {
        public int initialPrice = -1;
        public int shortExtnPrice = -1;
        public int longExtnPrice = -1;
        public int fullPrice = -1;
    }

    public static class PromotionObject {
        /**
         * The type of promotion
         **/
        public PromotionType type = null;
        public int amount = -1;
        public int discountPercent = -1;
        public Integer snsBulkDiscountPercent = null;
    }

    public static class UnitCountObject {
        public Double unitValue;
        public String unitType;
        public Double eachUnitCount;
    }

    public static class Video {
        public String title;

        /**
         * Full Amazon image path:<br>
         * https://m.media-amazon.com/images/I/_image_
         */
        public String image;

        /**
         * in seconds
         */
        public short duration;
        public VideoCreatorType creator;
        public String name;
        public String url;
    }

    public static class APlus {
        public APlusModule[] module;
        public boolean fromManufacturer;
    }

    public static class Image {

        /**
         * The filename of the large image.
         */
        public String l;

        /**
         * The height of the large image in pixels.
         */
        public Short lH;

        /**
         * The width of the large image in pixels.
         */
        public Short lW;

        /**
         * The filename of the medium image.
         */
        public String m;

        /**
         * The height of the medium image in pixels.
         */
        public Short mH;

        /**
         * The width of the medium image in pixels.
         */
        public Short mW;
    }

    public static class Format {
        /**
         * The ASIN of the format.
         */
        public String asin = null;

        /**
         * The type of format, which can be one of the following: Kindle, Paperback, Hardcover, Audiobook, or Spiral-bound.
         */
        public String format = null;
    }

    public static class APlusModule {
        public String[] text;
        public String[] image;
        public String[] video;
    }

    public static class VariationObject {
        /**
         * Variation ASIN
         **/
        public String asin = null;
        /**
         * This variation ASIN's dimension attributes
         **/
        public VariationAttributeObject[] attributes = null;
    }

    public static class VariationAttributeObject {
        /**
         * dimension type, e.g. Color
         **/
        public String dimension = null;
        /**
         * dimension value, e.g. Red
         **/
        public String value = null;
    }

    /**
     * Contains detailed FBA fees. If the total fee is 0 the product does not have (valid) dimensions and thus the fee can not be calculated.
     */
    public static class FBAFeesObject {
        public int storageFee;
        public int storageFeeTax;
        public int pickAndPackFee;
        public int pickAndPackFeeTax;
    }

    public enum PromotionType {
        SNS("SNS"),
        PrimeExclusive("PrimeExclusive");

        public final String type;

        PromotionType(String name) {
            type = name;
        }

        public String toString() {
            return type;
        }
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }


}