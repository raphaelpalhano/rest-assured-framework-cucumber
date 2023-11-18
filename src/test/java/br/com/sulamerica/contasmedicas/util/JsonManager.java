package br.com.sulamerica.contasmedicas.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonManager {
	public static JsonObject getJsonObject(File jsonFile) throws IOException {
		String jsonStr = new String(Files.readAllBytes(jsonFile.toPath()));
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(jsonStr).getAsJsonObject();
	}

	public static String getJsonAsString(File jsonFile) throws IOException {
		String jsonStr = new String(Files.readAllBytes(jsonFile.toPath()));
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(jsonStr).getAsJsonObject().toString();
	}

	public static String convertMapToJson(Map<String, String> object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
