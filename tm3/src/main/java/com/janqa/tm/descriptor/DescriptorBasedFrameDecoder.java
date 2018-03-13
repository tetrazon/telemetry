package com.janqa.tm.descriptor;

import java.util.Map;

import com.janqa.tm.base.decoder.FrameDecoder;
import com.janqa.tm.base.model.Tm;

/**
 * 
 * @author Jan Bronnikau
 * @version 1.0
 * @param <D>
 */
public abstract class DescriptorBasedFrameDecoder<D extends Descriptor> implements FrameDecoder {
	private Map<Tm, D> telemetry;

	public Map<Tm, D> getTelemetry() {
		return telemetry;
	}

	public void setTelemetry(Map<Tm, D> extended) {
		this.telemetry = extended;
	}

}
