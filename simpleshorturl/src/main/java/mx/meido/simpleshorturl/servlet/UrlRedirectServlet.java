package mx.meido.simpleshorturl.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.meido.simpleshorturl.listener.SimpleShortUrlContextListener;
import mx.meido.simpleshorturl.util.db.MongoDbDAOFactory;

/**
 * Servlet implementation class UrlRedirectServlet
 */
public final class UrlRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 3671843701429536982L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UrlRedirectServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
//    	log("UrlServlet Initialized!");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		
//		log("p: "+path);
//		log("context path: "+request.getContextPath());
		
		String contextPath = request.getContextPath();
		if(contextPath.length() > 1 && path.indexOf(contextPath)==0){
			path = apartShortUrl(path);
		}else{
			path = path.substring(3);
		}
		
//		log("s: "+path);
		String fullUrl = MongoDbDAOFactory.getInstance().getFullUrl(path);
//		log("f: "+fullUrl);
		
		if(fullUrl != null){
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", fullUrl);
		}else{
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", SimpleShortUrlContextListener.getProps().get("admin-url"));
		}
	}
	
	/**
	 * important: uri must with context
	 * @param uri
	 * @return
	 */
	private String apartShortUrl(String uri){
		return uri.substring(uri.indexOf("/s", 1)+3);
	}

}
