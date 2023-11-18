package br.com.sulamerica.contasmedicas.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import br.com.sulamerica.contasmedicas.constants.PathConstants;

public class FileManager {
	public static File getRecursiveFiles(String direcctory, String filename) throws Exception {
		List<File> files = new LinkedList<File>();
		Files.walk(Paths.get(direcctory)).filter(Files::isRegularFile)
				.filter((f) -> f.toFile().getName().replaceAll("\\..+", "").equals(filename))
				.forEach((f) -> files.add(f.toFile()));
		return files.get(0);

	}
	
	public static void zipFixtureFiles(String fileName) throws Exception {
		    File sourceFile = getRecursiveFiles(PathConstants.FIXTURES_PATH, fileName);
		    String path = PathConstants.FIXTURES_PATH + File.separator + "xml" + File.separator + "zipado";
	        FileOutputStream fos = new FileOutputStream(String.format("%s/%s.zip", path, fileName));
	        ZipOutputStream zipOut = new ZipOutputStream(fos);

	        FileInputStream fis = new FileInputStream(sourceFile);
	        ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
	        zipOut.putNextEntry(zipEntry);

	        byte[] bytes = new byte[1024];
	        int length;
	        while((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }

	        zipOut.close();
	        fis.close();
	        fos.close();
	    }
}

