package mx.meido.simpleshorturl.util.db;

import mx.meido.simpleshorturl.db.mongodb.MongoDbDAO;
import mx.meido.simpleshorturl.listener.SimpleShortUrlContextListener;

public class MongoDbDAOFactory {
	private static MongoDbDAO mongoDao;
	
	private MongoDbDAOFactory(){}
	
	public static MongoDbDAO getInstance(){
		if(mongoDao == null){
			mongoDao = new MongoDbDAO(SimpleShortUrlContextListener.getProps());
		}
		return mongoDao;
	}
}
