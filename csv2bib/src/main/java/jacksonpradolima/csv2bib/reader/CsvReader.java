package jacksonpradolima.csv2bib.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

/*
 * Copyright (C) 2017 Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * CSV Reader
 * 
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 */
public class CsvReader implements FileCsvReader {

	private static final String DOI_LABEL_COLUMN = "Item DOI";
    private static final String SEPARATOR = ",";
	private static final int HEADER_INDEX = 0;

    private final BufferedReader reader;
    private final Integer doiIndex;
	private final String file;

    private CsvReader(Reader source, Integer doiIndex, String file) {
        this.file = file;
        this.reader = new BufferedReader(source);
		this.doiIndex = doiIndex == null ? findDoiIndex() : doiIndex;
    }
    
    public static final CsvReader getInstance(String file, Integer doiIndex) {
		return createReader(file, doiIndex);
    }

    public static final CsvReader getInstance(String file, String doiIndex) {
    	return createReader(file, StringUtils.isBlank(doiIndex)? null : Integer.valueOf(doiIndex));
    }
    
	private static CsvReader createReader(String file, Integer doiIndex) {
		try {
            Reader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"));
            return new CsvReader(reader, doiIndex, file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
	}
    
    @Override
    public List<List<String>> readAllRecords() throws Exception {
        List<List<String>> records = readRecords();
        records.remove(HEADER_INDEX);
        if(records.isEmpty()) {
        	throw new Exception("Empty file.");      
        }

        return records;
    }

    private List<List<String>> readRecords() {
    	return reader.lines()
    			.parallel()
    			.map(line -> Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")))
    			.collect(Collectors.toList());
    }
    
    private Integer findDoiIndex() {
    	List<String> list = readHeader();
    	return IntStream.range(0, list.size())
    			.filter(index -> StringUtils.equalsIgnoreCase(list.get(index), DOI_LABEL_COLUMN))
    			.findFirst()
    			.getAsInt();
    }

    @Override
    public List<String> readHeader() {
        return reader.lines()
                .findFirst()
                .map(line -> Arrays.asList(line.split(SEPARATOR)))
                .get();
    }

	@Override
	public Integer getDoiIndex() {
		return doiIndex;
	}
	
	@Override
	public String getFileName() {
		return file;
	}
	
}
