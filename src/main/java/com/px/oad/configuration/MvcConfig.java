package com.px.oad.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;


/**
 * 过滤器配置类
 */
@Configuration
public class MvcConfig {


    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        //自定义配置...
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");    // 自定义时间格式
        fastJsonConfig.setSerializerFeatures(
                //配置美观的输出格式
                SerializerFeature.PrettyFormat,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter(){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * 添加拦截器
             * @param registry
             */
            public void addInterceptors(InterceptorRegistry registry) {
                //registry.addInterceptor(uspaInterceptor);
            }

            /**
             *配置fastjson为默认转换器
             * @param converters
             */
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                //添加字符串转换 不带"
                converters.add(stringHttpMessageConverter());
                converters.add(fastJsonHttpMessageConverter());
            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.clear();
                converters.add(stringHttpMessageConverter());
                converters.add(fastJsonHttpMessageConverter());
            }


            /**
             * 设置默认的conten_type
             * @param configurer
             */
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
                //是否排除源头accept类型
                //configurer.ignoreAcceptHeader(true);
            }

            /**
             * 支持跨域
             * @param registry
             */
            public void addCorsMappings(CorsRegistry registry) {
                 /*registry.addMapping("/**")
                         .allowedOrigins("*")
                         .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                         .allowCredentials(true)
                         .allowedHeaders("*")
                         .maxAge(3600);*/
            }


            /**
             * 防止@EnableMvc把默认的静态资源路径覆盖了，手动设置的方式
             * @param registry
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // 解决静态资源无法访问
                registry.addResourceHandler("/**")
                        .addResourceLocations("classpath:/static/");
                // 解决swagger无法访问
                registry.addResourceHandler("/swagger-ui.html")
                        .addResourceLocations("classpath:/META-INF/resources/");
                // 解决swagger的js文件无法访问
                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }
}
