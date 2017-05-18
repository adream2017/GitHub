/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import java.util.Date;

public class TimeSpan {
	public static final long MILLS_PER_YEAR = 31536000000L;
	public static final long MILLS_PER_MONTH = 2592000000L;
	public static final long MILLS_PER_DAY = 86400000L;
	public static final long MILLS_PER_HOUR = 3600000L;
	public static final long MILLS_PER_MINUTE = 60000L;
	public static final long MILLS_PER_SECOND = 1000L;
	private long years;
	private int months;
	private int days;
	private int hours;
	private int minutes;
	private int seconds;
	private int milliSeconds;
	private long totalMilliSeconds;

	public long getTotalMilliSeconds() {
		return this.totalMilliSeconds;
	}

	public void setTotalMilliSeconds(long time) {
		if (time == this.totalMilliSeconds)
			return;
		this.totalMilliSeconds = time;
		this.years = (time / 31536000000L);
		time %= 31536000000L;
		this.months = (int) (time / 2592000000L);
		time %= 2592000000L;
		this.days = (int) (time / 86400000L);
		time %= 86400000L;
		this.hours = (int) (time / 3600000L);
		time %= 3600000L;
		this.minutes = (int) (time / 60000L);
		time %= 60000L;
		this.seconds = (int) (time / 1000L);
		time %= 1000L;
		this.milliSeconds = (int) time;
	}

	public TimeSpan() {
	}

	public TimeSpan(long time) {
		setTotalMilliSeconds(time);
	}

	public TimeSpan(Date date1, Date date2) {
		ExceptionUtil.nullThrowException(date1, "date1 should not be null");
		ExceptionUtil.nullThrowException(date2, "date2 should not be null");
		setTotalMilliSeconds(date2.getTime() - date1.getTime());
	}

	public long getYears() {
		return this.years;
	}

	public int getMonths() {
		return this.months;
	}

	public int getDays() {
		return this.days;
	}

	public int getHours() {
		return this.hours;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public int getSeconds() {
		return this.seconds;
	}

	public int getMilliSeconds() {
		return this.milliSeconds;
	}

	public double getTotalYears() {
		return (this.totalMilliSeconds / 31536000000.0D);
	}

	public double getTotalMonths() {
		return (this.totalMilliSeconds / 2592000000.0D);
	}

	public double getTotalDays() {
		return (this.totalMilliSeconds / 86400000.0D);
	}

	public double getTotalHours() {
		return (this.totalMilliSeconds / 3600000.0D);
	}

	public double getTotalMinutes() {
		return (this.totalMilliSeconds / 60000.0D);
	}

	public double getTotalSeconds() {
		return (this.totalMilliSeconds / 1000.0D);
	}

	public void add(long time) {
		setTotalMilliSeconds(this.totalMilliSeconds + time);
	}

	public void sub(long time) {
		setTotalMilliSeconds(this.totalMilliSeconds - time);
	}

	public boolean equals(Object obj) {
		return (this.totalMilliSeconds == ((TimeSpan) obj).totalMilliSeconds);
	}
}