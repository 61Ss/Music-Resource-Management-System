package com.controller.front;

import com.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**

 */
@Controller
public class BaseFrontAction {


    protected final Log log = LogFactory.getLog(getClass());

 
    public Map<String, String[]> getParameters() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest().getParameterMap();
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    public HttpSession getSession() {
        HttpSession session = null;
        try {
            session = this.getRequest().getSession();
        } catch (Exception e) {
        }
        return session;
    }


}
