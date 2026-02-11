package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

/**
 * Contains <b>variation specific</b> review and rating counts histories
 * as well as a last update timestamp.
 * <b>null</b> if not available.
 * It is not possible to force an update to the reviews object data.
 * For non-variation specific ratings and review data access the csv field.
 * Accessible only if the <i>rating</i> parameter was used in the Product Request.
 * The ratingCount history has not been updated since April 9th 2025 as that data point was removed by Amazon.
 */
public class ReviewObject {
	/**
	 * The last time the information was updated, in Keepa Time minutes.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int lastUpdate = 0;
	
	/**
	 * Contains historical values of the ratingCount field. null if it has no value.
	 * The most recent value is at the end of the array. The first value is the oldest.
	 * Data points alternate in this pattern: <code>[ keepaTime, ratingCount, ... ]</code>
	 * The ratingCount history has not been updated since April 9th 2025 as that data point was removed by Amazon.
	 */
	public int[] ratingCount = null;
	
	/**
	 * Contains historical values of the reviewCount field. null if it has no value.
	 * The most recent value is at the end of the array. The first value is the oldest.
	 * Data points alternate in this pattern: <code>[ keepaTime, ratingCount, ... ]</code>
	 */
	public int[] reviewCount = null;
	
	/**
	 * Convenience method to get the most recent review count value. Returns 0 if the review count history is not available or empty.
	 * @return The most recent review count value, or 0 if not available.
	 * @see #reviewCount
	 */
	public int getCurrentReviewCount() {
		if ( reviewCount == null || reviewCount.length < 2 ) {
			return 0;
		}
		return reviewCount[reviewCount.length - 1];
	}
	
}
