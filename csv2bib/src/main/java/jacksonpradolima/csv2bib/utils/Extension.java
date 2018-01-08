package jacksonpradolima.csv2bib.utils;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * Extension of References file
 * 
 * @author Fernando Godóy <fernandogodoy18@gmail.com>
 *
 */
public enum Extension {

	BIB("bib", "bibtex"), RIS("ris", "refman");

	private String description;
	private String format;

	Extension(String extension, String format) {
		this.description = extension;
		this.format = format;
	}
	
	public String getFormat() {
		return format;
	}
	
	public static Extension getByDescription(String value) {
		return Arrays.asList(values()).stream()
							.filter(item -> StringUtils.equalsIgnoreCase(item.description, value))
							.findFirst()
							.get();
	}

	@Override
	public String toString() {
		return description;
	}
}
