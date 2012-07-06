package mx.meido.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertyBundle {
	private Properties prop;
	private File pfile;
	
	public PropertyBundle(File file) {
		prop = new Properties();
		pfile = file;
		this.reload();
	}
	public PropertyBundle(String file){
		prop = new Properties();
		pfile = new File(file);
		this.reload();
	}
	
	public String get(String key){
		return (String) prop.get(key);
	}
	
	public String[] getKeys(){
		Set<String> keys = prop.stringPropertyNames();
		String[] nkeys = new String[keys.size()];
		return keys.toArray(nkeys);
	}
	
	public void reload(String file){
		reload(new File(file));
	}
	public void reload(File file){
		try {
			InputStream is = new FileInputStream(file);
			prop.clear();
			prop.load(is);
			pfile = file;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void reload(){
		this.reload(pfile);
	}
	
}
