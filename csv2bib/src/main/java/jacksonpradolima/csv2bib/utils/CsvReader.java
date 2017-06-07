package jacksonpradolima.csv2bib.utils;

import java.io.BufferedReader;
import static java.io.File.separator;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 *
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 */
public class CsvReader {

    private static final String SEPARATOR = ",";

    private final BufferedReader reader;

    private boolean readedHeader = false;

    public CsvReader(Reader source) {
        this.reader = new BufferedReader(source);
    }

    public List<String> readHeader() {
        readedHeader = true;
        return reader.lines()
                .findFirst()
                .map(line -> Arrays.asList(line.split(SEPARATOR)))
                .get();
    }

    public List<List<String>> readRecords() {
        return reader.lines()
                .skip(readedHeader ? 0 : 1)
                .map(line -> Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")))
                .collect(Collectors.toList());
    }

}
