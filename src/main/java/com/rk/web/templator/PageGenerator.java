package com.rk.web.templator;

import com.rk.util.PropertyReader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static PropertyReader propertyReader = new PropertyReader("properties/application.properties");
    private static TemplateEngine templateEngine= new TemplateEngine();;
    private static boolean ifConfigured;

    public static void configureTemplate(ServletContext context) {
        if (ifConfigured){
            return;
        }
        synchronized (PageGenerator.class){
            templateEngine.setTemplateResolver(configureResolver(context));
            ifConfigured = true;
        }
    }

    public static void process(String template, Map<String, Object> paramsMap, Writer writer) {
        Context context = new Context();
        context.setVariables(paramsMap);
        templateEngine.process(template, context, writer);
    }

    public static void process(String template, Writer writer) {
        Context context = new Context();
        templateEngine.process(template, context, writer);
    }

    private static ITemplateResolver configureResolver(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(propertyReader.getProperty("resolver.prefix"));
        templateResolver.setSuffix(propertyReader.getProperty("resolver.suffix"));
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(Boolean.parseBoolean(propertyReader.getProperty("resolver.cacheable")));
        return templateResolver;
    }
}
