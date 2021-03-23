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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jackson A. Prado Lima <jacksonpradolima at gmail.com>
 */
public class RunTest {

    private String file_input;
    private String digital_library;
    private String doi_index;
    
    public RunTest() {
        file_input = "src/test/resources/SpringerLink.csv";
        digital_library = "SpringerLink";
        doi_index = "5";
    }

    @Test
    public void testSomeMethod() {        
        String[] args = new String[]
        {
            "-fi="+file_input, 
            "-dl="+ digital_library, 
            "-doiIndex=" + doi_index
        };
        
        try {
            Main.main(args);
            assertTrue(true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

}
