package com.rk.web.servlet.user;

import com.rk.ServiceLocator;
import com.rk.domain.Order;
import com.rk.security.entity.Session;
import com.rk.service.OrderService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveOrderServlet extends HttpServlet {
    private OrderService orderService;

    public SaveOrderServlet() {
        this.orderService = ServiceLocator.getBean("OrderService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = (Session) request.getAttribute("session");
        Order order = session.getOrder();
        if (order != null) {
            orderService.save(order);
           session.setOrder(null);
        }
        response.sendRedirect("");
    }
}
