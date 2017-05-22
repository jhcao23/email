package touchmars.technology.email;

import java.io.IOException;
import java.io.Serializable;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface ReportService extends Serializable {
	
	public Task generateTaskFromExcel(String filename) throws EncryptedDocumentException, InvalidFormatException, IOException;
	
	public Task getTask(Long taskId);

}
