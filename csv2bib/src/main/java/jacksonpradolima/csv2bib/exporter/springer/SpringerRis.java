package jacksonpradolima.csv2bib.exporter.springer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import jacksonpradolima.csv2bib.utils.Extension;
import me.tongfei.progressbar.ProgressBar;

/**
 * Wrapper to get RIS informations from URLs
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public class SpringerRis implements SpringerExporter {

	private String outputFile;
	private List<String> urls;

	public SpringerRis(String outputFile, List<String> urls) {
		this.urls = urls;
		this.outputFile = FilenameUtils.getBaseName(Paths.get(outputFile).toString());
	}

	@Override
	public Path getOutputFile() {
		return Paths.get(outputFile);
	}
	
	@Override
	public List<String> getUrls() {
		return urls;
	}

	@Override
	public void message(ProgressBar pb) {
		pb.setExtraMessage("Getting RIS informations...");
	}

	@Override
	public Extension getExtension() {
		return Extension.RIS;
	}

}
