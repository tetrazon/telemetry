package com.belintersat.tm.influxframesaver;

import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import com.janqa.tm.base.model.Frame;
import com.janqa.tm.base.model.Tm;

/**
 * 
 * @author Jan Bronnikau
 * @version 2.0
 *
 */
public class InfluxSaver {
	private String dbName;
	private String measurement;
	private InfluxDB influxDB;
	private String retentionPolicy;

	/**
	 * 
	 * @param host
	 * @param user
	 * @param password
	 * @param dbName
	 * @param measurement
	 */
	public InfluxSaver(String host, String user, String password, String dbName, String measurement,
			String retentionPolicy) {
		this.dbName = dbName;
		this.measurement = measurement;
		this.retentionPolicy = retentionPolicy;
		this.influxDB = InfluxDBFactory.connect(host, user, password);
	}

	/**
	 * 
	 * @param time
	 * @param frame
	 */
	public void save(Date time, Frame frame) {
		influxDB.write(dbName, retentionPolicy, createPoint(time.getTime(), frame));
	}

	private Point createPoint(long time, Frame frame) {
		Builder builder = Point.measurement(measurement);
		builder = builder.time(time, TimeUnit.MILLISECONDS);
		for (Entry<Tm, Float> e : frame.getTmFloat().entrySet()) {
			builder = builder.addField(e.getKey().getCode(), e.getValue());
		}
		for (Entry<Tm, Integer> e : frame.getTmInt().entrySet()) {
			builder = builder.addField(e.getKey().getCode(), e.getValue());
		}
		for (Entry<Tm, Boolean> e : frame.getTmBit().entrySet()) {
			builder = builder.addField(e.getKey().getCode(), e.getValue());
		}
		return builder.build();
	}
}
