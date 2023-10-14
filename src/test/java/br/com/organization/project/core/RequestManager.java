package br.com.organization.project.core;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.groovy.parser.antlr4.GroovyParser.ClassNameContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.organization.project.constants.PathConstants;
import br.com.organization.project.model.EnvObject;
import br.com.organization.project.model.ScenarioObject;
import br.com.organization.project.util.FileManager;
import br.com.organization.project.util.JsonUtil;
import br.com.organization.project.util.SimpleJsonManager;
import br.com.organization.project.util.StringManager;
import io.restassured.config.DecoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;


public class RequestManager {
	private static Logger LOGGER = Logger.getLogger(ClassNameContext.class.getName());
	private static JsonUtil jsonManipulator;
	public static final String TOKEN_FIELD = "Token";
	
	private RequestManager() {
	}
	
	
	public static Response post(String jsonName) throws Exception {
		jsonManipulator = new JsonUtil(jsonName);
		
		Response response = (Response) given().with().body(jsonManipulator.getJSONBodyString()).when().post();
		response.then().log().all();
		return response;
	}
	
	public static Response postFile(String filename) throws Exception {
		String filePath = PathConstants.SPREADSHEETS_PATH;
		return given().contentType("multipart/form-data")
				.multiPart("file", FileManager.getRecursiveFiles(filePath, filename)).when().post();
	}

	public static Response post(HashMap<String, Object> headers, String jsonName) throws Exception {
		File payload = FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH, jsonName);
		JSONObject jsonObject = SimpleJsonManager.getJsonObject(payload);
		return given().with().body(jsonObject.toString()).headers(headers).when().post();
	}
	
	
	public static Response post() throws Exception {
		JSONArray json = SimpleJsonManager.getJson();
		
		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getHeaders());
		Response response = given().contentType(EnvObject.getContent_type()).with().body(json).headers(EnvObject.getHeader()).headers(EnvObject.getToken()).when().post();
		response.then().log().all();
		
		if(response.getStatusCode() == 200)
			return response.then().extract().response();
		else {
			try {
				throw new Exception("Nao foi possivel fazer a requisição. O response retornou status code [ "+response.getStatusCode()+ " ].");
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "problema ao gerar a excecao do status code!", e);
			}
		}
		return null;
	}
	

	public static Response post(String url, JSONObject json) throws Exception {
		return given().with().body(json.toString()).when().post(url);
	}

	public static Response get(HashMap<String, Object> headers) {
		return given().baseUri("http://localhost").basePath("user/details").headers(headers).when().get();
	}

	public static Response get() {
		String PATHURL = LinkedHashMap.class.cast(EnvObject.getPath_url().get(ScenarioObject.getPath_url())).get("path_url").toString();	
		Response giveResponse = given().with().when().get(EnvObject.getBase_url() + PATHURL).then().extract().response();
		giveResponse.then().log().all();
		return giveResponse;
	}
	
	public static Response getForSearch(String statusValor) {
		String PATHURL = LinkedHashMap.class.cast(EnvObject.getPath_url().get(ScenarioObject.getPath_url())).get("path_url").toString();	
		Response give = given().with().when().get(EnvObject.getBase_url() + PATHURL + statusValor).then().extract().response();
		give.then().log().all();
		return give;
	}
	
	
	public static Response put(String jsonName) throws Exception {
		jsonManipulator = new JsonUtil(jsonName);
		Response put = (Response) given().with().body(jsonManipulator.getJSONBodyString()).when().put();
		put.then().log().all();
		return put;
	}
	
	
	public static boolean contains(String bodyResponse, String valueSpecific) {
		Boolean validator = false;
		jsonManipulator = new JsonUtil();
		JSONArray listJson = jsonManipulator.getReponseListJSON(bodyResponse);
		Iterator<?> iterator = listJson.iterator();
		while(iterator.hasNext()) {
			JSONObject object = (JSONObject) iterator.next();
			if(object.containsValue(valueSpecific))
				validator = true;
				break;
			
		}
		return validator;
		
	}	
	
	public static Response delete(String id) {
		String PATHURL = LinkedHashMap.class.cast(EnvObject.getPath_url().get(ScenarioObject.getPath_url())).get("path_url").toString();	
		return given().with().when().delete(EnvObject.getBase_url() + PATHURL);
	}
	
	public static Response delete(HashMap<String, Object> headers, String payload) {
		return given().contentType("aplication/json").body(payload).baseUri("http://localhost").basePath("user/details")
				.headers(headers).when().delete();
	}

	public static Response patch(String jsonName) {
		try {
			jsonManipulator = new JsonUtil(jsonName);
			return (Response) given().with().body(jsonManipulator.getJSONBodyString()).when().patch();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "nao foi possivel gerar a alteracao", e);
		}
		return null;
	}

	
	public static String generateToken(String codEmpresa) throws Exception {
		jsonManipulator = new JsonUtil();
		String token = null;
		Map<String, String> paramsRequest = StringManager.conversorStringToMap(EnvObject.getAuthetication().get("Body").toString().replaceFirst("\\d{1,5}", codEmpresa));
		
		String body = String.format("username=%s&password=%s&cod_empresa=%s", paramsRequest.get("username"), paramsRequest.get("password"), paramsRequest.get("cod_empresa"));
		HashMap<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type","application/x-www-form-urlencoded");
		
		Response response = 
				given().headers(headerMap).config(RestAssuredConfig.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).and().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
	             .body(body)
                .when()
	                .post(EnvObject.getAuthenticate_url());
		
		if (response.getStatusCode() == 201) {
			token =  (String) jsonManipulator.decodification(response.then().extract().response().getBody().asString());
		}else {
			try {
				throw new Exception("Não foi possível obter o token. A url de autenticação retornou status code [ "+response.getStatusCode()+ " ].");
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Servico de autenticacao com problemas, verifique se o autenticador nao esta fora!", e);
			}
		} 
		
		return token;
	}
	
	
	public static String getToken() throws Exception {
		String token = null;
		HashMap<?, ?> authenticationMap = EnvObject.getAuthetication();
		boolean containsTokenField = authenticationMap.containsKey(TOKEN_FIELD);
		if (containsTokenField && (authenticationMap.get(TOKEN_FIELD) != null)) {
			if (!(authenticationMap.get(TOKEN_FIELD).toString().isEmpty())) {
				token = EnvObject.getAuthetication().get(TOKEN_FIELD).toString();
			}
		} else {
			if (authenticationMap.containsKey(TOKEN_FIELD)) {
				authenticationMap.remove(TOKEN_FIELD);
			}
			String url = EnvObject.getBase_url() + EnvObject.getAuthenticate_url();
			JSONObject jsonStr = new JSONObject(LinkedHashMap.class.cast(authenticationMap.get("Body")));
			JSONObject jsonPayloadObject = (JSONObject) new JSONParser().parse(jsonStr.toJSONString());
			Response response = post(url, jsonPayloadObject);
			String responseBody = response.getBody().asString();
			if (response.getStatusCode() != 404) {
				token = StringManager.getListMatcherByRegex(responseBody, "token.\\:[^,]*").get(0).split("\\s*:\\s*")[1]
						.replaceFirst("\\\"", "").replaceAll("\\\"$", "");
			}else {
				throw new Exception("Não foi possível obter o token. A url de autenticação retornou status code [ "+response.getStatusCode()+ " ].");
			}
		}
		return token;
	}

}