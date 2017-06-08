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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper to get bibtex informations from urls
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 */
public class BibTex {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BibTex.class);
    
    /**
     * Encoding
     */
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    
    /**
     * Get bibtex from urls. It was adapted from:
     * https://www.crossref.org/labs/resolving-citations-we-dont-need-no-stinkin-parser/
     *
     * @param urls
     * @return List of bibtex
     */
    public static List<String> getBibTextFromCrossref(List<String> urls) {
        ProgressBar pb = new ProgressBar("[csv2bib]", urls.size(), 1000, System.out, ProgressBarStyle.UNICODE_BLOCK)
                .start()
                .maxHint(urls.size());

        List<String> bibs = new ArrayList();

        urls.parallelStream().forEach(strUrl -> {
            try {
                Process process = new ProcessBuilder("curl", "-LH", "Accept: application/x-bibtex; style=bibtex", strUrl)
                        .redirectErrorStream(true)
                        .start();

                try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), ENCODING))) {

                    StringBuilder bibtex = new StringBuilder();
                    String inputLine;

                    boolean startedBibtex = false; // A flag to know when the bibtex starts
                    while ((inputLine = in.readLine()) != null) {
                        if (!startedBibtex) {
                            if (inputLine.startsWith("@")) {
                                startedBibtex = true;
                            }
                        }

                        if (startedBibtex) {
                            bibtex.append(inputLine).append("\n");
                        }
                    }

                    bibs.add(bibtex.toString());
                } catch (IOException ex) {
                    throw ex;
                }

                pb.step();
                pb.setExtraMessage("Getting BibText informations...");

            } catch (IOException e) {
                logger.error("Error to get file at " + strUrl, e);
            }
        });

        pb.stop();

        return bibs;
    }

    /**
     * Get bibtex direct from Digital Libraries urls
     *
     * @param urls
     * @return List of bibtex
     */
    public static List<String> getBibTextFromDigitalLibrary(List<String> urls) {
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
}
