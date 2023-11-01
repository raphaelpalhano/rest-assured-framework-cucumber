package br.com.sulamerica.contasmedicas.constants;

import java.io.File;

public class PathConstants {
	private PathConstants() {
	}


	public static final String BASE_PATH = System.getProperty("user.dir") + File.separator + "src"+ File.separator+ "test";

	public static final String FIXTURES_PATH = BASE_PATH + File.separator + "resources" + File.separator + "fixtures";
	public static final String ENV_FILE = BASE_PATH + File.separator + "java" + File.separator  + File.separator + "env";
	public static final String FEATURE_PATH = BASE_PATH + File.separator + "resources" + File.separator + "features";
	public static final String SPREADSHEETS_PATH = BASE_PATH + File.separator + "resources" + File.separator + "spreadsheets";

	public static final String SCHEMA_PATH = BASE_PATH + File.separator + "resources" + File.separator + "schemas" + File.separator;
}
