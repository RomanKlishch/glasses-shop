package com.rk;

import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.util.PropertiesReader;
import com.rk.web.servlet.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.JarFileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.Arrays;

@Slf4j
public class Starter {
    private static final PropertiesReader PROPERTIES_READER = new PropertiesReader("properties/configDB.properties", "properties/config.properties");
    private static final int PORT = Integer.parseInt(PROPERTIES_READER.getProperties("PORT"));

    @SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(PROPERTIES_READER.getProperties("JDBC_DATABASE_URL"));
        dataSource.setUser(PROPERTIES_READER.getProperties("JDBC_DATABASE_USERNAME"));
        dataSource.setPassword(PROPERTIES_READER.getProperties("JDBC_DATABASE_PASSWORD"));

        JdbcGlassesDao jdbcGateDao = new JdbcGlassesDao(dataSource);
        DefaultGlassesService glassesService = new DefaultGlassesService(jdbcGateDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        HomeServlet homeServlet = new HomeServlet(glassesService);
        GlassesServlet glassesServlet = new GlassesServlet(glassesService);
        CatalogGlassesServlet catalogGlassesServlet = new CatalogGlassesServlet(glassesService);
        ArticleServlet articleServlet = new ArticleServlet();
        ContactServlet contactServlet = new ContactServlet();

        context.addServlet(new ServletHolder(homeServlet), "");
        context.addServlet(new ServletHolder(glassesServlet), "/glasses/*");
        context.addServlet(new ServletHolder(articleServlet), "/articles");
        context.addServlet(new ServletHolder(contactServlet), "/contacts");
        context.addServlet(new ServletHolder(catalogGlassesServlet), "/catalog");

        Resource resource = JarFileResource.newClassPathResource(PROPERTIES_READER.getProperties("RESOURCE_PATH"));
        context.setBaseResource(resource);
        context.addServlet(DefaultServlet.class, "/");

        Server server = new Server(PORT);
        server.setHandler(context);
        server.start();
        log.info("Server START - {}", Arrays.toString(server.getConnectors()));

    }
}
