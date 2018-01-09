package jacksonpradolima.csv2bib;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jacksonpradolima.csv2bib.reader.CsvReader;
import jacksonpradolima.csv2bib.runner.RunByProperties;


public class RunPropertiesTest {
	
	private RunByProperties reader;
	private Properties prop;
	
	@Before
	public void init() throws IOException {
		reader = new RunByProperties();
		prop = reader.readProperties();
	}

	@Test
	public void propertieReaderTest() {
		Assert.assertNotNull(prop);
		Assert.assertNotNull(prop.getProperty("filePath"));
		Assert.assertNull(prop.getProperty("invalidFilePath"));
	}

	
	@Test
	public void csvReaderTest() throws IOException {
		String filePath = prop.getProperty("filePath");
		Integer index = CsvReader.getInstance(filePath, StringUtils.EMPTY).getDoiIndex();
		Assert.assertSame(5, index);
	}

}
