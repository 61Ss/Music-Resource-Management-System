package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;

/**
 * 
 * 
 *
 * @author Levi
 */
@SpringBootConfiguration
public class AutoStartProjectInDefaultBrowser implements CommandLineRunner {

    @Value("${server.port}")
    private String port;

  
    @Value("${server.servlet.context-path}")
    private String context_path;

    /**
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        try {
            Runtime.getRuntime().exec("cmd /c start http://127.0.0.1:" + port + context_path + "/front/index.action");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}