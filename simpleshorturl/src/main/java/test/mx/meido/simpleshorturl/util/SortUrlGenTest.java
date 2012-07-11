package test.mx.meido.simpleshorturl.util;

import junit.framework.Assert;
import mx.meido.simpleshorturl.util.ShortUrlGen;

import org.junit.Test;

public class SortUrlGenTest {
	@Test
	public void testGenShortUrl(){
		String result = ShortUrlGen.genShortUrl(70*70*70);
		System.out.println(result);
		Assert.assertEquals(result, "rOOO");
	}
}
