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

import java.io.IOException;
import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jacksonpradolima.csv2bib.runner.Run;
import jacksonpradolima.csv2bib.runner.RunByArgs;
import jacksonpradolima.csv2bib.runner.RunByProperties;

/**
 * Class to parse csv file to bib file using DOI as key to get bibtex
 * informations.
 *
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 * 
 * @author Fernando S. Godóy <fernandogodot18@gmail.com>
 */
public class Main {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, InterruptedException {

		try {
			Run run = args.length > 0 ? new RunByArgs(args) : new RunByProperties();
			run.run();

		} catch (UncheckedIOException e) {
			logger.error("Error when tried to read the csv file.", e);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

}
