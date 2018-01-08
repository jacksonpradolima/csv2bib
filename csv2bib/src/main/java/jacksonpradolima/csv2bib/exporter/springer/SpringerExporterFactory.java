package jacksonpradolima.csv2bib.exporter.springer;

import java.util.List;
import java.util.stream.Collectors;

import jacksonpradolima.csv2bib.reader.FileCsvReader;
import jacksonpradolima.csv2bib.utils.Exporter;
import jacksonpradolima.csv2bib.utils.ExporterFactory;
import jacksonpradolima.csv2bib.utils.Extension;

/**
 * Factory exporter Springer Digital Library
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public class SpringerExporterFactory implements ExporterFactory {

	private static final String URL = "https://citation-needed.springer.com/v2/references/%s?%s";
	private static final String FORMAT = "format=%s&flavour=citation";
	
	private Extension extension;
	private FileCsvReader reader;

	public SpringerExporterFactory(Extension extension, FileCsvReader reader) {
		this.extension = extension;
		this.reader = reader;
	}

	public List<String> generateURLs() throws Exception {
		List<String> urls = reader.readAllRecords()
								.parallelStream()
								.map(m -> urlCreator(m.get(reader.getDoiIndex())))
								.collect(Collectors.toList());
		if (urls.isEmpty()) {
			throw new Exception("Urls not found.");
		}
		return urls;
	}
	
	private String urlCreator(String doi) {
		return String.format(URL, doi, formatCitationExporter()).replace("\"", "");
	}

	private String formatCitationExporter() {
		return String.format(FORMAT, extension.getFormat());
	}

	@Override
	public Exporter createrExporter() throws Exception {
		if (Extension.BIB.equals(extension)) {
			return new SpringerBibTex(reader.getFileName(), generateURLs());
		} else if (Extension.RIS.equals(extension)) {
			return new SpringerRis(reader.getFileName(), generateURLs());
		}
		
		throw new Exception("No exporter defined");
	}

}
