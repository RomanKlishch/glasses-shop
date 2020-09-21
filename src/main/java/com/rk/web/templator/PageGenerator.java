package com.rk.web.templator;

import com.rk.util.PropertyReader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator pageGenerator;
    private PropertyReader propertyReader = new PropertyReader("properties/application.properties");
    private TemplateEngine templateEngine;

    private boolean cache = Boolean.parseBoolean(propertyReader.getProperty("resolver.cacheable"));
    private String prefix = propertyReader.getProperty("resolver.prefix");
    private String suffix = propertyReader.getProperty("resolver.suffix");

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
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

    private PageGenerator() {
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(cache);
        templateEngine.setTemplateResolver(templateResolver);
    }
}
