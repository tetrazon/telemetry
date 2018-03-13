package com.janqa.tm.decoding;

import com.janqa.tm.base.model.Frame;

/**
 * 
 * @author Jan Bronnikau
 *
 */
@FunctionalInterface
public interface FrameDecodeListener {
	/**
	 * 
	 * @param frame
	 */
	void frameDecoded(Frame frame);
}
