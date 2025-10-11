package com.sunastrix.astropdf.util;

import java.io.Serializable;
import java.util.ArrayList;

public class Utility extends SUtil implements Serializable {
	protected String languageCode = "0";

	public static int[] removeValueFromIntArray(int valueToRemove, int[] array) {

		int[] returnVal;
		var arrayLength = 0;
		var index = -1;
		if (array.length == 0) {
			return array;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] != valueToRemove) {
				++arrayLength;
			}
		}
		returnVal = new int[arrayLength];
		for (int i = 0; i < array.length; i++) {
			if (array[i] != valueToRemove) {
				returnVal[++index] = array[i];
			}
		}
		return returnVal;
	}

	public static int[] assendingSort(int[] arr) {
		int temp;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}

	public static ArrayList<Integer> assendingSort(ArrayList<Integer> arr) {
		int temp;
		for (int i = 0; i < arr.size(); i++) {
			for (int j = i + 1; j < arr.size(); j++) {
				if (arr.get(i) > arr.get(j)) {
					temp = arr.get(i);
					arr.set(i, arr.get(j));
					arr.set(j, temp);
				}
			}
		}
		return arr;
	}

	public static int removeDuplicateElements(int[] arr, int n) {
		if (n == 0 || n == 1) {
			return n;
		}
		int[] temp = new int[n];
		int j = 0;

		for (int i = 0; i < n - 1; i++) {
			if (arr[i] != arr[i + 1]) {
				temp[j++] = arr[i];
			}
		}
		temp[j++] = arr[n - 1];

		// Changing original array
		for (int i = 0; i < j; i++) {
			arr[i] = temp[i];
		}
		return j;
	}

	public static int removeDuplicateElements(ArrayList<Integer> arr, int n) {
		if (n == 0 || n == 1) {
			return n;
		}
		ArrayList<Integer> temp = new ArrayList<>();
		int j = 0;

		for (int i = 0; i < n - 1; i++) {
			if (!arr.get(i).equals(arr.get(i + 1))) {
				temp.add(arr.get(i));
			}
		}
		temp.add(arr.get(n - 1));

		// Changing original list
		arr.clear();
		arr.addAll(temp);

		return temp.size();
	}

	public static String convertTimeToAmPm(String time) {
		int hr;
		int min;
		String timeWithMeridian = "";

		// Split the input time string by ":"
		String[] splitTime = time.split(":");

		try {
			// Parse hours and minutes
			hr = Integer.parseInt(splitTime[0]);
			min = Integer.parseInt(splitTime[1]);

			// Handle AM/PM conversion
			if (hr < 12) { // AM case
				timeWithMeridian = appendZeroOnSingleDigit(hr) + ":" + appendZeroOnSingleDigit(min) + " AM";
			} else if (hr < 24) { // PM case
				hr = (hr == 12) ? 12 : hr - 12;
				timeWithMeridian = appendZeroOnSingleDigit(hr) + ":" + appendZeroOnSingleDigit(min) + " PM";
			} else { // Time greater than 24 (handling overflow)
				hr -= 24;
				timeWithMeridian = "+" + appendZeroOnSingleDigit(hr) + ":" + appendZeroOnSingleDigit(min) + " AM";
			}
		} catch (Exception e) {
			// If any exception occurs (e.g., parsing), return the original time
			timeWithMeridian = time;
		}

		return timeWithMeridian;
	}

	private static String appendZeroOnSingleDigit(int number) {
		return (number < 10 ? "0" : "") + number;
	}

	public String dms(double x) {
		String[] var3 = new String[3];
		int var4 = (int) x;
		var3[0] = makelength(String.valueOf(var4), 3);
		double var7;
		int x1 = (int) ((var7 = x - (double) var4) * 60.0D);
		var3[1] = makelength(String.valueOf(x1), 2);
		x1 = (int) (((var7 *= 60.0D) - (double) x1) * 60.0D + 0.5D);
		var3[2] = makelength(String.valueOf(x1), 2);
		return var3[0] + this.getDashString(1) + var3[1] + this.getDashString(1) + var3[2];
	}

	public String getDashString(int noOfDash) {
		String var2 = "";
		if (this.getLanguageCode().equalsIgnoreCase("0")) {
			var2 = "-";
		} else if (this.getLanguageCode().equalsIgnoreCase("1")) {
			var2 = "&";
		}

		String var3 = "";

		for (int var4 = 0; var4 < noOfDash; ++var4) {
			var3 = var3 + var2;
		}

		return var3;
	}

	public String getDotString() {
		String var1 = "";
		if (this.getLanguageCode().equalsIgnoreCase("0")) {
			var1 = ".";
		} else if (this.getLanguageCode().equalsIgnoreCase("1")) {
			var1 = " + ";
		}

		return var1;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public  String getSlashString() {
		String var1 = "";
		if (this.getLanguageCode().equalsIgnoreCase("0")) {
			var1 = "/";
		} else if (this.getLanguageCode().equalsIgnoreCase("1")) {
			var1 = "@";
		}

		return var1;
	}

	public String hms(double x) {
		String[] var3 = new String[3];
		int var4 = (int) x;
		var3[0] = makelength(String.valueOf(var4), 2);
		double var7;
		int x1 = (int) ((var7 = x - (double) var4) * 60.0D);
		var3[1] = makelength(String.valueOf(x1), 2);
		x1 = (int) (((var7 *= 60.0D) - (double) x1) * 60.0D);
		var3[2] = makelength(String.valueOf(x1), 2);
		return var3[0] + "%" + var3[1] + "%" + var3[2];
	}

	public void setLanguageCode(String language) {
		this.languageCode = language;
	}

	public String ymd(int year, int month, int day) {
		return makespace(String.valueOf(day), 2) + this.getSlashString() + makespace(String.valueOf(month), 2)
				+ this.getSlashString() + makespace(String.valueOf(year), 4).substring(2);
	}

	public String dms1(double x) {
		String[] var3 = new String[3];
		int var4 = (int) x;
		var3[0] = makelength(String.valueOf(var4), 3);
		double var7;
		int x1 = (int) ((var7 = x - (double) var4) * 60.0D);
		var3[1] = makelength(String.valueOf(x1), 2);
		x1 = (int) (((var7 *= 60.0D) - (double) x1) * 60.0D);
		var3[2] = makelength(String.valueOf(x1), 2);
		return var3[0] + this.getDashString(1) + var3[1] + this.getDashString(1) + var3[2];
	}

	public String getDasaEndsTerminator() {
		String var1 = "";
		if (this.getLanguageCode().equalsIgnoreCase("0")) {
			var1 = "+";
		} else if (this.getLanguageCode().equalsIgnoreCase("1")) {
			var1 = "+";
		}
		return var1;
	}
}
