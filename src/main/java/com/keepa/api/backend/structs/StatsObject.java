package com.keepa.api.backend.structs;

import com.keepa.api.backend.helper.KeepaTime;

/**
 * Contains statistic values.
 * Only set if the stats parameter was used in the Product Request. Part of the {@link ProductObject}
 */
public class StatsObject {

	/**
	 * Contains the prices / ranks of the product of the time we last updated it.
	 * <p>Uses {@link com.keepa.api.backend.structs.ProductObject.CsvType} indexing.</p>
	 * The price is an integer of the respective Amazon locale's smallest currency unit (e.g. euro cents or yen).
	 * If no offer was available in the given interval (e.g. out of stock) the price has the value -1.
	 */
	public int[] current = null;

	/**
	 * Contains the weighted mean for the interval specified in the product request's stats parameter.<br>
	 * <p>Uses {@link com.keepa.api.backend.structs.ProductObject.CsvType} indexing.</p>
	 * If no offer was available in the given interval or there is insufficient data it has the value -1.
	 */
	public int[] avg = null;

	/**
	 * Contains the lowest prices registered for this product. <br>
	 * First dimension uses {@link com.keepa.api.backend.structs.ProductObject.CsvType} indexing <br>
	 * Second dimension is either null, if there is no data available for the price type, or
	 * an array of the size 2 with the first value being the time of the extreme point (in Keepa time minutes) and the second one the respective extreme value.
	 * <br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int[][] min = null;

	/**
	 * Contains the highest prices registered for this product. <br>
	 * First dimension uses {@link com.keepa.api.backend.structs.ProductObject.CsvType} indexing <br>
	 * Second dimension is either null, if there is no data available for the price type, or
	 * an array of the size 2 with the first value being the time of the extreme point (in Keepa time minutes) and the second one the respective extreme value.<br>
	 * Use {@link KeepaTime#keepaMinuteToUnixInMillis(int)} (long)} to get an uncompressed timestamp (Unix epoch time).
	 */
	public int[][] max = null;
}
