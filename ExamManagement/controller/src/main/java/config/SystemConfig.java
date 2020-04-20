package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*继承的该父类间接的实现了WebApplicationInitializer*/
public class SystemConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*如果需要多容器的配置子容器在getServletConfigClasses方法里*/
    /*mapping则是映射*/
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
