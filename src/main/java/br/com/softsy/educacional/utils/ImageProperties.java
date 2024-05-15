package br.com.softsy.educacional.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ImageProperties {

	public static String getImagePath() {
		return getPropertyValue("image.directory");
	}

	private static String getPropertyValue(String property) {

		try (InputStream input = ImageProperties.class.getClassLoader().getResourceAsStream("application-dev.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			return (prop.getProperty(property));

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}
	
}
