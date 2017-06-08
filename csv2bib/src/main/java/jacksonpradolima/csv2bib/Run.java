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
package jacksonpradolima.csv2bib;

import com.beust.jcommander.JCommander;
import jacksonpradolima.csv2bib.utils.BibTex;
import jacksonpradolima.csv2bib.utils.CsvReader;
import jacksonpradolima.csv2bib.utils.DigitalLibraries;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to parse csv file to bib file using DOI as key to get bibtex
 * informations.
 *
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 */
public class Run {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Run.class);

    /**
     * The output file is generated with the same name that the input file, but
     * with the extension .bib
     */
    private static String output_file = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            RunCommands jct = new RunCommands();
            JCommander jCommander = new JCommander(jct, args);

            if (jct.help) {
                jCommander.usage();
                return;
            }

            // Read the csv file
            List<List<String>> records = createCsvReader(jct.fileIn).readRecords(jct.header);

            if (records.isEmpty()) {
                throw new Exception("Empty file.");                
            }

            DigitalLibraries digitalLibray = DigitalLibraries.getEnum(jct.digitalLibrary);

            // Generate the urls
            List<String> urls = records.parallelStream()
                    .map(m -> String.format(digitalLibray.getUrl(), m.get(jct.doiIndex)).replace("\"", ""))
                    .collect(Collectors.toList());

            if (urls.isEmpty()) {
                throw new Exception("Urls not found.");                
            }
            
            try {
                // Get bibtex informations and generate bib file
                Files.write(Paths.get(output_file), BibTex.getBibTextFromDigitalLibrary(urls), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new Exception("Error in to generate .bib file.", e);
            }
        }catch(UncheckedIOException e){
            logger.error("Error when tried to read the csv file.", e);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Wrapper to read a csv
     *
     * @param file
     * @return
     */
    static CsvReader createCsvReader(String file) {
        try {
            Path path = Paths.get(file);
            output_file = FilenameUtils.getBaseName(path.toString()) + ".bib";

            Reader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            return new CsvReader(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
