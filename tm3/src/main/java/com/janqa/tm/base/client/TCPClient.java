package com.janqa.tm.base.client;

public abstract class TCPClient extends Client {
	protected final String ip;
	protected final int port;

	public TCPClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

}
