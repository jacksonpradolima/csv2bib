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

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import jacksonpradolima.csv2bib.library.Library;
import jacksonpradolima.csv2bib.library.SpringerLink;

/**
 * Digital Libraries with the respective urls to exporter informations
 * 
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 * 
 * @author Fernando S. Godóy <fernandogodoy18@gmail.com>
 */
public enum DigitalLibraries {

    SPRINGERLINK("SpringerLink") {
		@Override
		public Library getInstance() {
			return new SpringerLink();
		}
	};
	
    public static DigitalLibraries getDigitalLibrary(String value) {
    	return Arrays.asList(values())
    			.stream()
    			.filter(item -> StringUtils.equalsIgnoreCase(item.value, value))
    			.findFirst()
    			.get();
    }

    private final String value;
    
    public abstract Library getInstance();
    
    DigitalLibraries(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
