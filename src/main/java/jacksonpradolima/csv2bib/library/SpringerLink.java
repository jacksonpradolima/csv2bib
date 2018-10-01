package jacksonpradolima.csv2bib.library;

import jacksonpradolima.csv2bib.exporter.springer.SpringerExporterFactory;
import jacksonpradolima.csv2bib.reader.FileCsvReader;
import jacksonpradolima.csv2bib.utils.ExporterFactory;
import jacksonpradolima.csv2bib.utils.Extension;
import jacksonpradolima.csv2bib.utils.FileReader;

/**
 * Representation for Springer Link Digital Library
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public class SpringerLink implements Library {
	
	private Extension extension;
	private FileCsvReader csvFileReader;
	
	@Override
	public Library withExtension(String extension) {
		this.extension = Extension.getByDescription(extension);
		return this;
	}

	@Override
	public ExporterFactory createFactory() {
		return new SpringerExporterFactory(extension, csvFileReader);
	}

	@Override
	public <T extends FileReader> Library withReader(T reader) {
		this.csvFileReader = (FileCsvReader) reader;
		return this;
	}


}
