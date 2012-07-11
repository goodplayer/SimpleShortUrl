package mx.meido.simpleshorturl.util;

import mx.meido.simpleshorturl.db.mongodb.MongoDbDAO;

public class ShortUrlGen implements IShortUrlChar {
	//4位起
	public static final long INIT_VALUE = AVAILABLE_CHAR.length*AVAILABLE_CHAR.length*AVAILABLE_CHAR.length;
	private static final int TOTAL_CHAR = AVAILABLE_CHAR.length;
	
	public static long curUrlId;
	
	private MongoDbDAO db;
	
	private static Object lock = new Object();
	
	public ShortUrlGen(MongoDbDAO db) {
		this.db = db;
		curUrlId = db.getCurShortUrlId();
	}
	
	public String genAndSaveShortUrl(){
		String id = ShortUrlGen.genShortUrl(curUrlId);
		synchronized (lock) {
			curUrlId++;
			db.incUrlId();
		}
		return id;
	}
	
	public static String genShortUrl(long id){
		long s = id;
		int y;
		StringBuilder urlBuilder = new StringBuilder();
		do {
			y = (int) (s % TOTAL_CHAR);
			s = s / TOTAL_CHAR;
			urlBuilder.insert(0, IShortUrlChar.AVAILABLE_CHAR[y]);
		}while(s != 0);
		return urlBuilder.toString();
	}
}
