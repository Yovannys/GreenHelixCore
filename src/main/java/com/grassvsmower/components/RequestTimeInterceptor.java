package com.grassvsmower.components;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.grassvsmower.model.UserModel;
import com.grassvsmower.repository.LogRepository;
import com.grassvsmower.services.UserService;


/*
 * Por cada peticion que se haga entre por esta clase
 * */
@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	@Qualifier("logRepository")
	LogRepository logRepository;
	
	@Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
	
	private static final Log LOGGER =  LogFactory.getLog(RequestTimeInterceptor.class);

	//Se ejecuta antes de entrar al controlador
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

	
	//Se ejecuta antes de entrar de escupir la vista en el navegador 
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		long startTime = (long) request.getAttribute("startTime");
		String url = request.getRequestURL().toString();
		
				
		UserModel usrToken = userService.getUsernameByToken();
		
		if (usrToken!=null) {
			
			com.grassvsmower.entities.Log log = new com.grassvsmower.entities.Log();
			log.setDate(new Date());
			log.setDetails("Action : ");
			log.setUrl(url);
			log.setUsername(usrToken.getUsername());
			logRepository.save( log);
			
		}else {
			com.grassvsmower.entities.Log log = new com.grassvsmower.entities.Log();
			log.setDate(new Date());
			log.setDetails("Action");
			log.setUrl(url);
			log.setUsername("Unknow");
			logRepository.save( log);
		}
	
		
		LOGGER.info("--Request URL : '"+url+ "' -- TOTAL TIME: '"+(System.currentTimeMillis()-startTime)+"'ms");
	}
	 
}
