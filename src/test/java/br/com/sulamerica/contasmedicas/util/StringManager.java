package br.com.sulamerica.contasmedicas.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringManager {
	public static String substringByRegex(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		String matcherStr = null;
		if (matcher.find()) {
			matcherStr = matcher.group();
		}
		return matcherStr;
	}

	public static List<String> getListMatcherByRegex(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		List<String> matcherList = new LinkedList<String>();
		while (matcher.find()) {
			matcherList.add(matcher.group());
		}
		return matcherList;
	}

	public static Map<String,String> conversorStringToMap(String value){
		Map<String, String> map = new HashMap<>();
	    for(String str : value.substring(1,value.length() - 1).split(",")) {
	        String[] data = str.split("=");
	        map.put(data[0].replaceFirst("\\s?", ""), data[1].replaceFirst("\\s?", ""));
	        
	    }
	    
	    return map;
	}


	public static Map<String, String> conversorJsonToMap(String json) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> resultMap = objectMapper.readValue(json, Map.class);
		System.out.println("Map result: " + resultMap);

		return resultMap;
	}
	
	
	
}
