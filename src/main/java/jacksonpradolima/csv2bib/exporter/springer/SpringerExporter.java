package jacksonpradolima.csv2bib.exporter.springer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import jacksonpradolima.csv2bib.utils.Exporter;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

/**
 * Definition for SpringerLink References exporter
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public interface SpringerExporter  extends Exporter{
	
	/**
	 * Put message to Progress Bar
	 * @param pb
	 */
	default void message(ProgressBar pb) {
		pb.setExtraMessage("Getting Information");
	}
	
	/**
	 * Execute the extract references from Digital library
	 * 
	 * @param urls
	 * @return
	 */
	default List<String> execute(List<String> urls) {
        ProgressBar pb = new ProgressBar("[csv2bib]", urls.size(), 1000, System.out, ProgressBarStyle.UNICODE_BLOCK)
                .start()
                .maxHint(urls.size());

        List<String> refs = new ArrayList<>();

        URL uri;
        for (String strUrl : urls) {
            try {
                uri = new URL(strUrl);
                URLConnection ec = uri.openConnection();

                try (BufferedReader in = new BufferedReader(new java.io.InputStreamReader(ec.getInputStream(), ENCODING))) {

                    StringBuilder refBuilder = new StringBuilder();
                    String ref;
                    while ((ref = in.readLine()) != null) {
                        refBuilder.append(ref).append("\n");
                    }
                    LOGGER.debug(refBuilder.toString());
                    refs.add(refBuilder.toString());
                } catch (IOException ex) {
                    throw ex;
                }
                pb.step();
                message(pb);
            } catch (MalformedURLException ex) {
                LOGGER.error("Invalid url: " + strUrl, ex);
            } catch (IOException ex) {
                LOGGER.error("Error to get file at " + strUrl, ex);
            }
        }

        pb.stop();
        return refs;
    }
	
}
