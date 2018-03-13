package com.belintersat.tm.simple;

import com.janqa.tm.base.model.Frame;
import com.janqa.tm.base.model.Tm;

/**
 * 
 * @author Jan Bronnikau
 * @version 1.0
 * @param <D>
 */
public class SimplestBelintersatFrameDecoder<D extends SimplestBelintersatDescriptor> extends AbstractBelintersatFrameDecoder<D> {

	@Override
	protected void decodeAllStuff(Frame thisFrame, byte[] kernel, Integer frameCounter, Integer formatCounter1,
			Integer formatCounter2) {
		getTelemetry().entrySet().stream()
				.filter(e -> !(e.getKey().equals(getFrameCounterTm()) || e.getKey().equals(getFormatCounter1Tm())
						|| e.getKey().equals(getFormatCounter2Tm()) || e.getKey().equals(getTimeTm())))
				.filter(e -> e.getValue().frameIndex == frameCounter || e.getValue().frameIndex == null)
				.filter(e -> e.getValue().formatIndex1 == formatCounter1 || e.getValue().formatIndex1 == null)
				.filter(e -> e.getValue().formatIndex1 == formatCounter2 || e.getValue().formatIndex2 == null)
				.filter(e -> e.getValue().byteIndex != null && e.getValue().bitIndex1 != null).forEach(e -> {
					Tm tm = e.getKey();
					SimplestBelintersatDescriptor dscr = e.getValue();
					switch (tm.getType()) {
					case FLOAT:
						thisFrame.getTmFloat().put(tm,
								dscr.table[value(kernel[dscr.byteIndex], dscr.bitIndex1, dscr.bitIndex2)]);
						break;
					case BIT:
						thisFrame.getTmBit().put(tm, flag(kernel[dscr.byteIndex], dscr.bitIndex1));
						break;
					case TIME:
						break;
					case INT:
						thisFrame.getTmInt().put(tm, value(kernel[dscr.byteIndex], dscr.bitIndex1, dscr.bitIndex2));
						break;
					default:
						break;
					}
				});
	}
}
