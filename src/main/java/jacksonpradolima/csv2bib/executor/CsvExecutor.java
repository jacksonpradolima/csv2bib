package jacksonpradolima.csv2bib.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jacksonpradolima.csv2bib.library.Library;
import jacksonpradolima.csv2bib.reader.FileCsvReader;
import jacksonpradolima.csv2bib.utils.DigitalLibraries;
import jacksonpradolima.csv2bib.utils.Exporter;
import jacksonpradolima.csv2bib.utils.ExporterFactory;

/**
 * Executor of exporter using file extension .csv
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public class CsvExecutor implements Executor{

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvExecutor.class);

	private FileCsvReader reader;
	private String digitalLib;
	private String fileExtension;
	
	public  CsvExecutor withReader(FileCsvReader reader) {
		this.reader = reader;
		return this;
	}
	
	public CsvExecutor withDigitalLib(String digitalLibName) {
		this.digitalLib = digitalLibName;
		return this;
	}
	
	public CsvExecutor withFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
		return this;
	}
	
	@Override
	public void run() {
		try {
			Library digitalLibray = DigitalLibraries.getDigitalLibrary(digitalLib).getInstance();
			ExporterFactory factory = digitalLibray.withExtension(fileExtension).withReader(reader).createFactory();
			Exporter exporter = factory.createrExporter();
			exporter.write();
		} catch (Exception e) {
			LOGGER.error("Error in to generate " + fileExtension +" file.", e);
		}
	}

}
