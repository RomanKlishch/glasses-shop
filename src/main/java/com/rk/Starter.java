package com.rk;

import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.util.PropertyReader;
import com.rk.web.handler.DefaultErrorHandler;
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
   /* private static final PropertyReader PROPERTIES_READER = new PropertyReader("properties/application.properties","properties/sqlQueries.properties");
    private static final int PORT = Integer.parseInt(PROPERTIES_READER.getProperty("PORT"));

    @SneakyThrows
    public static void main(String[] args) {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(PROPERTIES_READER.getProperty("JDBC_DATABASE_URL"));
        dataSource.setUser(PROPERTIES_READER.getProperty("JDBC_DATABASE_USERNAME"));
        dataSource.setPassword(PROPERTIES_READER.getProperty("JDBC_DATABASE_PASSWORD"));

        JdbcGlassesDao jdbcGateDao = new JdbcGlassesDao(dataSource,PROPERTIES_READER);
        DefaultGlassesService glassesService = new DefaultGlassesService(jdbcGateDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        HomeServlet homeServlet = new HomeServlet(glassesService);
        GlassesServlet glassesServlet = new GlassesServlet(glassesService);
        CatalogGlassesServlet catalogGlassesServlet = new CatalogGlassesServlet(glassesService);
        CategoryServlet categoryServlet = new CategoryServlet(glassesService);
        ArticleServlet articleServlet = new ArticleServlet();
        ContactServlet contactServlet = new ContactServlet();
        AddGlassesServlet addGlassesServlet = new AddGlassesServlet(glassesService);
        EditGlassesServlet editGlassesServlet = new EditGlassesServlet(glassesService);
        DeleteServlet deleteServlet = new DeleteServlet(glassesService);

        //context.addServlet(new ServletHolder(homeServlet), "");
        //context.addServlet(new ServletHolder(glassesServlet), "/glasses/*");
        //context.addServlet(new ServletHolder(articleServlet), "/articles");
        //context.addServlet(new ServletHolder(contactServlet), "/contacts");
        //context.addServlet(new ServletHolder(catalogGlassesServlet), "/catalog");
        //context.addServlet(new ServletHolder(catalogGlassesServlet), "/search");
        //context.addServlet(new ServletHolder(categoryServlet), "/sun");
        //context.addServlet(new ServletHolder(categoryServlet), "/optical");
        //context.addServlet(new ServletHolder(addGlassesServlet), "/addGlasses");
        //context.addServlet(new ServletHolder(editGlassesServlet), "/edit");
        //context.addServlet(new ServletHolder(deleteServlet), "/delete");

        context.setErrorHandler(new DefaultErrorHandler());

        Resource resource = JarFileResource.newClassPathResource("/webapp/static");
        context.setBaseResource(resource);
        context.addServlet(DefaultServlet.class, "/");

        Server server = new Server(PORT);
        server.setHandler(context);
        server.start();
        log.info("Server START - {}", Arrays.toString(server.getConnectors()));
    }*/
}

//TODO: Не важные вопросы, если будет свободное время
// 1. В видео на Stepik  лектор говорит что операция isEmpty "гораздо, более дешовая операция" чем if(size==0)?