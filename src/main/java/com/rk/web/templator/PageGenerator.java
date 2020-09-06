package com.rk.web.templator;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator pageGenerator;
    private TemplateEngine templateEngine;

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

    private PageGenerator() {
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("webapp/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateEngine.setTemplateResolver(templateResolver);
    }
}
