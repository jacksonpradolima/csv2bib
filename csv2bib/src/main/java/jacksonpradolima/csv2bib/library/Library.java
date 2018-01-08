package jacksonpradolima.csv2bib.library;

import jacksonpradolima.csv2bib.utils.ExporterFactory;
import jacksonpradolima.csv2bib.utils.FileReader;

/**
 * Definion for Library 
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public interface Library {

	Library withExtension(String extension);
	
	<T extends FileReader> Library withReader(T reader);
	
	ExporterFactory createFactory();

}
