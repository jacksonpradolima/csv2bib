package jacksonpradolima.csv2bib.utils;

/**
 * Define behavior for Factory exporter References files
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public interface ExporterFactory {

	Exporter createrExporter() throws Exception;

}
