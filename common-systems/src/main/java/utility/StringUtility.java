package utility;

import java.util.HashMap;
import java.util.Map;

public class StringUtility {

	public static Map<String, String> convertToMapByChar(String[] args,
			String splitString) {
		Map<String, String> resourceMap = new HashMap<String, String>();
		for (String str : args) {
			String[] kvPair = str.split(splitString);
			System.err.println(kvPair[0] + "=" + kvPair[1]);
			resourceMap.put(kvPair[0], kvPair[1]);
		}
		return resourceMap;
	}

	public static String[] appendStringToAll(String[] data, String appender) {
		for (int i = 0; i < data.length; i++) {
			String str = data[i];
			data[i] = appender + str;
		}
		return data;
	}

	public static String[] convertToArrayByChar(String str, String splitString) {
		String[] resourceMap = str.split(splitString);
		for (int i = 0; i < resourceMap.length; i++) {
			System.err.println(resourceMap[i]);
		}
		return resourceMap;
	}
}
