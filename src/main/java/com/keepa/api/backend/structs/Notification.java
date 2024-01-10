package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

/**
 * Represents a price alert
 */
public class Notification {

	/**
	 * The notified product ASIN
	 */
	public String asin = null;

	/**
	 * Title of the product. Caution: may contain HTML markup in rare cases.
	 */
	public String title;

	/**
	 * The main image name of the product. Full Amazon image path:<br>
	 * https://m.media-amazon.com/images/I/_image name_
	 */
	public String image;

	/**
	 * Creation date of the notification in {@link KeepaTime} minutes
	 */
	public int createDate;

	/**
	 * The main Amazon locale of the tracking which determines the currency used for all prices of this notification.<br>
	 * Integer value for the Amazon locale {@link AmazonLocale}
	 */
	public byte domainId;

	/**
	 * The Amazon locale which triggered the notification.<br>
	 * Integer value for the Amazon locale {@link AmazonLocale}
	 */
	public byte notificationDomainId;

	/**
	 * The {@link Product.CsvType} which triggered the notification.
	 */
	public int csvType;

	/**
	 * The {@link Tracking.TrackingNotificationCause} of the notification.
	 */
	public int trackingNotificationCause;

	/**
	 * Contains the prices / values of the product of the time this notification was created.
	 * <p>Uses {@link Product.CsvType} indexing.</p>
	 * The price is an integer of the respective Amazon locale's smallest currency unit (e.g. euro cents or yen).
	 * If no offer was available in the given interval (e.g. out of stock) the price has the value -1.
	 */
	public int[] currentPrices;

	/**
	 * States through which notification channels ({@link Tracking.NotificationType}) this notification was delivered.
	 */
	public boolean[] sentNotificationVia;

	/**
	 * The meta data of the tracking.
	 */
	public String metaData;
}
