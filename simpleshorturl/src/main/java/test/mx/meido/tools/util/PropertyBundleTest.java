package test.mx.meido.tools.util;

import mx.meido.tools.util.PropertyBundle;

import org.junit.Assert;
import org.junit.Test;

public class PropertyBundleTest {
	@Test
	public void testGetKeys(){
		PropertyBundle pb = new PropertyBundle("D:/!SoftRepo2/!GitRepo/SimpleShortUrl/simpleshorturl/src/main/webapp/config.properties");
		String[] keys = pb.getKeys();
		for(String key : keys){
			System.out.println(key);
		}
		Assert.assertNotSame(keys.length, 0);
	}
}
