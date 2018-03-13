package com.janqa.tm.bbeclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.janqa.tm.base.client.TCPClient;

public class BBEClient extends TCPClient {
	private static ExecutorService mainExecutor = Executors.newSingleThreadExecutor();
	private static final String s = "49 96 02 D2 00 00 00 40  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 02  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  00 00 00 00 B6 69 FD 2E";
	private static final byte[] REQUEST = hexStringToByteArray(s.replaceAll(" ", ""));
	private final int frameLength;

	public BBEClient(String ip, int port, int frameLength) {
		super(ip, port);
		this.frameLength = frameLength;
	}

	@Override
	public void start() {
		mainExecutor.execute(() -> {
			System.out.println("run client..");
			byte[] b = new byte[frameLength];
			try (Socket client = new Socket(ip, port);
					InputStream in = client.getInputStream();
					OutputStream out = client.getOutputStream()) {

				out.write(REQUEST);

				for (;;) {
					if (in.available() >= frameLength) {
						in.read(b);
						notifyListeners(b);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void terminate() {
		super.terminate();
		mainExecutor.shutdownNow();
	}

	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
