package com.rk.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;

@Slf4j
public class StaticResourcesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(path);

        if (resourceAsStream != null) {
            try (BufferedInputStream styleStream = new BufferedInputStream(resourceAsStream)) {
                ServletOutputStream outputStream = response.getOutputStream();
                int count;
                byte[] buffer = new byte[8196];
                while ((count = styleStream.read(buffer)) > -1) {
                    outputStream.write(buffer, 0, count);
                }
            } catch (Exception e) {
                log.error("Cannot read from resources ", e);
                throw new RuntimeException("Cannot read from resources ", e);
            }
        }else {
            log.debug("Resources not found -{}",path);
            throw new RuntimeException("Cannot read from resources");
        }
    }
}
