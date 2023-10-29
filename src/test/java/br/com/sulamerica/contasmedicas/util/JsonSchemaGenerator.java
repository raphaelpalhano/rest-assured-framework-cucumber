package br.com.sulamerica.contasmedicas.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.sun.codemodel.JCodeModel;
import org.apache.commons.io.FileUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsonschema2pojo.SchemaMapper;

import static br.com.sulamerica.contasmedicas.constants.PathConstants.SCHEMA_PATH;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Logger;

public final class JsonSchemaGenerator {

    private static final Logger LOGGER = Logger.getLogger(JsonSchemaGenerator.class.getName());
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String outputAsString(String json) throws IOException {
        return cleanup(outputAsString(json, null));
    }
    
  
    
    public static void outputAsFile(String json, String filename) throws IOException {
        FileUtils.writeStringToFile(
                new File(filename),
                cleanup(outputAsString( json)),
                "utf8");
    }

    
    public static void generateSchema(String filePath, org.json.simple.JSONObject jsonObject) throws Exception {
    	String schemaJson = outputAsString(jsonObject.toJSONString());

        Path schemaPath = Paths.get(SCHEMA_PATH, filePath);
        if (Files.notExists(schemaPath)) {
            System.out.println("Arquivo não encontrado, criando o arquivo...");
            try {
                Files.createDirectories(schemaPath.getParent());
                Files.write(schemaPath, schemaJson.getBytes(StandardCharsets.UTF_8));
            	
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O arquivo já existe!");
        }
    }
    
    public static void outputAsPOJO(String json, String packageName,
                                    String outputDirectory) throws IOException {
        String schema = JsonSchemaGenerator.outputAsString(json);
        LOGGER.info("Generating POJO(s) ...");

        File fDirectory = new File(outputDirectory);
        if (!fDirectory.exists()) FileUtils.forceMkdir(fDirectory);

        JCodeModel codeModel = new JCodeModel();
        SchemaMapper mapper = new SchemaMapper();
        mapper.generate(codeModel, "", packageName, schema);
        codeModel.build(fDirectory);
        LOGGER.info("DONE.");
    }

    private static String outputAsString(String json, JsonNodeType type) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(json);
        StringBuilder output = new StringBuilder();
        output.append("{");

        if (type == null) output.append("\"type\": \"object\", \"properties\": {");

        for (Iterator<String> iterator = jsonNode.fieldNames(); iterator.hasNext();) {
            String fieldName = iterator.next();
            LOGGER.info("processing " + fieldName + "...");

            JsonNodeType nodeType = jsonNode.get(fieldName).getNodeType();

            output.append(convertNodeToStringSchemaNode(jsonNode, nodeType, fieldName));
        }

        if (type == null) output.append("}");

        output.append("}");

        LOGGER.info("generated schema = " + output.toString());
        return output.toString();
    }

    private static String convertNodeToStringSchemaNode(
            JsonNode jsonNode, JsonNodeType nodeType, String key) throws IOException {
        StringBuilder result = new StringBuilder("\"" + key + "\": { \"type\": \"");

        LOGGER.info(key + " node type " + nodeType + " with value " + jsonNode.get(key));
        JsonNode node = null;
        switch (nodeType) {
            case ARRAY :
                node = jsonNode.get(key).get(0);
                LOGGER.info(key + " is an array with value of " + node.toString());
                result.append("array\", \"items\": { \"properties\":");
                result.append(outputAsString(node.toString(), JsonNodeType.ARRAY));
                result.append("}},");
                break;
            case BOOLEAN:
                result.append("boolean\" },");
                break;
            case NUMBER:
                result.append("number\" },");
                break;
            case OBJECT:
                node = jsonNode.get(key);
                result.append("object\", \"properties\": ");
                result.append(outputAsString( node.toString(), JsonNodeType.OBJECT));
                result.append("},");
                break;
            case STRING:
                result.append("string\" },");
                break;
		default:
			break;
        }

        return result.toString();
    }

    private static String cleanup(String dirty) {
        JSONObject rawSchema = new JSONObject(new JSONTokener(dirty));
        Schema schema = SchemaLoader.load(rawSchema);
        return schema.toString();
    }
}