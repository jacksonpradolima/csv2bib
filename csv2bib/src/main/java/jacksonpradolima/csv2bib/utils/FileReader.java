package jacksonpradolima.csv2bib.utils;

import java.util.List;

/**
 * Define behavior Reader for files.
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public interface FileReader {
	
	List<List<String>> readAllRecords() throws Exception;
	
}
