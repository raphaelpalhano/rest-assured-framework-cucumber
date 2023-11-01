package br.com.sulamerica.contasmedicas.util;

import java.util.HashMap;
import java.util.Map;

public class XmlContasMedicas {
	private static Map<String, String> modificador;

	
	public static void modificadorDeXmlGuias(Map<String, String> xmlData, String nameFile) throws Exception {
		modificador = new HashMap<String, String>();
		modificador.put("ans:numeroGuiaPrestador", RandomData.uniquePositiveNumber().toString());
		modificador.put("ans:numeroLote", RandomData.uniquePositiveNumber().toString());
		modificador.putAll(xmlData);
		XmlManager.updateXml(modificador, nameFile);
		FileManager.zipFixtureFiles(nameFile);


	}
}
