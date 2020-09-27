package com.rk.web.templator;

import com.rk.util.PropertyReader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final PropertyReader propertyReader = new PropertyReader("properties/application.properties");
    private static PageGenerator pageGenerator;
    private TemplateEngine templateEngine = new TemplateEngine();
    private boolean ifConfigured;

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public synchronized void configureTemplate(ServletContext context) {
        if (ifConfigured) {
            return;
        }

        templateEngine.setTemplateResolver(configureResolver(context));
        ifConfigured = true;
    }

    public void process(String template, Map<String, Object> paramsMap, Writer writer) {
        Context context = new Context();
        context.setVariables(paramsMap);
        templateEngine.process(template, context, writer);
    }

    public void process(String template, Writer writer) {
        Context context = new Context();
        templateEngine.process(template, context, writer);
    }

    private ITemplateResolver configureResolver(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(propertyReader.getProperty("resolver.prefix"));
        templateResolver.setSuffix(propertyReader.getProperty("resolver.suffix"));
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(Boolean.parseBoolean(propertyReader.getProperty("resolver.cacheable")));
        return templateResolver;
    }
}
