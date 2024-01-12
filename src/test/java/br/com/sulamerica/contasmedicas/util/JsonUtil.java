package br.com.sulamerica.contasmedicas.util;



import org.apache.groovy.parser.antlr4.GroovyParser.ClassNameContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.ParseException;

import br.com.sulamerica.contasmedicas.constants.PathConstants;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * @author Raphael Angel
 * @version 0.1
 *
 *
 * <h1>Manipulador de JSON</h1>
 *
 *
 * <h3>M�todos que a classe possui</h3>
 * <ul>
 *     <li>
 *         <p>getJSONBody: recebe um argumento do caminho do JSON (String pathDoJson)
 *         e devolve o JSON inteiro em formato string em uma linha
 *         </p>
 *
 *     </li>
 *
 *     <li>
 *         <p>
 *             getKeyString: recebe um argumento do tipo String chave do JSON (String key)
 *             devolve o valor da chave passada.
 *             Ex: "nome": "Joao"
 *                  getKeyString(nome) == Joao
 *         </p>
 *     </li>
 *
 *
 *
 * </ul>
 *
 */

public class JsonUtil {
    JSONObject jsonObjct;
    JSONTokener tokener;

    JSONArray jsonArray;
    String[] jsonBody;
    String path;

    private static final Logger LOGGER = Logger.getLogger(ClassNameContext.class.getName());


    public JsonUtil(){
    }

    public JsonUtil(String arquivo) {
		try {
			File payload = FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH + File.separator + "json", arquivo);
			String jsonBodyRead = getJSONFile(payload.toString());
			jsonBody = jsonBodyRead.split("");
			if (!jsonBody[0].equals("[")) {
                tokener = new JSONTokener(new FileReader(payload));
                jsonObjct = new JSONObject(tokener);
            }
			if (jsonBody[0].equals("[")) {
                tokener = new JSONTokener(new FileReader(payload));
                jsonArray = new JSONArray(tokener);

            }
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "nao foi possivel carregar o arquivo JSON", e);
		}
	}

    public JSONObject parseJson(String valueJson) {
        tokener = new JSONTokener(valueJson);
        jsonObjct = new JSONObject(tokener);
        return jsonObjct;
    }

    public JSONArray parseJsonArray(String valueJson) {
        tokener = new JSONTokener(valueJson);
        jsonArray = new JSONArray(tokener);
        return jsonArray;
    }

    public JSONObject JsonResponse(String responseBody) throws ParseException {

        return parseJson(responseBody);
    }
    
    public JSONObject getJSONBodyObject(){
        return jsonObjct;
    }

    public JSONArray getArrayObject(){
        return jsonArray;
    }


    public JSONObject getJSONBodyInArray(int index){
        return (JSONObject) jsonArray.get(index);

    }



    public JSONObject getJsonWithDot(String keys) {
        JSONObject json = jsonObjct;
        String[] keysValues = keys.split("\\.");
        for (String k : keysValues) {
            json = (JSONObject) json.get(k);
            if (json.isEmpty()) {
                return null;
            }
        }

        return json;
    }

    public JSONObject getJsonWitKey(String key) {
        return (JSONObject) jsonObjct.get(key);
    }

    public Object decodification(String code) throws ParseException {
        String[] parts = code.split("\\.");
        Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(parts[1]));
        JSONObject valor = new JSONObject(payload);
        JSONObject valorKeyCloak = (JSONObject) valor.get("keycloak");
        return valorKeyCloak.get("access_token");
    }

    public Object getKeyObject(String key){
        return jsonObjct.get(key);
    }

    public JSONObject getKeyJsonObject(String key){
        return (JSONObject) jsonObjct.get(key);
    }


    public Object getKeyArray(Integer index, String key){
        JSONObject jsonArraySelect = (JSONObject) jsonArray.get(index);
        return jsonArraySelect.get(key);
    }

    public String getValueArrayInObjectUsingThreeKey(Integer index, String key1, String key2, String key3) {
        JSONObject jsonObjectArray = getJSONBodyInArray(index);
        JSONObject object = (JSONObject) jsonObjectArray.get(key1);
        JSONObject object1 = (JSONObject) object.get(key2);
        return String.valueOf(object1.get(key3));
    }

    public Object getValueArrayInArrayJSON(Integer index, String key, int indexObject, String keyObject){
        JSONObject jsonObjectArray = getJSONBodyInArray(index);
        JSONArray keyArrayValue = (JSONArray) jsonObjectArray.get(key);
        JSONObject object = (JSONObject) keyArrayValue.get(indexObject);
        if(object.get(keyObject).getClass().equals(Long.class))
            return Integer.parseInt(String.valueOf(object.get(keyObject)));
        return object.get(keyObject);

    }

    public Object getValueObjectJson(String keyArray, int nElement, String keyObject){
        JSONArray array = (JSONArray) jsonObjct.get(keyArray);
        JSONObject objeto = (JSONObject) array.get(nElement);
        if(objeto.get(keyObject).getClass().equals(Long.class))
            return Integer.parseInt(String.valueOf(objeto.get(keyObject)));
        return objeto.get(keyObject);

    }

    public String getValueObjectIntoObject(JSONObject json,String keyObject, String keyChild){
        JSONObject objectFather = (JSONObject) json.get(keyObject);
        return (String) objectFather.get(keyChild);
    }

    
    public Object getValueObjectIntoObject(String keyObject, String keyChild){
        JSONObject objectFather = (JSONObject) this.jsonObjct.get(keyObject);
        return objectFather.get(keyChild);
    }
    
    public String getJSONFile(String nomePath){
        try {
            return new String(Files.readAllBytes(Paths.get(nomePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

   
	public String getBodyString(String JsonBody, String key) {
        JSONObject objectJSON = new JSONObject(JsonBody);
        return String.valueOf(objectJSON.get(key));

	}

	public String getBodyStringIntoList(String JsonBody, String key, Integer indexObject, String keySecond) {
        JSONObject objectJSON = new JSONObject(JsonBody);
        JSONArray listArray = (JSONArray) objectJSON.get(key);
        JSONObject objectInto = (JSONObject) listArray.get(indexObject);
        return String.valueOf(objectInto.get(keySecond));


	}
	
    public String getJSONBodyString() {
		return jsonObjct.toString();
	}
	
	public JSONArray getReponseListJSON(String jsonBody){
        return parseJsonArray(jsonBody);

	}

	
}



