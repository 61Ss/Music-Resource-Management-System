package com.config;

import com.interceptor.ManagerLoginInterceptor;
import com.interceptor.FrontLoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 
     *
     * @return
     */
 /*   @Bean
    public HandlerInterceptor loginInterceptor(){
        return new FrontLoginInterceptor();
    }
*/

    @Value("${server.servlet.context-path}")
    private String context_path;



    @Bean
    ManagerLoginInterceptor managerLoginInterceptor() {
        return new ManagerLoginInterceptor();
    }



    @Bean
    FrontLoginInterceptor frontLoginInterceptor() {
        return new FrontLoginInterceptor();
    }


    private static final String[] EXCLUDE_PATH_PATTERNS = {
            "/upload_files/**"
    };



    /**
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(managerLoginInterceptor())
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS)//
                .addPathPatterns("/manager/**")//未

        ;


        //前台访问拦截器  普通 用户
        registry.addInterceptor(frontLoginInterceptor())
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS)//
                .addPathPatterns("/front/**")//

        ;


    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true)   
                .setUseTrailingSlashMatch(true);    
    }
}
