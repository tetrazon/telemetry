package com.janqa.tm.base.client;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import com.janqa.tm.base.datalistener.DataReceptionListener;

public abstract class Client {
	private List<DataReceptionListener> listeners = new LinkedList<DataReceptionListener>();
	ReentrantLock lock = new ReentrantLock();
	private static ExecutorService notifyingExecutor = Executors.newCachedThreadPool();

	public abstract void start();

	public void terminate() {
		notifyingExecutor.shutdownNow();
	};

	/**
	 * 
	 * @param listener
	 */
	public void addDataReceptionListener(DataReceptionListener listener) {
		lock.lock();
		listeners.add(listener);
		lock.unlock();
	}

	/**
	 * 
	 * @param listener
	 */
	public void removeDataReceptionListener(DataReceptionListener listener) {
		lock.lock();
		listeners.remove(listener);
		lock.unlock();
	}

	protected void notifyListeners(byte[] b) {
		lock.lock();
		listeners.forEach(l -> notifyingExecutor.submit(() -> l.dataReceived(b)));
		lock.unlock();
	}
}
