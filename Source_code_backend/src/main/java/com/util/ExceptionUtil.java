package com.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author
 * @description 
 */
@Component
public class ExceptionUtil extends SimpleMappingExceptionResolver {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    @Override
    public ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                           Object o, Exception ex) {

        PrintWriter writer = null;
        try {

            httpServletResponse.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
            writer = httpServletResponse.getWriter();

            ex.printStackTrace();
            ResultUtil result = ResultUtil.error().message("Go wrong").code(HttpStatus.SC_INTERNAL_SERVER_ERROR);


            writer.write(JSONObject.toJSONString(result));
            writer.flush();
        } catch (IOException e) {

            logger.error("SpringGlobalExceptionHandler exception  ", e);

        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
        return null;
    }


}
