package com.keepa.api.backend.structs;

/**
 * Required by the Add Tracking request.
 */
public class TrackingRequest {

	public TrackingRequest(String asin, AmazonLocale mainDomainId, int updateInterval){
		this.asin = asin;
		this.mainDomainId = (byte) mainDomainId.ordinal();
		this.updateInterval = updateInterval < 1 ? 1 : updateInterval;
	}

	/**
	 * The product ASIN to track
	 */
	public String asin;

	/**
	 * The time to live in hours until the tracking expires and is deleted.
	 * When setting the value through the _Add Tracking_ request it is in relation to the time of request. Possible values:<br>
	 * <blockquote>any positive integer: time to live in hours<br>
	 * 0: never expires<br>
	 * any negative integer:<br>
	 * <blockquote>tracking already exists: keep the original `ttl`<br>tracking is new: use the absolute value as `ttl`</blockquote>
	 * </blockquote>
	 */
	public int ttl = 24 * 365 * 2;

	/**
	 * Trigger a notification if tracking expires or is removed by the system (e.g. product deprecated)
	 */
	public boolean expireNotify = false;

	/**
	 * Whether or not all desired prices are in the currency of the mainDomainId. If false they will be converted.
	 */
	public boolean desiredPricesInMainCurrency = true;

	/**
	 * The main Amazon locale of this tracking determines the currency used for all desired prices. <br>
	 * Integer value for the Amazon locale {@link AmazonLocale}
	 */
	public byte mainDomainId;

	/**
	 * Contains all settings for price or value related tracking criteria
	 */
	public Tracking.TrackingThresholdValue[] thresholdValues = null;

	/**
	 * Contains specific, meta tracking criteria, like out of stock.
	 */
	public Tracking.TrackingNotifyIf[] notifyIf = null;

	/**
	 * Determines through which channels we will send notifications.<br>
	 * Uses NotificationType indexing {@link Tracking.NotificationType}. True means the channel will be used.
	 */
	public boolean[] notificationType = null;

	/**
	 * A tracking specific rearm timer.<br>
	 * -1 = use default notification timer of the user account (changeable via the website settings)
	 * 0 = never notify a desired price more than once
	 * larger than 0 = rearm the desired price after x minutes.
	 */
	public int individualNotificationInterval = -1;

	/**
	 * The update interval, in hours. Determines how often our system will trigger a product update. A setting of 1
	 * hour will not trigger an update exactly every 60 minutes, but as close to that as it is efficient for our system.
	 * Throughout a day it will be updated 24 times, but the updates are not perfectly distributed.<br>
	 * Possible values: Any integer between 0 and 25. Default is 1.
	 */
	public int updateInterval = 1;

	/**
	 * Meta data of this tracking (max length is 500 characters). You can use this to store any string with this tracking.
	 */
	public String metaData = null;
}
