package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

/**
 * Represents a Tracking Object

 "metaData": String,
 "thresholdValues": TrackingThresholdValue array,
 "notifyIf": TrackingNotifyIf array,
 "notificationType": Boolean array,
 "notificationCSV": Integer array,
 "individualNotificationInterval": Integer
 */
public class Tracking {

	/**
	 * The tracked product ASIN
	 */
	public String asin = null;

	/**
	 * Creation date of the tracking in {@link KeepaTime} minutes
	 */
	public int createDate;

	/**
	 * The time to live in hours until the tracking expires and is deleted. When setting the value through the _Add Tracking_ request it is in relation to the time of request,
	 * when retrieving the tracking object it is relative to the `createDate`. Possible values:<br>
	 * <blockquote>any positive integer: time to live in hours<br>
	 * 0: never expires<br>
	 * any negative integer (only when setting the value):<br>
	 * <blockquote>tracking already exists: keep the original `ttl`<br>tracking is new: use the absolute value as `ttl`</blockquote>
	 * </blockquote>
	 */
	public int ttl;

	/**
	 * Trigger a notification if tracking expires or is removed by the system (e.g. product deprecated)
	 */
	public boolean expireNotify;

	/**
	 * The main Amazon locale of this tracking determines the currency used for all desired prices. <br>
	 * Integer value for the Amazon locale {@link AmazonLocale}
	 */
	public byte mainDomainId;

	/**
	 * Contains all settings for price or value related tracking criteria
	 */
	public TrackingThresholdValue[] thresholdValues;

	/**
	 * Contains specific, meta tracking criteria, like out of stock.
	 */
	public TrackingNotifyIf[] notifyIf;

	/**
	 * Determines through which channels we will send notifications.<br>
	 * Uses NotificationType indexing {@link NotificationType}. True means the channel will be used.
	 */
	public boolean[] notificationType;

	/**
	 * A history of past notifications of this tracking. Each past notification consists of 5 entries, in the format:<br>
	 * [{@link AmazonLocale}, {@link Product.CsvType}, {@link NotificationType}, {@link TrackingNotificationCause}, {@link KeepaTime}]
	 */
	public int[] notificationCSV;

	/**
	 * A tracking specific rearm timer.<br>
	 * -1 = use default notification timer of the user account (changeable via the website settings)
	 * 0 = never notify a desired price more than once
	 * larger than 0 = rearm the desired price after x minutes.
	 */
	public int individualNotificationInterval;

	/**
	 * Whether or not the tracking is active. A tracking is automatically deactivated if the corresponding API account is no longer sufficiently paid for.
	 */
	public boolean isActive;

	/**
	 * The update interval, in hours. Determines how often our system will trigger a product update. A setting of 1 hour will not trigger an update exactly every 60 minutes, but as close to that as it is efficient for our system. Throughout a day it will be updated 24 times, but the updates are not perfectly distributed.
	 */
	public int updateInterval;

	/**
	 * The meta data of this tracking (max length is 500 characters). Used to assign some text to this tracking, like a user reference or a memo.
	 */
	public String metaData;

	/**
	 * Available notification channels
	 */
	public enum NotificationType {
		EMAIL, TWITTER, FACEBOOK_NOTIFICATION, BROWSER, FACEBOOK_MESSENGER_BOT, API, MOBILE_APP;
		public static final NotificationType[] values = NotificationType.values();

	}

	/**
	 * The cause that triggered a notification
	 */
	public enum TrackingNotificationCause {
		EXPIRED, DESIRED_PRICE, PRICE_CHANGE, PRICE_CHANGE_AFTER_DESIRED_PRICE, OUT_STOCK, IN_STOCK, DESIRED_PRICE_AGAIN;
		public static final TrackingNotificationCause[] values = TrackingNotificationCause.values();
	}


	/**
	 * Available notification meta trigger types
	 */
	public enum NotifyIfType {
		OUT_OF_STOCK, BACK_IN_STOCK;
		public static final NotifyIfType[] values = NotifyIfType.values();
	}

	/**
	 * Represents a desired price - a {@link Product.CsvType} of a specific {@link AmazonLocale} to be monitored
	 */
	public static class TrackingThresholdValue {

		public TrackingThresholdValue(AmazonLocale domainId, Product.CsvType csvType, int thresholdValue, boolean isDrop, Integer minDeltaAbsolute, Integer minDeltaPercentage, Boolean deltasAreBetweenNotifications){
			this.thresholdValue = thresholdValue;
			this.isDrop = isDrop;
			this.domain = (byte) domainId.ordinal();
			this.csvType = csvType.index;
			this.minDeltaAbsolute = minDeltaAbsolute;
			this.minDeltaPercentage = minDeltaPercentage;
			this.deltasAreBetweenNotifications = deltasAreBetweenNotifications == null ? false : deltasAreBetweenNotifications;
		}
		/**
		 * The history of threshold values (or desired prices). Only for existing tracking!<br>
		 * Format: [{@link KeepaTime}, value]
		 */
		public int[] thresholdValueCSV;

		/**
		 * The threshold value (or desired price). Only for creating a tracking!
		 */
		public int thresholdValue;

		/**
		 * Integer value of the {@link AmazonLocale} this threshold value belongs to. Regardless of the locale, the threshold value is always in the currency of the mainDomainId.
		 */
		public byte domain;

		/**
		 * Integer value of the {@link Product.CsvType} for this threshold value
		 */
		public int csvType;

		/**
		 * Whether or not this tracking threshold value tracks value drops (true) or value increases (false)
		 */
		public boolean isDrop;

		/**
		 * not yet available.
		 */
		public Integer minDeltaAbsolute;

		/**
		 * not yet available.
		 */
		public Integer minDeltaPercentage;

		/**
		 * not yet available.
		 */
		public boolean deltasAreBetweenNotifications;
	}

	public static class TrackingNotifyIf {

		public TrackingNotifyIf(AmazonLocale domainId, Product.CsvType csvType, NotifyIfType notifyIfType){
			this.domain = (byte) domainId.ordinal();
			this.csvType = csvType.index;
			this.notifyIfType = notifyIfType.ordinal();
		}

		/**
		 * Integer value of the {@link AmazonLocale} for this NotifyIf
		 */
		public byte domain;

		/**
		 * The {@link Product.CsvType} for this threshold value
		 */
		public int csvType;

		/**
		 * The {@link NotifyIfType}
		 */
		public int notifyIfType;
	}
}
