package mx.meido.simpleshorturl.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

//@UrlBinding("/s/")
public class DemoAction implements ActionBean {
	private ActionBeanContext context;
	public ActionBeanContext getContext() {
		return context;
	}
	public void setContext(ActionBeanContext context) {
		this.context = context;
	}
	
	@DefaultHandler
	public Resolution show(){
		HttpServletResponse resp = context.getResponse();
		resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		resp.setHeader("Location", "http://meido.mx");
		return null;
	}
	
	@HandlesEvent("xiaoxin")
	public Resolution show1(){
		HttpServletResponse resp = context.getResponse();
		resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		resp.setHeader("Location", "http://ixin.me");
		return null;
	}
}
