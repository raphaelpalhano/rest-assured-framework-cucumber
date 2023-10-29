package br.com.sulamerica.contasmedicas.util;



import org.apache.groovy.parser.antlr4.GroovyParser.ClassNameContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    JSONParser leitor;
    JSONObject jsonObjct;
    JSONArray jsonArray;
    String[] jsonBody;
    String path;

    private static Logger LOGGER = Logger.getLogger(ClassNameContext.class.getName());


    public JsonUtil(){
        leitor = new JSONParser();
        jsonArray = new JSONArray();
    }

    public JsonUtil(String arquivo) {
		try {
			File payload = FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH, arquivo);
			leitor = new JSONParser();
			jsonArray = new JSONArray();
			String jsonBodyRead = getJSONFile(payload.toString());
			jsonBody = jsonBodyRead.split("");
			if (!jsonBody[0].equals("["))
				jsonObjct = (JSONObject) leitor.parse(new FileReader(payload));
			if (jsonBody[0].equals("["))
				jsonArray = (JSONArray) leitor.parse(new FileReader(payload));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "nao foi possivel carregar o arquivo JSON", e);
		}
	}

    public JSONObject JsonResponse(String responseBody) throws ParseException {
    	jsonObjct = (JSONObject) this.leitor.parse(responseBody);
		return jsonObjct;
    }
    
    public String getJSONBodyObject(){
        return jsonObjct.toString();
    }

    public JSONObject getJSONBodyInArray(int index){
        return (JSONObject) jsonArray.get(index);

    }

    public Object decodification(String code) throws ParseException {
        String[] parts = code.split("\\.");
        Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(parts[1]));
        JSONObject valor = (JSONObject) leitor.parse(payload);
        JSONObject valorKeyCloak = (JSONObject) valor.get("keycloak");
        return valorKeyCloak.get("access_token");
    }

    public Object getKeyObject(String key){
        return jsonObjct.get(key);
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
		try {
			JSONObject objectJSON = (JSONObject) this.leitor.parse(JsonBody);
            return String.valueOf(objectJSON.get(key));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public String getBodyStringIntoList(String JsonBody, String key, Integer indexObject, String keySecond) {
		try {
			JSONObject objectJSON = (JSONObject) this.leitor.parse(JsonBody);
			JSONArray listArray = (JSONArray) objectJSON.get(key);
			JSONObject objectInto = (JSONObject) listArray.get(indexObject);
            return String.valueOf(objectInto.get(keySecond));
            
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
    public String getJSONBodyString() {
		return jsonObjct.toJSONString();
	}
	
	public JSONArray getReponseListJSON(String jsonBody){
		try {
			JSONArray jsonArray = (JSONArray) this.leitor.parse(jsonBody);
			return jsonArray;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}



