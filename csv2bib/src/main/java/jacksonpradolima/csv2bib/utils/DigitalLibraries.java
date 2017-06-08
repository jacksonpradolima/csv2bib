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

import java.util.Objects;

/**
 * Digital Libraries with the respective urls to get bibtex informations
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 */
public enum DigitalLibraries {

    DOI("DOI", "http://dx.doi.org/%s"),
    SPRINGER_LINK("SpringerLink", "https://citation-needed.springer.com/v2/references/%s?format=bibtex&flavour=citation");

    /**
     *
     * @param value
     * @return
     */
    public static DigitalLibraries getEnum(String value) {
        for (DigitalLibraries v : values()) {
            if (Objects.equals(v.getValue(), value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    private final String value;
    private final String url;

    DigitalLibraries(String value, String url) {
        this.value = value;
        this.url = url;
    }

    /**
     * Digital Library Name
     *
     * @return name
     */
    public String getValue() {
        return value;
    }

    /**
     * Digital Library Url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

}
