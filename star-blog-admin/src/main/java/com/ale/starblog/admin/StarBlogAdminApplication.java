package com.ale.starblog.admin;

import com.ale.starblog.framework.common.domain.entity.EntityScan;
import com.ale.starblog.framework.common.enumeration.EnumScan;
import com.ale.starblog.framework.security.config.servlet.EnableVenusAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 *
 * @author Ale
 */
@EnumScan
@EntityScan
@EnableVenusAuthentication
@SpringBootApplication
public class StarBlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarBlogAdminApplication.class, args);
    }

}
