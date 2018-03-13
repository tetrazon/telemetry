package com.belintersat.tm.simple;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.janqa.tm.base.decoder.FrameDecoderException;
import com.janqa.tm.base.model.Frame;
import com.janqa.tm.base.model.Tm;
import com.janqa.tm.descriptor.DescriptorBasedFrameDecoder;

/**
 * 
 * @author Jan Bronnikau
 * @version 1.1
 * @param <D>
 */
public abstract class AbstractBelintersatFrameDecoder<D extends SimplestBelintersatDescriptor>
		extends DescriptorBasedFrameDecoder<D> {
	private static final int FORMAT_MODULE_1 = 2;
	private static final int FORMAT_MODULE_2 = 3;

	private Tm frameCounterTm;
	private Tm formatCounter1Tm;
	private Tm formatCounter2Tm;
	private Tm timeTm;

	private int rangeFrom = 64;
	private int rangeTo = 192;

	@Override
	public Frame decode(byte[] b, Frame... previous) throws FrameDecoderException {
		try {
			Frame thisFrame = new Frame();
			byte[] prefix = Arrays.copyOfRange(b, 0, rangeTo);
			byte[] kernel = Arrays.copyOfRange(b, rangeFrom, rangeTo);
			// byte[] postfix = Arrays.copyOfRange(b, rangeTo, b.length);

			Integer frameCounter = calculateFrameCounter(kernel);
			Integer formatCounter1 = calculateFormatCounter(getFormatCounter1Tm(), FORMAT_MODULE_1, previous[0], kernel,
					frameCounter);
			Integer formatCounter2 = calculateFormatCounter(getFormatCounter2Tm(), FORMAT_MODULE_2, previous[0], kernel,
					frameCounter);
			Date time = calculateTime(prefix);

			thisFrame.getTmInt().put(getFrameCounterTm(), frameCounter);
			thisFrame.getTmInt().put(getFormatCounter1Tm(), formatCounter1);
			thisFrame.getTmInt().put(getFormatCounter2Tm(), formatCounter2);
			thisFrame.getTmTime().put(getTimeTm(), time);

			decodeAllStuff(thisFrame, kernel, frameCounter, formatCounter1, formatCounter2);
			return thisFrame;
		} catch (Exception e) {
			throw new FrameDecoderException(e.getMessage(), e);
		}
	}

	protected abstract void decodeAllStuff(Frame thisFrame, byte[] kernel, Integer frameCounter, Integer formatCounter1,
			Integer formatCounter2);

	private Integer calculateFrameCounter(byte[] kernel) {
		SimplestBelintersatDescriptor dscr = getTelemetry().get(getFrameCounterTm());
		return value(kernel[dscr.byteIndex], dscr.bitIndex1, dscr.bitIndex2);
	}

	private Integer calculateFormatCounter(Tm formatCounterTm, int module, Frame previousFrame, byte[] kernel,
			Integer frameCounter) {
		Integer formatCounter = null;
		Integer previousValue = null;
		if (previousFrame != null && previousFrame.getTmInt().containsKey(formatCounterTm)) {
			previousValue = previousFrame.getTmInt().get(formatCounterTm);
		}
		if (frameCounter == getTelemetry().get(formatCounterTm).frameIndex) {
			formatCounter = value(kernel[getTelemetry().get(formatCounterTm).byteIndex],
					getTelemetry().get(formatCounterTm).bitIndex1, getTelemetry().get(formatCounterTm).bitIndex2);
		} else if (previousValue != null && frameCounter == 0) {
			formatCounter = (previousValue + 1) % module;
		} else {
			formatCounter = previousValue;
		}
		return formatCounter;
	}

	private Date calculateTime(byte[] prefix) {
		Calendar calendar = Calendar.getInstance();
		int seconds = ByteBuffer.wrap(prefix, 12, 4).getInt();
		float millisecs = ByteBuffer.wrap(prefix, 16, 4).getFloat();
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		// calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.YEAR, year);
		calendar.add(Calendar.SECOND, seconds);
		calendar.set(Calendar.MILLISECOND, (int) millisecs);
		return calendar.getTime();
	}

	protected static boolean flag(byte b, int i) {
		return ((b >>> i) & 1) == 1;
	}

	protected static int value(int a, int i, int j) {
		return a << (31 - i) >>> (j + 31 - i);
	}

	public Tm getFrameCounterTm() {
		return frameCounterTm;
	}

	public void setFrameCounterTm(Tm frameCounterTm) {
		this.frameCounterTm = frameCounterTm;
	}

	public Tm getFormatCounter1Tm() {
		return formatCounter1Tm;
	}

	public void setFormatCounter1Tm(Tm formatCounter1Tm) {
		this.formatCounter1Tm = formatCounter1Tm;
	}

	public Tm getFormatCounter2Tm() {
		return formatCounter2Tm;
	}

	public void setFormatCounter2Tm(Tm formatCounter2Tm) {
		this.formatCounter2Tm = formatCounter2Tm;
	}

	public Tm getTimeTm() {
		return timeTm;
	}

	public void setTimeTm(Tm timeTm) {
		this.timeTm = timeTm;
	}

	public int getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(int rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public int getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(int rangeTo) {
		this.rangeTo = rangeTo;
	}

}
