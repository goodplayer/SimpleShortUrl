package mx.meido.simpleshorturl.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.meido.simpleshorturl.db.mongodb.MongoDbDAO;
import mx.meido.simpleshorturl.util.ShortUrlGen;
import mx.meido.tools.util.PropertyBundle;

/**
 * Servlet implementation class UrlRedirectServlet
 */
public final class UrlRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 3671843701429536982L;
	
	public static PropertyBundle props;
	private static final String CONFIG_FILE = "/WEB-INF/config.properties";
	
	public static MongoDbDAO dbdao;
	
	public static ShortUrlGen shortUrlGen;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UrlRedirectServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	props = new PropertyBundle(config.getServletContext().getRealPath("/")+CONFIG_FILE);
    	dbdao = new MongoDbDAO(props);
    	log("UrlServlet Initialized!");
    	UrlRedirectServlet.shortUrlGen = new ShortUrlGen(dbdao);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		path = apartShortUrl(path);
		log("s: "+path);
		String fullUrl = dbdao.getFullUrl(path);
		log("f: "+fullUrl);
		
		if(fullUrl != null){
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", fullUrl);
		}else{
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", props.get("admin-url"));
		}
	}
	
	private String apartShortUrl(String uri){
		return uri.substring(uri.indexOf("/s", 1)+3);
	}

}
