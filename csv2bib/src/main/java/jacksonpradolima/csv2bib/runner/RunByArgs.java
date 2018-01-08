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
package jacksonpradolima.csv2bib.runner;

import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.JCommander;

import jacksonpradolima.csv2bib.Commands;
import jacksonpradolima.csv2bib.executor.CsvExecutor;
import jacksonpradolima.csv2bib.executor.Executor;
import jacksonpradolima.csv2bib.reader.CsvReader;

/**
 * Execute parse bib using args
 *
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 * 
 * @author Fernando S. Godóy <fernandogodot18@gmail.com>
 */
public class RunByArgs implements Run {

	private Commands command = new Commands();

	public RunByArgs(String[] args) {
		JCommander jCommander = new JCommander(command, args);
		if (command.help) {
			jCommander.usage();
			return;
		}
	}

	@Override
	public void run() throws Exception {

		Executor executor = new CsvExecutor()
				.withReader(CsvReader.getInstance(command.fileIn, command.doiIndex))
				.withFileExtension(StringUtils.defaultIfBlank(command.extension, "bib"))
				.withDigitalLib(command.digitalLibrary);
		
		executor.run();
	}

}
