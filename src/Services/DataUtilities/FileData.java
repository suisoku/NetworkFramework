package Services.DataUtilities;

import java.io.IOException;
import java.io.Serializable;

public class FileData implements Serializable{


	private static final long serialVersionUID = 1L;
	private String filenameStart;
	private String filenameEnd;
	private byte [] bytedata;
	
	public FileData(String filenameStart , String filenameEnd) {
		this.filenameStart = filenameStart;
		this.filenameEnd = filenameEnd;
	}
	
	
	public void serializeFile() throws IOException {
		bytedata = ByteFileUtility.FileToBytes(filenameStart);
	}
	public void unserializeFile() throws IOException {
		ByteFileUtility.BytesToFile(bytedata, filenameEnd);
	}
	
	
}
