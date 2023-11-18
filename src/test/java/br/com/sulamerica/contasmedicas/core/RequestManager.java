package br.com.sulamerica.contasmedicas.core;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.groovy.parser.antlr4.GroovyParser.ClassNameContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.sulamerica.contasmedicas.constants.PathConstants;
import br.com.sulamerica.contasmedicas.model.EnvObject;
import br.com.sulamerica.contasmedicas.model.Request;
import br.com.sulamerica.contasmedicas.model.ScenarioObject;
import br.com.sulamerica.contasmedicas.util.FileManager;
import br.com.sulamerica.contasmedicas.util.JsonUtil;
import br.com.sulamerica.contasmedicas.util.SimpleJsonManager;
import br.com.sulamerica.contasmedicas.util.StringManager;
import io.restassured.RestAssured;
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
	
	public static Response postFile(String filename) throws Exception {
		String filePath = PathConstants.FIXTURES_PATH;
		return given().contentType("multipart/form-data")
				.multiPart("file", FileManager.getRecursiveFiles(filePath, filename)).when().post();
	}

	
	public static Response putFile(String url, String filename) throws Exception {
		RestAssured.urlEncodingEnabled = false;
		Map<String, String> headers = new HashMap<String, String>();
		headers.putAll(EnvObject.getToken());
		File filePath = FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH + File.separator + "zipado", filename) ;
		Response response = given().baseUri(url)
				.with().headers(headers).when()
				.body(filePath).when().put();
		
		response.then().log().all();
		
		return response.then().extract().response();
	}
	
	
	public static HttpResponse putFileClient(String url, String path, String filenamePath) throws Exception {
		
		File filePath = FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH + File.separator + path, filenamePath) ;
        FileEntity fileEntity = new FileEntity(filePath, ContentType.APPLICATION_OCTET_STREAM);
 
	    HttpPut request = new HttpPut(url);
	    request.setEntity(fileEntity);
	    
	    HttpClient httpClient = HttpClientBuilder.create().build();

	   HttpResponse res = httpClient.execute(request, response -> {
	        System.out.println(response.getStatusLine());
	        return response;
	    });
	   
		return res;
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
		Response response = given().contentType(EnvObject.getContent_type()).with().body(json).headers(EnvObject.getHeader()).headers(EnvObject.getToken()).when().post(Request.getPath());
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


	public static Response post(String body) throws Exception {

		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getHeaders());
		headers.putAll(EnvObject.getToken());

		Response response = given().contentType(EnvObject.getContent_type()).with().body(body).headers(headers)
				.when().post(Request.getPath());
		response.then().log().all();

		return response;


	}

	public static Response put(Map<String, String> pathParam, String body) throws Exception {

		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getHeaders());
		headers.putAll(EnvObject.getToken());

		Response response = given().contentType(EnvObject.getContent_type()).basePath(Request.getPath()).pathParams(pathParam).with().body(body).headers(headers)
				.when().post();
		response.then().log().all();

		return response;


	}

	public static Response put(String pathParam, String body) throws Exception {

		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getHeaders());
		headers.putAll(EnvObject.getToken());

		Response response = given().contentType(EnvObject.getContent_type()).basePath(Request.getPath()).with().body(body).headers(headers)
				.when().put(pathParam);
		response.then().log().all();

		return response;


	}


	public static Response post(String url, JSONObject json) throws Exception {
		return given().with().body(json.toString()).when().post(url);
	}

	public static Response get(HashMap<String, Object> headers) {
		return given().baseUri("http://localhost").basePath("user/details").headers(headers).when().get();
	}

	public static Response get() {
		String path = Request.getPath();;	
		Response giveResponse = given().with().when().get(path).then().extract().response();
		giveResponse.then().log().all();
		return giveResponse;
	}
	
	public static Response getForSearch(String statusValor) {
		String PATHURL = Request.getPath();	
		Response give = given().with().when().get(EnvObject.getBase_url() + PATHURL + statusValor).then().extract().response();
		give.then().log().all();
		return give;
	}
	
	
	public static Response putWithFile(String jsonName) throws Exception {
		jsonManipulator = new JsonUtil(jsonName);
		Response put = (Response) given().with().body(jsonManipulator.getJSONBodyString()).when().put(Request.getPath());
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
	
	
	public static Response getWithPathParam(String endpoint, String param) {
		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getToken());

		Response response = given().with().headers(headers).when().get(Request.getPath() + endpoint + param);
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


	public static Response postWithQueryParams(String endpoint, Map<String, String> parametros) throws Exception {

		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getToken());

		Response response = given().queryParams(parametros).with().headers(headers).when().post(endpoint);
		response.then().log().all();

		return response;
	}
	
	
	public static Response postWithParams(String endpoint, Map<String, String> parametros) throws Exception {

		Map<String, String> headers = EnvObject.getHeaders();
		headers.putAll(EnvObject.getHeaders());
		headers.putAll(EnvObject.getToken());
		
		
		Response response = given().params(parametros).with().headers(headers).when().post(endpoint);
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


	public static String generateToken() throws Exception {
		jsonManipulator = new JsonUtil();
		String token = null;
		String body = "";
		Map<String, String> paramsRequest = StringManager.conversorStringToMap(EnvObject.getAuthetication().get("Body").toString());
		Map<String, String> headers = StringManager.conversorStringToMap(EnvObject.getAuthetication().get("Headers").toString());
		headers.put("Authorization", "Basic dnBwLXZhbGlkYS10b2tlbi1oOmRmOWFmMzlmLWNlODItNGRmNy1hMmRhLWViYjc1YWE0ODkwZA==");
		for(Entry<String, String> entry: paramsRequest.entrySet()) {
			body += entry.getKey() + "=" + entry.getValue() + "&";

		}
		if (!body.isEmpty() && body.endsWith("&")) {
		    // Remova o '&' do final da string
		    body = body.substring(0, body.length() - 1);
		}
		
		Response response = 
				given().headers(headers).config(RestAssuredConfig.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).and().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
	             .body(body)
                .when()
	                .post(EnvObject.getAuthenticate_url());
		response.then().log().all();

		
		if (response.getStatusCode() == 200) {
			token =  String.valueOf(jsonManipulator.getBodyString(response.getBody().asString(), "access_token"));
		}else {
			try {
				throw new Exception("Não foi possível obter o token. A url de autenticação retornou status code [ "+response.getStatusCode()+ " ].");
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Servico de autenticacao com problemas, verifique se o autenticador nao esta fora!", e);
			}
		} 
		
		return token;
	}
	
	
}