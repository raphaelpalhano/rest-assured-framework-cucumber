package br.com.sulamerica.contasmedicas.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.codemodel.JCodeModel;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsonschema2pojo.SchemaMapper;

import static br.com.sulamerica.contasmedicas.constants.PathConstants.SCHEMA_PATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Logger;

public final class SchemaGenerator {

    private static final Logger LOGGER = Logger.getLogger(SchemaGenerator.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String outputAsString(String json) throws Exception {
        JsonNode cleanedJsonNode = removeNullAndEmptyFields(objectMapper.readTree(json));
        return outputAsString(Objects.requireNonNull(cleanedJsonNode).toString(), null);
    }
    
  
    
    public static void outputAsFile(String json, String filename) throws Exception {
        FileUtils.writeStringToFile(
                new File(filename),
                cleanup(outputAsString( json)),
                "utf8");
    }

    
    public static void generateSchema(String folder, String fileName, String jsonObject) throws Exception {

        Path schemaPath = Paths.get(SCHEMA_PATH, folder);
        Path pathFile = Path.of(schemaPath + File.separator + fileName + ".json");
        if (Files.notExists(pathFile)) {
            System.out.println("Arquivo não encontrado, criando o arquivo...");
            String schemaJson = outputAsString(jsonObject);

            try {
                Files.createDirectories(schemaPath);
                Files.writeString(pathFile, schemaJson);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O arquivo já existe!");
        }
    }
    
    public static void outputAsPOJO(String json, String packageName,
                                    String outputDirectory) throws Exception {
        String schema = SchemaGenerator.outputAsString(json);
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

        //output.append("}");

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
                if (node != null) {
                    result.append("object\", \"properties\": ");
                    result.append(outputAsString( node.toString(), JsonNodeType.OBJECT));
                    result.append("},");
                }

                break;
            case STRING:
                result.append("string\" },");
                break;
            case NULL:
                result.append("null\" },");
                break;
		default:
			break;
        }

        return result.toString();
    }


    private static JsonNode removeNullAndEmptyFields(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            ObjectNode cleanedObjectNode = objectMapper.createObjectNode();

            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValue = jsonNode.get(fieldName);

                // Recursivamente remover campos do tipo objeto
                cleanedObjectNode.set(fieldName, removeNullAndEmptyFields(fieldValue));
            }

            return cleanedObjectNode;
        } else if (jsonNode.isArray()) {
            ArrayNode cleanedArrayNode = objectMapper.createArrayNode();

            for (JsonNode element : jsonNode) {
                // Recursivamente remover elementos do tipo array
                cleanedArrayNode.add(removeNullAndEmptyFields(element));
            }

            return cleanedArrayNode.isEmpty() ? null : cleanedArrayNode;
        } else {
            // Remover campos nulos ou vazios
            return (jsonNode.isNull() || (jsonNode.isTextual() && jsonNode.asText().isEmpty())) ? null : jsonNode;
        }
    }

    private static String cleanup(String dirty) throws Exception {
        JSONObject rawSchema = new JSONObject(new JSONTokener(dirty));
        //Schema schema = SchemaLoader.load(rawSchema);
        return rawSchema.toString();

    }
}