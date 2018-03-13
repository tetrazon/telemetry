//package app;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.locks.ReentrantLock;
//
//import com.janqa.tm.base.datalistener.DataReceptionListener;
//
///**
// * 
// * @author Jan Bronnikau
// * @version 2.2
// *
// */
//@Deprecated
//public class BBEClient {
//
//	private static final String s = "49 96 02 D2 00 00 00 40  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 02  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  00 00 00 00 B6 69 FD 2E";
//	private static final byte[] REQUEST = hexStringToByteArray(s.replaceAll(" ", ""));
//	private final String ip;
//	private final int port;
//	private int frameLength = 196;
//	private List<DataReceptionListener> listeners = new LinkedList<DataReceptionListener>();
//	ReentrantLock lock = new ReentrantLock();
//	private static ExecutorService mainExecutor = Executors.newSingleThreadExecutor();
//	private static ExecutorService notifyingExecutor = Executors.newCachedThreadPool();
//
//	/**
//	 * 
//	 * @param ip
//	 * @param port
//	 */
//	public BBEClient(String ip, int port) {
//		this.ip = ip;
//		this.port = port;
//	}
//
//	/**
//	 * Initiates connection to the Server and receiving data. Once a data packet of
//	 * defined {@code frameLength} is received, notifies all listeners.
//	 * 
//	 * @since 2.0
//	 */
//	public void start() {
//		mainExecutor.execute(() -> {
//			System.out.println("run client..");
//			byte[] b = new byte[frameLength];
//			try (Socket client = new Socket(ip, port);
//					InputStream in = client.getInputStream();
//					OutputStream out = client.getOutputStream()) {
//
//				out.write(REQUEST);
//
//				for (;;) {
//					if (in.available() >= frameLength) {
//						in.read(b);
//						notifyListeners(b);
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//	}
//
//	/**
//	 * 
//	 */
//	public void terminate() {
//		mainExecutor.shutdownNow();
//		notifyingExecutor.shutdownNow();
//	}
//
//	/**
//	 * 
//	 * @param listener
//	 */
//	public void addDataReceptionListener(DataReceptionListener listener) {
//		lock.lock();
//		listeners.add(listener);
//		lock.unlock();
//	}
//
//	/**
//	 * 
//	 * @param listener
//	 */
//	public void removeDataReceptionListener(DataReceptionListener listener) {
//		lock.lock();
//		listeners.remove(listener);
//		lock.unlock();
//	}
//
//	private void notifyListeners(byte[] b) {
//		lock.lock();
//		listeners.forEach(l -> notifyingExecutor.submit(() -> l.dataReceived(b)));
//		lock.unlock();
//	}
//
//	private static byte[] hexStringToByteArray(String s) {
//		int len = s.length();
//		byte[] data = new byte[len / 2];
//		for (int i = 0; i < len; i += 2) {
//			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
//		}
//		return data;
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public int getFrameLength() {
//		return frameLength;
//	}
//
//	/**
//	 * 
//	 * @param frameLength
//	 */
//	public void setFrameLength(int frameLength) {
//		this.frameLength = frameLength;
//	}
//
//}
