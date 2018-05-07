package htmlchangerservletfilter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;


@Component(
 immediate = true, 
 property = {
 "servlet-context-name=", 
 "servlet-filter-name=Custom Filter",
 "url-pattern=/web/*" 
 }, 
 service = Filter.class
 )

public class CustomFilter extends BaseFilter {

	private static final Log _log = LogFactoryUtil.getLog(CustomFilter.class);
	@Override
	protected Log getLog() { return _log; }

	private ServletContext context;
	
	@Override
	public void init(FilterConfig fConfig) {
		this.context = fConfig.getServletContext();
		this.context.log("RequestLoggingFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException{
		  GenericResponseWrapper wrapper = new GenericResponseWrapper((HttpServletResponse) response); 
		  chain.doFilter(request,wrapper);
		  if(wrapper.getContentType().contains("text/html")){
			  OutputStream out = response.getOutputStream();
			  _log.info("replacing content");
			  //out.write(new String("<HR>PRE<HR>").getBytes());
			  //out.write(wrapper.getString().replaceAll("body", "BODY").getBytes());
			  out.write(wrapper.getString().replaceAll(
					  "<meta content=\".*?\".*?http-equiv=\".*?\" \\/>",
					  "<meta content=\"ie-8\" http-equiv=\"x-ua-compatible\" />"
					  ).getBytes());
			  //out.write(new String("<HR>POST<HR>").getBytes());
			  out.close(); 
		  }else{
			  super.doFilter(request, response, chain);
		  }
	}

}