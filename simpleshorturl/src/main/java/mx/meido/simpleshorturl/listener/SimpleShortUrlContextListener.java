package mx.meido.simpleshorturl.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import mx.meido.simpleshorturl.util.db.MongoDbDAOFactory;
import mx.meido.tools.util.PropertyBundle;

/**
 * Application Lifecycle Listener implementation class SimpleShortUrlContextListener
 *
 */
public final class SimpleShortUrlContextListener implements ServletContextListener {
	
	private static final String CONFIG_FILE = "/WEB-INF/config.properties";
	
	private static PropertyBundle props;

    /**
     * Default constructor. 
     */
    public SimpleShortUrlContextListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	props = new PropertyBundle(arg0.getServletContext().getRealPath("/")+CONFIG_FILE);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	try {
			MongoDbDAOFactory.getInstance().close();
		} catch (Exception e) {
		}
    }
    
    public static PropertyBundle getProps() {
		return props;
	}
	
}
