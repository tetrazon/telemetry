package com.janqa.tm.base.decoder;

/**
 * 
 * @author Jan Bronnikau
 * @version 1.0
 *
 */
public class FrameDecoderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6014439529634905852L;

	/**
	 * 
	 * @param e
	 */
	public FrameDecoderException(Exception e) {
		super(e);
	}

	/**
	 * 
	 * @param message
	 * @param e
	 */
	public FrameDecoderException(String message, Exception e) {
		super(message, e);
	}

}
