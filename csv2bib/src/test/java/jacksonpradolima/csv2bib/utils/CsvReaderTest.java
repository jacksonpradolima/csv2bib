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
package jacksonpradolima.csv2bib.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

public class CsvReaderTest {

    @Test
    public void readsHeader() {
        CsvReader csvReader = createCsvReader();
        List<String> header = csvReader.readHeader();

        assertThat(header, contains("Item Title", "Publication Title", "Book Series Title", "Journal Volume", "Journal Issue", "Item DOI", "Authors", "Publication Year", "URL", "Content Type"));
        assertThat(header, hasSize(10));
    }

    @Test
    public void readsRecords() {
        CsvReader csvReader = createCsvReader();
        List<List<String>> records = csvReader.readRecords();        
        assertThat(records, hasSize(343));
    }

    private CsvReader createCsvReader() {
        try {
            Path path = Paths.get("src/test/resources", "SpringerLink.csv");
            Reader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            return new CsvReader(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
