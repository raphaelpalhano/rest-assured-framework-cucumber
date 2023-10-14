package br.com.organization.project.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
}
