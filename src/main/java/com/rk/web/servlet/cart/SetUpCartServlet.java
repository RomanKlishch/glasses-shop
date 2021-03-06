package com.rk.web.servlet.cart;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.Order;
import com.rk.security.entity.Session;
import com.rk.service.GlassesService;
import com.rk.service.OrderService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rk.constants.WebConstants.CONTENT_TYPE;

public class SetUpCartServlet extends HttpServlet {
    private GlassesService glassesService;
    private OrderService orderService;

    public SetUpCartServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
        this.orderService = ServiceLocator.getBean("OrderService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        Session session = (Session) request.getAttribute("session");
        Order sessionOrder = session.getOrder();
        List<Order> ordersFromBD = orderService.findOrderByUserId(session.getUser().getId().getId());

        pageVariables.put("orderFromSession", sessionOrder);
        pageVariables.put("ordersFromBD", ordersFromBD);

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("/cart", pageVariables, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Glasses glasses = glassesService.findById(Long.parseLong(request.getParameter("id")));

        Session session = (Session) request.getAttribute("session");
        Order order = session.getOrder();
        Map<Glasses, Integer> glassesMap;
        if (order == null) {
            glassesMap = new HashMap<>();
            glassesMap.put(glasses, 1);
            order = Order.builder()
                    .glassesMap(glassesMap)
                    .user(session.getUser())
                    .status("CONFIRMED")
                    .build();
            session.setOrder(order);
        } else {
            glassesMap = order.getGlassesMap();
            if (glassesMap.containsKey(glasses)) {
                glassesMap.computeIfPresent(glasses, (key, value) -> value + 1);
            } else {
                glassesMap.put(glasses, 1);
            }
        }
        response.sendRedirect("");
    }
}
