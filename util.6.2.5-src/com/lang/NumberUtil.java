/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NumberUtil {
	private static Random rand = new Random();

	public static int[] getArrayByRange(int min, int max) {
		ExceptionUtil
				.gtThrowException(Integer.valueOf(min), Integer.valueOf(max),
						"the min's value must be less than or equal to the max's value");
		long len = max - min + 1;

		ExceptionUtil
				.gtThrowException(Long.valueOf(len),
						Integer.valueOf(2147483647),
						"the range between the max's value and the min's value is too large");
		int[] arr = new int[(int) len];
		for (int i = 0; i < arr.length; ++i) {
			arr[i] = (min++);
		}
		return arr;
	}

	public static Number toNumber(Object obj) {
		return Convert.toNumber(obj);
	}

	public static Number[] toNumber(Object[] obj) {
		return Convert.toNumber(obj);
	}

	public static Number[] toNumber(byte[] arr) {
		return Convert.toNumber(arr);
	}

	public static Number[] toNumber(short[] arr) {
		return Convert.toNumber(arr);
	}

	public static Number[] toNumber(int[] arr) {
		return Convert.toNumber(arr);
	}

	public static Number[] toNumber(long[] arr) {
		return Convert.toNumber(arr);
	}

	public static Number[] toNumber(float[] arr) {
		return Convert.toNumber(arr);
	}

	public static Number[] toNumber(double[] arr) {
		return Convert.toNumber(arr);
	}

	public static int nextInt(int max) {
		ExceptionUtil.ltThrowException(Integer.valueOf(max),
				Integer.valueOf(0), "max must be non negative");
		return nextInt(0, max);
	}

	public static int nextInt(int min, int max) {
		ExceptionUtil
				.gtThrowException(Integer.valueOf(min), Integer.valueOf(max),
						"the min's value must be less than or equal to the max's value");
		return ((min == max) ? min : rand.nextInt(max - min + 1) + min);
	}

	public static long[] getMms(byte[] arr) {
		ExceptionUtil.nullThrowException(arr, "arr is null");
		if (arr.length == 0)
			throw new IllegalArgumentException("arr is empty");
		long[] res = { arr[0], arr[0], arr[0] };
		for (int i = 1; i < arr.length; ++i) {
			res[0] = arr[i];
			res[1] = arr[i];
			res[2] += arr[i];
		}
		return res;
	}

	public static long[] getMms(short[] arr) {
		ExceptionUtil.nullThrowException(arr, "arr is null");
		if (arr.length == 0)
			throw new IllegalArgumentException("arr is empty");
		long[] res = { arr[0], arr[0], arr[0] };
		for (int i = 1; i < arr.length; ++i) {
			res[0] = arr[i];
			res[1] = arr[i];
			res[2] += arr[i];
		}
		return res;
	}

	public static long[] getMms(int[] arr) {
		ExceptionUtil.nullThrowException(arr, "arr is null");
		if (arr.length == 0)
			throw new IllegalArgumentException("arr is empty");
		long[] res = { arr[0], arr[0], arr[0] };
		for (int i = 1; i < arr.length; ++i) {
			res[0] = arr[i];
			res[1] = arr[i];
			res[2] += arr[i];
		}
		return res;
	}

	public static long[] getMms(long[] arr) {
		ExceptionUtil.nullThrowException(arr, "arr is null");
		if (arr.length == 0)
			throw new IllegalArgumentException("arr is empty");
		long[] res = { arr[0], arr[0], arr[0] };
		for (int i = 1; i < arr.length; ++i) {
			res[0] = ((res[0] > arr[i]) ? res[0] : arr[i]);
			res[1] = ((res[1] < arr[i]) ? res[1] : arr[i]);
			res[2] += arr[i];
		}
		return res;
	}

	public static float[] getMmsa(float[] arr) {
		ExceptionUtil.nullThrowException(arr, "arr is null");
		if (arr.length == 0)
			throw new IllegalArgumentException("arr is empty");
		float[] res = { arr[0], arr[0], arr[0], 0.0F };
		for (int i = 1; i < arr.length; ++i) {
			res[0] = ((res[0] > arr[i]) ? res[0] : arr[i]);
			res[1] = ((res[1] < arr[i]) ? res[1] : arr[i]);
			res[2] += arr[i];
		}
		res[3] += res[2] / arr.length;
		return res;
	}

	public static double[] getMmsa(double[] arr) {
		ExceptionUtil.nullThrowException(arr, "arr is null");
		if (arr.length == 0)
			throw new IllegalArgumentException("arr is empty");
		double[] res = { arr[0], arr[0], arr[0], 0.0D };
		for (int i = 1; i < arr.length; ++i) {
			res[0] = ((res[0] > arr[i]) ? res[0] : arr[i]);
			res[1] = ((res[1] < arr[i]) ? res[1] : arr[i]);
			res[2] += arr[i];
		}
		res[3] += res[2] / arr.length;
		return res;
	}

	public static byte getMax(byte[] arr) {
		return (byte) (int) getMms(arr)[0];
	}

	public static short getMax(short[] arr) {
		return (short) (int) getMms(arr)[0];
	}

	public static int getMax(int[] arr) {
		return (int) getMms(arr)[0];
	}

	public static long getMax(long[] arr) {
		return getMms(arr)[0];
	}

	public static float getMax(float[] arr) {
		return getMmsa(arr)[0];
	}

	public static double getMax(double[] arr) {
		return getMmsa(arr)[0];
	}

	public static byte getMin(byte[] arr) {
		return (byte) (int) getMms(arr)[1];
	}

	public static short getMin(short[] arr) {
		return (short) (int) getMms(arr)[1];
	}

	public static int getMin(int[] arr) {
		return (int) getMms(arr)[1];
	}

	public static long getMin(long[] arr) {
		return getMms(arr)[1];
	}

	public static float getMin(float[] arr) {
		return getMmsa(arr)[1];
	}

	public static double getMin(double[] arr) {
		return getMmsa(arr)[1];
	}

	public static long getSum(byte[] arr) {
		return getMms(arr)[2];
	}

	public static long getSum(short[] arr) {
		return getMms(arr)[2];
	}

	public static long getSum(int[] arr) {
		return getMms(arr)[2];
	}

	public static long getSum(long[] arr) {
		return getMms(arr)[2];
	}

	public static float getSum(float[] arr) {
		return getMmsa(arr)[2];
	}

	public static double getSum(double[] arr) {
		return getMmsa(arr)[2];
	}

	public static double getAvg(byte[] arr) {
		return (getMms(arr)[2] / arr.length);
	}

	public static double getAvg(short[] arr) {
		return (getMms(arr)[2] / arr.length);
	}

	public static double getAvg(int[] arr) {
		return (getMms(arr)[2] / arr.length);
	}

	public static double getAvg(long[] arr) {
		return (getMms(arr)[2] / arr.length);
	}

	public static float getAvg(float[] arr) {
		return getMmsa(arr)[3];
	}

	public static double getAvg(double[] arr) {
		return getMmsa(arr)[3];
	}

	public static int[] getPrimeNums(int max) {
		return getPrimeNums(2, max);
	}

	public static long[] getPrimeNums(long max) {
		return getPrimeNums(2L, max);
	}

	public static int[] getPrimeNums(int min, int max) {
		ExceptionUtil.ltThrowException(Integer.valueOf(min),
				Integer.valueOf(2), "the min's value must be greater than 2");
		ExceptionUtil.ltThrowException(Integer.valueOf(max),
				Integer.valueOf(2), "the max value must be greater than 2");

		ExceptionUtil
				.gtThrowException(Integer.valueOf(min), Integer.valueOf(max),
						"the min's value must be less than or equal to the max's value");
		if (min == 2) {
			if (max == 2)
				return new int[] { 2 };
			if (max == 3)
				return new int[] { 2, 3 };
		} else if ((min == 3) && (max == 3)) {
			return new int[] { 3 };
		}
		ArrayList list = new ArrayList();
		for (int i = min; i <= max; ++i) {
			if (isPrimeNum(i)) {
				list.add(Integer.valueOf(i));
				if (i > 2)
					++i;
			}
		}
		return Convert.toIntValue(Convert.toArray(list, Integer.class));
	}

	public static long[] getPrimeNums(long min, long max) {
		ExceptionUtil.ltThrowException(Long.valueOf(min), Integer.valueOf(2),
				"the min's value must be greater than 2");
		ExceptionUtil.ltThrowException(Long.valueOf(max), Integer.valueOf(2),
				"the max value must be greater than 2");

		ExceptionUtil
				.gtThrowException(Long.valueOf(min), Long.valueOf(max),
						"the min's value must be less than or equal to the max's value");
		if (min == 2L) {
			if (max == 2L)
				return new long[] { 2L };
			if (max == 3L)
				return new long[] { 2L, 3L };
		} else if ((min == 3L) && (max == 3L)) {
			return new long[] { 3L };
		}
		ArrayList list = new ArrayList();
		for (long i = min; i <= max; i += 1L) {
			if (isPrimeNum(i)) {
				list.add(Long.valueOf(i));
				if (i > 2L)
					i += 1L;
			}
		}
		return Convert.toLongValue(Convert.toArray(list, Long.class));
	}

	public static boolean isPrimeNum(int num) {
		return isPrimeNum(num);
	}

	public static boolean isPrimeNum(long num) {
		if ((num < 2L) || ((num > 2L) && (num % 2L == 0L))
				|| ((num > 3L) && (num % 3L == 0L))
				|| ((num > 5L) && (num % 5L == 0L))
				|| ((num > 7L) && (num % 7L == 0L))) {
			return false;
		}
		if ((num == 2L) || (num == 3L))
			return true;
		for (int i = 2; i <= Math.sqrt(num); ++i) {
			if (num % i == 0L)
				return false;
		}
		return true;
	}

	public static int[] getPrimeFactorization(int num) {
		return Convert
				.toIntValue(getPrimeFactorizationToString(num).split("=")[1]
						.split("\\*"));
	}

	public static long[] getPrimeFactorization(long num) {
		return Convert.toLongValue(getPrimeFactorizationToString(num)
				.split("=")[1].split("\\*"));
	}

	public static String getPrimeFactorizationToString(int num) {
		return getPrimeFactorizationToString(num);
	}

	public static String getPrimeFactorizationToString(long num)
  {
    ExceptionUtil.ltThrowException(Long.valueOf(num), Integer.valueOf(2), "被分解的数必须大于等于2");
    StringBuilder sb = new StringBuilder();

    if (isPrimeNum(num)) {
      sb.append(num).append('=').append(num);
      return sb.toString();
    }
    sb.append(num).append('=');
    long n = num;

    int i = 2; break label177:

    ++i;
    do
    {
      if ((((i <= 2) || (i % 2 != 0))) && (((i <= 3) || (i % 3 != 0))) && ((
        (i <= 5) || (i % 5 != 0))));
      if (i > 7)
        if (i % 7 == 0);

      while (n % i == 0L)
      {
        n /= i;
        sb.append(i);
        if (n > 1L)
          sb.append('*');
      }
      if (i % 2 == 1)
        ++i;
      label177: ++i; } while (n > 1L);

    return sb.toString();
  }

	public static String[] getGCDandLCMToString(byte[] arr) {
		return getGCDandLCMToString(Convert.toLongValue(arr));
	}

	private static String[] getGCDandLCMToString(boolean isLcm, byte[] arr) {
		return getGCDandLCMToString(isLcm, Convert.toLongValue(arr));
	}

	public static String[] getGCDandLCMToString(short[] arr) {
		return getGCDandLCMToString(Convert.toLongValue(arr));
	}

	private static String[] getGCDandLCMToString(boolean isLcm, short[] arr) {
		return getGCDandLCMToString(isLcm, Convert.toLongValue(arr));
	}

	public static String[] getGCDandLCMToString(int[] arr) {
		return getGCDandLCMToString(Convert.toLongValue(arr));
	}

	private static String[] getGCDandLCMToString(boolean isLcm, int[] arr) {
		return getGCDandLCMToString(isLcm, Convert.toLongValue(arr));
	}

	private static String[] getGCDandLCMToString(long[] arr) {
		return getGCDandLCMToString(true, arr);
	}

	private static String[] getGCDandLCMToString(boolean isLcm, long[] arr) {
    ExceptionUtil.nullThrowException(arr, "arr is null");
    if (arr.length == 0)
      throw new IllegalArgumentException("arr is empty");
    long min = arr[0];
    ExceptionUtil.ltThrowException(Long.valueOf(min), Integer.valueOf(2), "数组arr中的数必须大于等于2");
    String[] resArr = new String[2];
    if (arr.length == 1) {
      resArr[0] = getPrimeFactorizationToString(min);
      resArr[1] = resArr[0];
      return resArr;
    }
    for (int i = 1; i < arr.length; ++i) {
      ExceptionUtil.ltThrowException(Long.valueOf(arr[i]), Integer.valueOf(2), "数组arr中的数必须大于等于2");
      min = (min < arr[i]) ? min : arr[i];
    }
    StringBuilder sb = new StringBuilder("=");
    long[] copyArr = Arrays.copyOf(arr, arr.length);
    long res = 1L;
    int i = 2; break label350:

    ++i;
    do
    {
      if ((((i <= 2) || (i % 2 != 0))) && (((i <= 3) || (i % 3 != 0))) && 
        (((i <= 5) || (i % 5 != 0))) && ((
        (i <= 7) || (i % 7 != 0))));
      boolean tmp = false;
      while (!(tmp)) {
        for (int j = 0; j < copyArr.length; ++j) {
          if (copyArr[j] % i != 0L) {
            tmp = true;
            break;
          }
        }
        if (!(tmp)) {
          res *= i;
          sb.append(i).append('*');
          for (int j = 0; j < copyArr.length; ++j) {
            copyArr[j] /= i;
            if ((copyArr[j] <= i) && (!(tmp)))
              tmp = true;
          }
          if (tmp)
            break label358;
        }
      }
      if (i % 2 == 1)
        ++i;
      label350: ++i; } while (i <= min);

    if (res == 1L) {
      label358: resArr[0] = "1=1";
    } else {
      if (sb.lastIndexOf("*") == sb.length() - 1)
        sb.deleteCharAt(sb.length() - 1);
      resArr[0] = res + sb.toString();
    }
    if (!(isLcm))
      return resArr;
    StringBuilder s = null;
    int i = 2;
    break label452:

    ++i;

    for (; ; ++i) {
      label452: if ((((i <= 2) || (i % 2 != 0))) && (((i <= 3) || (i % 3 != 0))) && 
        (((i <= 5) || (i % 5 != 0))) && ((
        (i <= 7) || (i % 7 != 0))));
      while (true)
      {
        int c = 0;
        int count = 0;
        long max = copyArr[0];
        for (int j = 0; j < copyArr.length; ++j) {
          if (copyArr[j] < i)
            ++c;
          if (copyArr[j] % i == 0L)
            ++count;
          max = (max > arr[j]) ? max : arr[j];
        }
        if (c == copyArr.length) break label762; if (i > max)
          break label762;
        if (count < 2)
          break;
        for (int j = 0; j < copyArr.length; ++j) {
          if (copyArr[j] % i == 0L)
            copyArr[j] /= i;
        }
        if (res * i <= 9.223372036854776E+018D) {
          res *= i;
        } else if (s == null) {
          s = new StringBuilder(String.valueOf(res));
          res = i;
        } else {
          s.append(',').append(res);
          res = i;
        }
        sb.append(i).append('*');
      }
      if (i % 2 == 1)
        ++i;
    }
    for (int i = 0; i < copyArr.length; ++i) {
      if (copyArr[i] != 1L) {
        if (res * copyArr[i] <= 9.223372036854776E+018D) {
          label762: res *= copyArr[i];
        } else if (s == null) {
          s = new StringBuilder(String.valueOf(res));
          res = copyArr[i];
        } else {
          s.append(',').append(res);
          res = copyArr[i];
        }
        sb.append(copyArr[i]).append('*');
      }
    }
    if (s == null) {
      sb.insert(0, res);
    } else {
      String[] arr2 = s.toString().split(",");
      BigInteger big = new BigInteger(arr2[0]);
      for (int i = 1; i < arr2.length; ++i) {
        big = big.multiply(new BigInteger(arr2[i]));
      }
      sb.insert(0, big.multiply(new BigInteger(String.valueOf(res)))
        .toString());
    }
    if (sb.lastIndexOf("*") == sb.length() - 1)
      sb.deleteCharAt(sb.length() - 1);
    resArr[1] = sb.toString();
    return resArr;
  }

	public static byte getGreatestCommonDivisor(byte[] arr) {
		return Convert.toByteValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[0]);
	}

	public static short getGreatestCommonDivisor(short[] arr) {
		return Convert.toShortValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[0]);
	}

	public static int getGreatestCommonDivisor(int[] arr) {
		return Convert.toIntValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[0]);
	}

	public static long getGreatestCommonDivisor(long[] arr) {
		return Convert.toIntValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[0]);
	}

	public static byte[] getGreatestCommonDivisorToArray(byte[] arr) {
		return Convert.toByteValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[1].split("\\*"));
	}

	public static short[] getGreatestCommonDivisorToArray(short[] arr) {
		return Convert.toShortValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[1].split("\\*"));
	}

	public static int[] getGreatestCommonDivisorToArray(int[] arr) {
		return Convert.toIntValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[1].split("\\*"));
	}

	public static long[] getGreatestCommonDivisorToArray(long[] arr) {
		return Convert.toLongValue(getGCDandLCMToString(false, arr)[0]
				.split("=")[1].split("\\*"));
	}

	public static String getGreatestCommonDivisorToString(byte[] arr) {
		return getGCDandLCMToString(false, arr)[0];
	}

	public static String getGreatestCommonDivisorToString(short[] arr) {
		return getGCDandLCMToString(false, arr)[0];
	}

	public static String getGreatestCommonDivisorToString(int[] arr) {
		return getGCDandLCMToString(false, arr)[0];
	}

	public static String getGreatestCommonDivisorToString(long[] arr) {
		return getGCDandLCMToString(false, arr)[0];
	}

	public static String getLowestCommonMultiple(byte[] arr) {
		return getGCDandLCMToString(true, arr)[1].split("=")[0];
	}

	public static String getLowestCommonMultiple(short[] arr) {
		return getGCDandLCMToString(true, arr)[1].split("=")[0];
	}

	public static String getLowestCommonMultiple(int[] arr) {
		return getGCDandLCMToString(true, arr)[1].split("=")[0];
	}

	public static String getLowestCommonMultipleByRange(int max) {
		return getLowestCommonMultipleByRange(2, max);
	}

	public static String getLowestCommonMultipleByRange(int min, int max) {
		ExceptionUtil.ltThrowException(Integer.valueOf(min),
				Integer.valueOf(2), "the min's value must be greater than 2");
		ExceptionUtil.ltThrowException(Integer.valueOf(max),
				Integer.valueOf(2), "the max value must be greater than 2");
		int[] arr = getArrayByRange(min, max);
		return getGCDandLCMToString(true, arr)[1].split("=")[0];
	}

	public static long[] getLowestCommonMultipleToArray(byte[] arr) {
		return Convert.toLongValue(getGCDandLCMToString(true, arr)[1]
				.split("=")[1].split("\\*"));
	}

	public static long[] getLowestCommonMultipleToArray(short[] arr) {
		return Convert.toLongValue(getGCDandLCMToString(true, arr)[1]
				.split("=")[1].split("\\*"));
	}

	public static long[] getLowestCommonMultipleToArray(int[] arr) {
		return Convert.toLongValue(getGCDandLCMToString(true, arr)[1]
				.split("=")[1].split("\\*"));
	}

	public static long[] getLowestCommonMultipleByRangeToArray(int max) {
		return getLowestCommonMultipleByRangeToArray(2, max);
	}

	public static long[] getLowestCommonMultipleByRangeToArray(int min, int max) {
		ExceptionUtil.ltThrowException(Integer.valueOf(min),
				Integer.valueOf(2), "the min's value must be greater than 2");
		ExceptionUtil.ltThrowException(Integer.valueOf(max),
				Integer.valueOf(2), "the max value must be greater than 2");
		int[] arr = getArrayByRange(min, max);
		return Convert.toLongValue(getGCDandLCMToString(true, arr)[1]
				.split("=")[1].split("\\*"));
	}

	public static String getLowestCommonMultipleToString(byte[] arr) {
		return getGCDandLCMToString(true, arr)[1];
	}

	public static String getLowestCommonMultipleToString(short[] arr) {
		return getGCDandLCMToString(true, arr)[1];
	}

	public static String getLowestCommonMultipleToString(int[] arr) {
		return getGCDandLCMToString(true, arr)[1];
	}

	public static String getLowestCommonMultipleByRangeToString(int max) {
		return getLowestCommonMultipleByRangeToString(2, max);
	}

	public static String getLowestCommonMultipleByRangeToString(int min, int max) {
		ExceptionUtil.ltThrowException(Integer.valueOf(min),
				Integer.valueOf(2), "the min's value must be greater than 2");
		ExceptionUtil.ltThrowException(Integer.valueOf(max),
				Integer.valueOf(2), "the max value must be greater than 2");
		int[] arr = getArrayByRange(min, max);
		return getGCDandLCMToString(true, arr)[1];
	}
}