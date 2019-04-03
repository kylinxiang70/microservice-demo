package org.kylin.authcenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        /**
         * Access-Control-Allow-Credentials
         * 该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。
         * 设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。
         * 这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。
         *
         * ref: http://www.ruanyifeng.com/blog/2016/04/cors.html
         */
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                .allowCredentials(false).maxAge(3600)
        ;
    }
}
