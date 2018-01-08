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
package jacksonpradolima.csv2bib.exporter.springer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import me.tongfei.progressbar.ProgressBar;

/**
 * Wrapper to get BibTex informations from URLs
 * 
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 */
public class SpringerBibTex implements SpringerExporter {

	private String outputFile;
	
	private List<String> urls;

	public SpringerBibTex(String outputFolder, List<String> urls) {
		this.urls = urls;
		this.outputFile = FilenameUtils.getBaseName(Paths.get(outputFolder).toString()) + ".bib";
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
		pb.setExtraMessage("Getting BibText informations...");
	}

}
