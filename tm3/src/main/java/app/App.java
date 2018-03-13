package app;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.belintersat.tm.influxframesaver.InfluxSaver;
import com.janqa.tm.base.client.Client;
import com.janqa.tm.base.model.Tm;
import com.janqa.tm.bbeclient.BBEClient;
import com.janqa.tm.decoding.DecodingDataListener;

public class App {

	private static InfluxSaver infuxSaver;
	private static MyDecoder decoder = new MyDecoder();
	private static String influxHost = "http://localhost:8086";
	private static String influxUser = "janqa";
	private static String influxPassword = "123456";

	public static void main(String[] args) throws IOException {
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		String path = args[2];
		String dbName = args[3];
		String measurement = args[4];

		infuxSaver = new InfluxSaver(influxHost, influxUser, influxPassword, dbName, measurement, "autogen");
		Map<Tm, MyDescriptor> extended = JSONParser.createMap(path);
		Tm timeTm = extended.entrySet().stream().map(Entry::getKey).filter(tm -> tm.getCode().equals("time")).findAny()
				.orElse(null);
		Tm frameCounterTm = extended.entrySet().stream().map(Entry::getKey)
				.filter(tm -> tm.getCode().equals("frameCounter")).findAny().orElse(null);
		Tm formatCounter1Tm = extended.entrySet().stream().map(Entry::getKey)
				.filter(tm -> tm.getCode().equals("formatCounter1")).findAny().orElse(null);
		Tm formatCounter2Tm = extended.entrySet().stream().map(Entry::getKey)
				.filter(tm -> tm.getCode().equals("formatCounter2")).findAny().orElse(null);
		decoder.setTelemetry(extended);
		decoder.setFrameCounterTm(frameCounterTm);
		decoder.setFormatCounter1Tm(formatCounter1Tm);
		decoder.setFormatCounter2Tm(formatCounter2Tm);
		decoder.setTimeTm(timeTm);

		Client client = new BBEClient(ip, port, 196);
		client.start();
		DecodingDataListener decoding = new DecodingDataListener(decoder);
		client.addDataReceptionListener(decoding);
		decoding.addFrameDecodeListener(frame -> infuxSaver.save(frame.getTmTime().get(timeTm), frame));
	}
}
