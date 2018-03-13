package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.janqa.tm.base.model.Tm;

/**
 * 
 * @author Jan Bronnikau
 * @version 1.0
 *
 */
class JSONParser {
	private static class Tupple {
		public Tm tm;
		public MyDescriptor description;
	}

	private JSONParser() {
	}

	@SuppressWarnings("serial")
	public static Map<Tm, MyDescriptor> createMap(String path) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get(path)));
		java.lang.reflect.Type type = new TypeToken<Collection<Tupple>>() {
		}.getType();
		Collection<Tupple> son = new Gson().fromJson(s, type);
		return son.stream().collect(Collectors.toMap(e -> e.tm, e -> e.description));
	}
}
