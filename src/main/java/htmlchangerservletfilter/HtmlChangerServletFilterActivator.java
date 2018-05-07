package htmlchangerservletfilter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HtmlChangerServletFilterActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Servlet filter ON!!");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Servlet filter OFF!");
	}

}