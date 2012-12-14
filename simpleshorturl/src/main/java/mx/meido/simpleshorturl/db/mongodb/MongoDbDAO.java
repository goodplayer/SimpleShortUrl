package mx.meido.simpleshorturl.db.mongodb;

import java.net.UnknownHostException;

import mx.meido.simpleshorturl.util.ShortUrlGen;
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
 * 
 * ================================================
 * collection: short_url
 * 
 * short	String
 * fullurl	String
 * create_time  long
 * modified_time  long
 * totalCnt  long
 * urlpwd  String----为空则为已经归入一个管理帐户, 非空则为当前生成管理密码状态为浮动
 * accountId  long----空为浮动状态, 非空为归入帐户
 * 
 * db.short_url.ensureIndex({short: 1});
 * db.short_url.ensureIndex({fullurl: 1});
 * ================================================
 * collection: user
 * 
 * accountId  long
 * email  String
 * password  String
 * 
 * 
 * ================================================
 * collection: system
 * 
 * key  String
 * value  ?
 * 
 * key     value
 * urlId - idno(long)
 * 
 */
public class MongoDbDAO {
	public static final String MONGODB_HOST = "mongodb_host";
	public static final String MONGODB_PORT = "mongodb_port";
	public static final String MONGODB_DBNAME = "mongodb_dbname";
	private String db_host;
	private int db_port;
	private String db_name;
	private Mongo mongo;
	public static final String STORE_DbCollectionName = "short_url";
	public static final String STORE_SystemCollectionName = "system";

	//
	private static final BasicDBObject totalCntIncObj = new BasicDBObject(
			"$inc", new BasicDBObject("totalCnt", 1l));
	private static final BasicDBObject urlId = new BasicDBObject("key", "urlId");
	private static final BasicDBObject urlIdIncObj = new BasicDBObject("$inc",
			new BasicDBObject("value", 1l));

	public MongoDbDAO(PropertyBundle pb) {
		this.db_host = pb.get(MONGODB_HOST);
		this.db_name = pb.get(MONGODB_DBNAME);
		this.db_port = Integer.valueOf(pb.get(MONGODB_PORT));
		try {
			mongo = new Mongo(db_host, db_port);
			MongoOptions mongoOption = mongo.getMongoOptions();
			mongoOption.connectionsPerHost = 100;
			
			init();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭Dao<br />
	 * DO NOT DO THIS IN YOUR CODE , IT MAY CAUSE FACTORY ERROR
	 */
	public void close(){
		if(mongo != null)
			mongo.close();
	}
	
	//初始化某些关键点
	private void init(){
		DB db = mongo.getDB(db_name);
		DBCollection coll = db.getCollection(MongoDbDAO.STORE_SystemCollectionName);
		DBObject o = coll.findOne(MongoDbDAO.urlId);
		if(o == null){
			BasicDBObject insert = new BasicDBObject();
			insert.put("key", "urlId");
			insert.put("value", ShortUrlGen.INIT_VALUE);
			coll.insert(insert);
		}
	}

	// 获取FullUrl
	public String getFullUrl(String shortUrl) {
		DB db = mongo.getDB(db_name);
		String url = null;
		try {
			db.requestStart();
			DBCollection coll = db
					.getCollection(MongoDbDAO.STORE_DbCollectionName);
			BasicDBObject query = new BasicDBObject();
			query.put("short", shortUrl);
			DBObject o = coll.findOne(query);
			url = o == null ? null : o.get("fullurl").toString();
			if (o != null) {
				BasicDBObject condition = new BasicDBObject();
				condition.put("short", shortUrl);

				coll.update(condition, totalCntIncObj, true, true);
			}
			db.requestDone();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return url;
	}

	// 获取ShortUrl
	public String getShortUrl(String fullUrl) {
		DB db = mongo.getDB(db_name);
		String url = null;
		try {
			DBCollection coll = db
					.getCollection(MongoDbDAO.STORE_DbCollectionName);
			BasicDBObject query = new BasicDBObject();
			query.put("fullurl", fullUrl);
			DBObject o = coll.findOne(query);
			url = o == null ? null : o.get("short").toString();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return url;
	}

	// 插入新ShortUrl
	public void insertUrl(String fullUrl, String shortUrl, String urlpwd) {
		DB db = mongo.getDB(db_name);
		DBCollection coll = db.getCollection(MongoDbDAO.STORE_DbCollectionName);
		BasicDBObject insert = new BasicDBObject();
		insert.put("short", shortUrl);
		insert.put("fullurl", fullUrl);
		insert.put("create_time", System.currentTimeMillis());
		insert.put("totalCnt", 0l);
		insert.put("urlpwd", urlpwd);
		coll.insert(insert);
	}

	// 获取shortUrlId
	public long getCurShortUrlId() {
		DB db = mongo.getDB(db_name);
		DBCollection coll = db
				.getCollection(MongoDbDAO.STORE_SystemCollectionName);
		DBObject o = coll.findOne(MongoDbDAO.urlId);
		long urlId = 0;
		try {
			urlId = o == null ? ShortUrlGen.INIT_VALUE : (long) o.get("value");
		} catch (Exception e) {
			e.printStackTrace();
			urlId = ShortUrlGen.INIT_VALUE;
		}
		return urlId;
	}

	// shortUrlId自增
	public void incUrlId() {
		DB db = mongo.getDB(db_name);
		DBCollection coll = db
				.getCollection(MongoDbDAO.STORE_SystemCollectionName);
		coll.update(MongoDbDAO.urlId, MongoDbDAO.urlIdIncObj, true, true);
	}
}
