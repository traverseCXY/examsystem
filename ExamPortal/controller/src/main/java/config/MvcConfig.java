package config;

import com.ssm.interceptor.MyInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan({"com.ssm.controller","com.ssm.interceptor"})
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /*视图资源解析器*/
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        viewResolver.setPrefix("/WEB-INF/views/");
        return viewResolver;
    }
    /*格式转换器*/
    /*@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }
*/
    /*配置拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration=registry.addInterceptor(new MyInterceptor());
        interceptorRegistration.addPathPatterns("/**/**");
        interceptorRegistration.excludePathPatterns("/**/user/user-login");
        interceptorRegistration.excludePathPatterns("/**/login");
        interceptorRegistration.excludePathPatterns("/**/user/user-register");
        interceptorRegistration.excludePathPatterns("/**/user/add-user");
    }
    /*视静态文件处理器*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration=registry.addResourceHandler("/static/**");
        resourceHandlerRegistration.addResourceLocations("classpath:/static/");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(10000000);
        //commonsMultipartResolver.setUploadTempDir("classpath:/temporary/");
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return commonsMultipartResolver;
    }

    /**
     * 这里添加的转换器会导致不会添加默认转换器。
     * 如果想在保留默认转换器的情况下添加消息转换器，可以重写extendMessageConverters方法
     *
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        SimpleDateFormat adf=new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(adf);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converters.add(converter);
    }*/
}
