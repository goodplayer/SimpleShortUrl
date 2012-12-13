package mx.meido.simpleshorturl.action;

import mx.meido.simpleshorturl.listener.SimpleShortUrlContextListener;
import mx.meido.simpleshorturl.servlet.UrlRedirectServlet;
import mx.meido.simpleshorturl.util.db.MongoDbDAOFactory;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

//@UrlBinding("/s/")
public class ShortUrlAction implements ActionBean {
	private static final String BLANK_PAGE = "/WEB-INF/Blank.jsp";
	private static final String SUCCESS_PAGE = "/WEB-INF/SubmitResult.jsp";
	private static final String ERROR_PAGE = "/WEB-INF/ERROR.jsp";
	
	private ActionBeanContext context;
	public ActionBeanContext getContext() {
		return context;
	}
	public void setContext(ActionBeanContext context) {
		this.context = context;
	}
	
	private String fullUrl;
	public String getFullUrl() {
		return fullUrl;
	}
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
	private String shortUrl;
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@DefaultHandler
	public Resolution insert(){
		try {
			if(this.fullUrl == null || this.fullUrl.length()==0)
				return new ForwardResolution(BLANK_PAGE);
			
			String url = new String(this.fullUrl);
			if(!this.fullUrl.startsWith("http://") && !this.fullUrl.startsWith("https://"))
				url = "http://"+url;
			
			String urlInDb = MongoDbDAOFactory.getInstance().getShortUrl(url);
			//还没有
			if(urlInDb == null){
				urlInDb = UrlRedirectServlet.shortUrlGen.genAndSaveShortUrl();
				MongoDbDAOFactory.getInstance().insertUrl(url, urlInDb, "111111");
				msg = "成功缩短url，短链接管理名为：<b>"+urlInDb+"</b> 密码为111111，短链接如下：";
				shortUrl = SimpleShortUrlContextListener.getProps().get("main-url")+urlInDb;
				return new ForwardResolution(SUCCESS_PAGE);
			}else{//已经存在
				msg = "该Url已经存在，短链接如下：";
				shortUrl = SimpleShortUrlContextListener.getProps().get("main-url")+urlInDb;
				return new ForwardResolution(SUCCESS_PAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ForwardResolution(ERROR_PAGE);
		}
	}
	
}
