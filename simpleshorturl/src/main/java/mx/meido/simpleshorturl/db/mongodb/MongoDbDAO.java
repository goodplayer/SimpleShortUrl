package mx.meido.simpleshorturl.db.mongodb;

import java.net.UnknownHostException;

import mx.meido.tools.util.PropertyBundle;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

/*
 * db: shorturl
 * collection: short_url
 * 
 * short	String
 * fullurl	String
 * create_time  long
 * modified_time  long
 * password  String
 * account  long
 * 
 * 
 * db.short_url.ensureIndex({short: 1});
 * db.short_url.ensureIndex({fullurl: 1});
 */
public class MongoDbDAO {
	public static final String MONGODB_HOST = "mongodb_host";
	public static final String MONGODB_PORT = "mongodb_port";
	public static final String MONGODB_DBNAME = "mongodb_dbname";
	private String db_host;
	private int db_port;
	private String db_name;
	private Mongo mongo;
	public static final String DbCollectionName = "short_url";
	
	public MongoDbDAO(PropertyBundle pb){
		this.db_host = pb.get(MONGODB_HOST);
		this.db_name = pb.get(MONGODB_DBNAME);
		this.db_port = Integer.valueOf(pb.get(MONGODB_PORT));
		try {
			mongo = new Mongo(db_host, db_port);
			MongoOptions mongoOption = mongo.getMongoOptions();
			mongoOption.connectionsPerHost = 100;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}
	
	public String getFullUrl(String shortUrl){
		DB db = mongo.getDB(db_name);
		String url = null;
		try {
			db.requestStart();
			DBCollection coll = db.getCollection(MongoDbDAO.DbCollectionName);
			BasicDBObject query = new BasicDBObject();
			query.put("short", shortUrl);
			DBObject o = coll.findOne(query);
			url = o == null?null:o.get("fullurl").toString();
			db.requestDone();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return url;
	}
}
