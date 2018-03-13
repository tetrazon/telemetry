package com.janqa.tm.decoding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.janqa.tm.base.datalistener.DataReceptionListener;
import com.janqa.tm.base.decoder.FrameDecoder;
import com.janqa.tm.base.decoder.FrameDecoderException;
import com.janqa.tm.base.model.Frame;

/**
 * 
 * @author Jan Bronnikau
 * @version 1.2
 *
 */
public class DecodingDataListener implements DataReceptionListener {
	private Collection<FrameDecodeListener> listeners = new ArrayList<>();
	private Executor scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	private FrameDecoder decoder;
	private Frame previous;

	/**
	 * 
	 * @param decoder
	 */
	public DecodingDataListener(FrameDecoder decoder) {
		this.decoder = decoder;
	}

	/**
	 * 
	 */
	@Override
	public void dataReceived(byte[] b) {
		scheduledExecutor.execute(() -> {
			try {
				Frame frame = decoder.decode(b, previous);
				notifyListeners(frame);
				previous = frame;
			} catch (FrameDecoderException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 
	 * @param listener
	 */
	public void addFrameDecodeListener(FrameDecodeListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * @param listener
	 */
	public void removeFrameDecodeListener(FrameDecodeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 * @param frame
	 */
	private void notifyListeners(Frame frame) {
		listeners.forEach(l -> l.frameDecoded(frame));
	}

}
