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
import jacksonpradolima.csv2bib.utils.CsvReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to parse csv file from SpringerLink to bib file.
 *
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 */
public class Run {

    private static final Logger logger = LoggerFactory.getLogger(Run.class);

    private static final String url_export_citation = "https://citation-needed.springer.com/v2/references/%s?format=bibtex&flavour=citation";
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static String output_file = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Converting .csv to .bib");
        RunCommands jct = new RunCommands();
        JCommander jCommander = new JCommander(jct, args);

        if (jct.help) {
            jCommander.usage();
            return;
        }

        CsvReader csvReader = createCsvReader(jct.fileIn);
        List<String> header = csvReader.readHeader();
        List<List<String>> records = csvReader.readRecords();

        if (records.isEmpty()) {
            logger.error("Empty file.");
            System.exit(-1);
        }

        logger.info("Found: " + records.size() + " citations");

        List<String> urls = new ArrayList();

        int indexDoi = header.indexOf("Item DOI");

        records.stream().forEach(record -> {
            try {
                String url = String.format(url_export_citation, record.get(indexDoi)).replace("\"", "");
                if (!url.isEmpty()) {
                    urls.add(url);
                }
            } catch (Exception e) {
                logger.error("Error in get citation from url.", e);
            }
        });

        if (urls.isEmpty()) {
            logger.error("Urls not found.");
            System.exit(-1);
        }

        try {
            List<String> bibs = getBibText(urls);
            logger.info("Generating .bib file");
            Path path = Paths.get(output_file, new String[0]);
            Files.write(path, bibs, ENCODING, new java.nio.file.OpenOption[0]);
            logger.info("The file was generated with successfull!");
        } catch (IOException e) {
            logger.info("Error in to generate .bib file.", e);
        }
    }

    static List<String> getBibText(List<String> urls) {
        ProgressBar pb = new ProgressBar("[csv2bib]", urls.size(), 1000, System.out, ProgressBarStyle.UNICODE_BLOCK)
                .start()
                .maxHint(urls.size());

        List<String> bibs = new ArrayList();

        URL uri;
        for (String strUrl : urls) {
            try {
                uri = new URL(strUrl);
                URLConnection ec = uri.openConnection();

                try (BufferedReader in = new BufferedReader(new java.io.InputStreamReader(ec.getInputStream(), ENCODING))) {

                    StringBuilder a = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        a.append(inputLine).append("\n");
                    }
                    logger.debug(a.toString());
                    bibs.add(a.toString());
                } catch (IOException ex) {
                    throw ex;
                }

                pb.step();
                pb.setExtraMessage("Getting BibText informations...");
            } catch (MalformedURLException ex) {
                logger.error("Invalid url: " + strUrl, ex);
            } catch (IOException ex) {
                logger.error("Error to get file at " + strUrl, ex);
            }
        }

        pb.stop();

        return bibs;
    }

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

    private static void printProgress(long startTime, long total, long current) {
        long eta = current == 0 ? 0
                : (total - current) * (System.currentTimeMillis() - startTime) / current;

        String etaHms = current == 0 ? "N/A"
                : String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
                        TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

        StringBuilder string = new StringBuilder(140);
        int percent = (int) (current * 100 / total);
        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
                .append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

        System.out.print(string);
    }

}
