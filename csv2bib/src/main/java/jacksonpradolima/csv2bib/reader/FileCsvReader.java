package jacksonpradolima.csv2bib.reader;

import java.util.List;

import jacksonpradolima.csv2bib.utils.FileReader;

/**
 * Define Behavior Reader for CSV file
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public interface FileCsvReader extends FileReader{

	List<String> readHeader();
	
	Integer getDoiIndex();
	
	String getFileName();
}
