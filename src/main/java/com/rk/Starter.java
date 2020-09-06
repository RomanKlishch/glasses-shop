package com.rk;

import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.util.PropertiesReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.Arrays;

@Slf4j
public class Starter {
    private static final PropertiesReader PROPERTIES_READER = new PropertiesReader("properties/configDB.properties");
    private static final int PORT = Integer.parseInt(PROPERTIES_READER.getProperties("PORT"));

    @SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(PROPERTIES_READER.getProperties("JDBC_DATABASE_URL"));
        dataSource.setUser(PROPERTIES_READER.getProperties("JDBC_DATABASE_USERNAME"));
        dataSource.setPassword(PROPERTIES_READER.getProperties("JDBC_DATABASE_PASSWORD"));
        JdbcGlassesDao jdbcGateDao = new JdbcGlassesDao(dataSource);
        DefaultGlassesService gateService = new DefaultGlassesService(jdbcGateDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        Server server = new Server(PORT);
        server.start();
        log.info("Server START - {}", Arrays.toString(server.getConnectors()));

    }
}
