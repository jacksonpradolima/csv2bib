package jacksonpradolima.csv2bib.runner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.Properties;

import jacksonpradolima.csv2bib.executor.CsvExecutor;
import jacksonpradolima.csv2bib.executor.Executor;
import jacksonpradolima.csv2bib.reader.CsvReader;

/**
 * Execute parse bib using .properties
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */

public class RunByProperties implements Run {

	private static final String PROPERTIES_FILE = "config.properties";
	private final Properties properties = readProperties();

	@Override
	public void run() throws IOException {

		Executor executor = new CsvExecutor()
				.withReader(CsvReader.getInstance(properties.getProperty("filePath"), properties.getProperty("doiIndexColumn")))
				.withFileExtension(properties.getProperty("fileExtension", "bib"))
				.withDigitalLib(properties.getProperty("digitalLib"));

		executor.run();
	}

	public Properties readProperties() {
		try {
			FileInputStream inputStream;
			inputStream = new FileInputStream(Paths.get(PROPERTIES_FILE).toFile());
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
