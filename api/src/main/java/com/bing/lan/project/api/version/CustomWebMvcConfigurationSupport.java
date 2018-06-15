package com.bing.lan.project.api.version;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class CustomWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }

    //@Override
    //protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //    // 代码配置转换器
    //    //https://stackoverflow.com/questions/10650196/how-to-configure-mappingjacksonhttpmessageconverter-while-using-spring-annotatio
    //    converters.add(converter());
    //    addDefaultHttpMessageConverters(converters);
    //}
    //
    //@Bean
    //StringHttpMessageConverter converter() {
    //    return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    //}
}
