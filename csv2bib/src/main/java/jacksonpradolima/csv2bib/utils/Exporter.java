package jacksonpradolima.csv2bib.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jacksonpradolima.csv2bib.exporter.springer.SpringerBibTex;

/**
 * Definition for Digital Libraries References exporter
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public interface Exporter {

	/**
	 * Logger
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(SpringerBibTex.class);

	/**
	 * Encoding
	 */
	static final Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * Path to output file
	 * 
	 * @return
	 */
	Path getOutputFile();

	/**
	 * Get URL list for exporter
	 * 
	 * @return
	 */
	List<String> getUrls();
	
	/**
	 * Get Extension for file exporter
	 * 
	 * @return
	 */
	Extension getExtension();

	/**
	 * Execute the extract references from Digital library
	 * 
	 * @param urls
	 * @return
	 */
	List<String> execute(List<String> urls);
	
	
	/**
	 * File for write results
	 * 
	 * @return
	 */
	default String toFileName() {
		return FilenameUtils.getBaseName(getOutputFile().toString() + getExtension().getFormat());
	}

	/**
	 * Write file
	 * 
	 * @throws IOException
	 */
	default void write() throws IOException {
		LOGGER.info("File generated in: " + getOutputFile());
		Files.write(Paths.get(toFileName()), execute(getUrls()), StandardCharsets.UTF_8);
	}

}
