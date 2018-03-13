package com.janqa.tm.base.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jan Bronnikau
 * @version 4.0
 *
 */

public class Frame extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6445371612978013127L;
	
	private Date time;

	private Map<Tm, Boolean> tmBit = new HashMap<Tm, Boolean>();

	private Map<Tm, Integer> tmInt = new HashMap<Tm, Integer>();

	private Map<Tm, Float> tmFloat = new HashMap<Tm, Float>();

	private Map<Tm, Date> tmTime = new HashMap<Tm, Date>();

	private Map<Tm, byte[]> tmBinary = new HashMap<Tm, byte[]>();

	private Map<Tm, String> tmString = new HashMap<>();

	public Map<Tm, Boolean> getTmBit() {
		return tmBit;
	}

	public void setTmBit(Map<Tm, Boolean> tmBit) {
		this.tmBit = tmBit;
	}

	public Map<Tm, Integer> getTmInt() {
		return tmInt;
	}

	public void setTmInt(Map<Tm, Integer> tmTinyint) {
		this.tmInt = tmTinyint;
	}

	public Map<Tm, Float> getTmFloat() {
		return tmFloat;
	}

	public void setTmFloat(Map<Tm, Float> tmFloat) {
		this.tmFloat = tmFloat;
	}

	public Map<Tm, Date> getTmTime() {
		return tmTime;
	}

	public void setTmTime(Map<Tm, Date> tmDatetime) {
		this.tmTime = tmDatetime;
	}

	public Map<Tm, byte[]> getTmBinary() {
		return tmBinary;
	}

	public void setTmBinary(Map<Tm, byte[]> tmBinary) {
		this.tmBinary = tmBinary;
	}

	public Map<Tm, String> getTmString() {
		return tmString;
	}

	public void setTmString(Map<Tm, String> tmString) {
		this.tmString = tmString;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
