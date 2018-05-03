package Services.DataUtilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ByteFileUtility {
	
	public static byte[] FileToBytes(String file_name) throws IOException {

        Path path = Paths.get(file_name);
        byte[] data = Files.readAllBytes(path);
        
		return data;
    }
	
	public static void BytesToFile(byte[] data , String file_name) throws IOException {
		
		Path path = Paths.get(file_name);
		Files.write(path, data);
	}
}
