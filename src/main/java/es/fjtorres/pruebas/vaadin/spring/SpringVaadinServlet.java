package es.fjtorres.pruebas.vaadin.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.server.Constants;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

public class SpringVaadinServlet extends VaadinServlet {

	private static final long serialVersionUID = 7507271542053154606L;

	private ApplicationContext applicationContext;
	
	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
	}

	@SuppressWarnings("serial")
	@Override
	protected VaadinServletService createServletService(
			DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {
		final VaadinServletService service = super
				.createServletService(deploymentConfiguration);

		 if (service.getDeploymentConfiguration().getApplicationOrSystemProperty(Constants.SERVLET_PARAMETER_UI_PROVIDER, "").isEmpty()) {
		      service.addSessionInitListener(new SessionInitListener() {
				
				@Override
				public void sessionInit(SessionInitEvent event) throws ServiceException {
					event.getSession().addUIProvider(new SpringUIProvider(applicationContext));
				}
			});
		 }
		
		return service;
	}
}
