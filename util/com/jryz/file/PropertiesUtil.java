package com.jryz.file;

import java.io.*;
import java.util.Properties;


public class PropertiesUtil {
    private File propertiesFile;
	
	public PropertiesUtil(String fileName) {
		this.propertiesFile = new File(PropertiesUtil.class.getClassLoader().getResource(fileName).getPath());
	}
	
	public PropertiesUtil(File file) {
		this.propertiesFile = file;
	}

    public static void main(String[] args) {
        /*PropertiesUtil p = new PropertiesUtil("sycn_date.properties");
        String s = p.readProperty("time");
        System.out.println(s);
        p.writeProperty("asdfas", "123");
        p.writeProperty("time", "22");
        s = p.readProperty("time");*/
    }

	public String readProperty(String key) {
		String value = "";
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesFile);
			Properties p = new Properties();
			p.load(is);
			value = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			    if (is !=null)
				    is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public Properties getProperties() {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesFile);
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
                if (is !=null)
				    is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

    public void writeProperty(String key, String value) {
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			is = new FileInputStream(propertiesFile);
			p.load(is);
			os = new FileOutputStream(propertiesFile);
			p.setProperty(key, value);
			p.store(os, value);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
                if (null != is) {
                    is.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	

}
