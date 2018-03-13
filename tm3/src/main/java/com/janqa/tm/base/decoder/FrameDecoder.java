package com.janqa.tm.base.decoder;

import com.janqa.tm.base.model.Frame;

/**
 * 
 * @author Jan Bronnikau
 * @version 2.0
 *
 */
public interface FrameDecoder {
	/**
	 * 
	 * @param b
	 * @param previous
	 * @return
	 * @throws FrameDecoderException
	 */
	Frame decode(byte[] b, Frame... previous) throws FrameDecoderException;
}
