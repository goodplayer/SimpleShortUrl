package mx.meido.simpleshorturl.util;

import mx.meido.simpleshorturl.util.db.MongoDbDAOFactory;

public class ShortUrlGenFactory {
	private static ShortUrlGen sug;
	
	private ShortUrlGenFactory(){}
	
	public static ShortUrlGen getInstance(){
		if(sug == null){
			sug = new ShortUrlGen(MongoDbDAOFactory.getInstance());
		}
		return sug;
	}
}
