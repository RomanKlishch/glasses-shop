package com.rk.web.handler;

import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DefaultErrorHandler extends ErrorPageErrorHandler {

    @SneakyThrows
    protected void generateAcceptableResponse(Request baseRequest, HttpServletRequest request,
                                              HttpServletResponse response, int code,
                                              String message, String mimeType) {

        Map<String, Object> pageVeriables = new HashMap<>();

        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        baseRequest.setHandled(true);
        pageVeriables.put("code", code);
        pageVeriables.put("message", message);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        PageGenerator.instance().process("error", pageVeriables, response.getWriter());
        log.error("Error in servlet: {}, code: {}, message: {}", servletName, code, message, exception);
    }
}
