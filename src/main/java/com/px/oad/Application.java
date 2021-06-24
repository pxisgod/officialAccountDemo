package com.px.oad;

/*
 工程启动方法
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication(exclude = {JestAutoConfiguration.class})
@ServletComponentScan
@EnableAspectJAutoProxy
@EntityScan(basePackages = {"com.px.oad"})
@PropertySource({"classpath:config.properties"})
@EnableCaching
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
	    try {
            SpringApplication.run(Application .class, args);
        }catch (Exception e){
	        e.printStackTrace();
        }
	}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application .class);
    }
}
