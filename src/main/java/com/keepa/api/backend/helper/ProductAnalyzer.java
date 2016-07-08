package com.keepa.api.backend.helper;

/**
 * Provides methods to work on the Keepa price history CSV format.
 */
class ProductAnalyzer {

	/**
	 * finds the extreme point in the specified interval
	 * @param csv value/price history csv
	 * @param start start of the interval (keepa time minutes), can be 0.
	 * @param end end of the interval (keepa time minutes), can be in the future (Integer.MAX_VALUE).
	 * @param isMinimum whether to find the minimum or maximum
	 * @return extremePoint (value/price) in the given interval or -1 if no extreme point was found.
	 */
	public static int getExtremePointInInterval(int[] csv, int start, int end, boolean isMinimum) {
		if (csv == null || csv.length < 4 || csv[csv.length - 1] == -1 || csv[csv.length - 3] == -1)
			return -1;

		int extremeValue = -1;
		if (isMinimum)
			extremeValue = Integer.MAX_VALUE;

		for (int i = 0; i < csv.length; i += 2) {
			int date = csv[i];

			if (date <= start) continue;
			if (date >= end) break;
			if (csv[i + 1] == -1) continue;

			if (isMinimum)
				extremeValue = Math.min(extremeValue, csv[i + 1]);
			else
				extremeValue = Math.max(extremeValue, csv[i + 1]);
		}

		if (extremeValue == Integer.MAX_VALUE) return -1;
		return extremeValue;
	}


	/**
	 * Get the last value/price change.
	 * @param csv value/price history csv
	 * @return the last value/price change delta
	 */
	public static int getDeltaLast(int[] csv) {
		if (csv == null || csv.length < 4 || csv[csv.length - 1] == -1 || csv[csv.length - 3] == -1)
			return 0;

		return csv[csv.length - 1] - csv[csv.length - 3];
	}

	/**
	 * Get the last value/price.
	 * @param csv value/price history csv
	 * @return the last value/price
	 */
	private static int getLast(int[] csv) {
		return csv == null || csv.length == 0 ? -1 : csv[csv.length - 1];
	}


	/**
	 * Get the value/price at the specified time
	 * @param csv value/price history csv
	 * @param time value/price lookup time (keepa time minutes)
	 * @return the price/value of the product at the specified time. -1 if not value was found or if the product was out of stock.
	 */
	public static int getValueAtTime(int[] csv, int time) {
		if (csv == null || csv.length == 0) return -1;
		int i = 0;
		for (; i < csv.length; i += 2) {
			if (csv[i] > time) break;
		}

		if (i > csv.length) return getLast(csv);
		if (i < 2) return -1;

		return csv[i - 1];
	}


	/**
	 *
	 * @param csv value/price history csv
	 * @param time time to begin the search
	 * @return the closest value/price found to the specified time
	 */
	public static int getClosestValueAtTime(int[] csv, int time) {
		if (csv == null || csv.length == 0) return -1;
		int i = 0;
		for (; i < csv.length; i += 2) {
			if (csv[i] > time) break;
		}

		if (i > csv.length) return getLast(csv);
		if (i < 2) {
			if (csv.length < 3)
				return csv[1];
			else
				i += 2;
		}

		if (csv[i - 1] != -1)
			return csv[i - 1];
		else {
			for (; i < csv.length; i += 2) {
				if (csv[i - 1] != -1) break;
			}
			if (i > csv.length) return getLast(csv);
			if (i < 2) return -1;
			return csv[i - 1];
		}
	}


	/**
	 * finds the lowest and highest value/price of the csv history
	 * @param csv value/price history csv
	 * @return [0] = low, [1] = high
	 */
	public static int[] getLowestAndHighest(int[] csv) {
		if (csv == null || csv.length < 6) {
			return new int[]{-1, -1};
		}

		int[] lowHigh = new int[]{Integer.MAX_VALUE, -1};

		for (int i = 0, k = csv.length; i < k; i = i + 2) {
			int v = csv[i + 1];
			if (v == -1) continue;

			if (v < lowHigh[0])
				lowHigh[0] = v;
			if (v > lowHigh[1])
				lowHigh[1] = v;
		}

		if (lowHigh[0] == Integer.MAX_VALUE)
			lowHigh[0] = -1;
		return lowHigh;
	}


	/**
	 * Returns a weighted mean of the products csv history in the last X days
	 * @param csv value/price history csv
	 * @param days number of days the weighted mean will be calculated for (e.g. 90 days, 60 days, 30 days)
	 * @return the weighted mean or -1 if insufficient history csv length (less than a day)
	 */
	public static int calcWeightedMean(int[] csv, double days) {
		int avg = -1;

		int now = KeepaTime.nowMinutes();
		if (csv == null || csv.length == 0) {
			return avg;
		}

		int size = csv.length;

		int duration = (csv[size - 2] - csv[0]) / 60;
		double count = 0;

		if (size < 4 || duration < 24)
			return avg;

		if (duration < 24 * days)
			days = Math.floor(duration / 24.0);

		for (int i = 1; i < size; i = i + 2) {
			int c = csv[i];
			if (c != -1) {
				if (now - csv[i - 1] < days * 24 * 60) {
					if (i == 1) {
						continue;
					}

					if (avg == -1) {
						if (csv[i - 2] == -1) {
							avg = 0;
						} else {
							double tmpCount = (days * 24 * 60 - (now - csv[i - 1])) / (24 * 60.0);
							count = tmpCount;
							avg = (int) Math.floor(csv[i - 2] * tmpCount);
						}
					}

					if (i + 1 == size) {
						if (csv[i - 2] == -1) {
							continue;
						}
						double tmpCount = ((now - csv[size - 2]) / (24.0 * 60.0));
						count += tmpCount;
						avg += c * tmpCount;
					} else {
						double tmpCount = ((csv[i + 1] - csv[i - 1]) / (24.0 * 60.0));
						count += tmpCount;
						avg += c * tmpCount;
					}
				} else {
					if(i == size-1 && csv[i] != -1){
						count = 1;
						avg = csv[i];
					}
				}
			}
		}

		if (avg != -1) {
			avg = (int) Math.floor(avg / count);
		}

		return avg;
	}
}
