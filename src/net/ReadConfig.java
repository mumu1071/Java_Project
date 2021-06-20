package net;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 读取配置文件：properties
 * @author Administrator
 */
public class ReadConfig {

	private static final String CONFIG_PATH = "conf/parameter";
	private static final String CHARSET_NAME = "UTF-8";
	private static String[] fileNames = new String[] {
		"chairmanzheng","card","ftp","message","security","tenpay","alipay","notnull","history","activity", "bwton", "apppayParams", "water_travel"
	};
	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
	static {
		InputStream inStream = null;
		InputStreamReader reader = null;
		try {
			if (fileNames != null && fileNames.length > 0) {
				for (int i = 0; i < fileNames.length; i++) {
					Properties properties = new Properties();
					String filePath = CONFIG_PATH + File.separator + fileNames[i] + ".properties";
					inStream = ReadConfig.class.getClassLoader().getResourceAsStream(filePath);
					if (inStream != null) {
						reader = new InputStreamReader(inStream, CHARSET_NAME);
						properties.load(reader);
						propertiesMap.put(fileNames[i], properties);
					} else {
						
					}
				}
			}
		} catch (Exception e) {
			
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}