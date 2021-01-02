package com.rk.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ConfigureApplication.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{/*new DelegatingFilterProxy("UserRoleFilter")
                ,new DelegatingFilterProxy("AdminRoleFilter")*/};
    }
}
/*
    <?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
        http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
        version="4.0">
        >


<!--ADMIN-->
<servlet>
<servlet-name>AddGlassesServlet</servlet-name>
<servlet-class>com.rk.web.servlet.admin.AddGlassesServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>AddGlassesServlet</servlet-name>
<url-pattern>/addGlasses</url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>EditGlassesServlet</servlet-name>
<servlet-class>com.rk.web.servlet.admin.EditGlassesServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>EditGlassesServlet</servlet-name>
<url-pattern>/edit</url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>DeleteServlet</servlet-name>
<servlet-class>com.rk.web.servlet.admin.DeleteGlassesServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>DeleteServlet</servlet-name>
<url-pattern>/delete</url-pattern>
</servlet-mapping>

<!--USER-->
<servlet>
<servlet-name>ContactServlet</servlet-name>
<servlet-class>com.rk.web.servlet.user.ContactServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>ContactServlet</servlet-name>
<url-pattern>/contacts</url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>ArticleServlet</servlet-name>
<servlet-class>com.rk.web.servlet.user.ArticleServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>ArticleServlet</servlet-name>
<url-pattern>/articles</url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>CategoryServlet</servlet-name>
<servlet-class>com.rk.web.servlet.user.CategoryServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>CategoryServlet</servlet-name>
<url-pattern>/sun</url-pattern>
<url-pattern>/optical</url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>HomeServlet</servlet-name>
<servlet-class>com.rk.web.servlet.user.HomeServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>HomeServlet</servlet-name>
<url-pattern>/home</url-pattern>
<url-pattern></url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>CatalogGlassesServlet</servlet-name>
<servlet-class>com.rk.web.servlet.user.CatalogGlassesServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>CatalogGlassesServlet</servlet-name>
<url-pattern>/catalog</url-pattern>
<url-pattern>/search</url-pattern>
</servlet-mapping>
<servlet>
<servlet-name>GlassesServlet</servlet-name>
<servlet-class>com.rk.web.servlet.user.GlassesServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>GlassesServlet</servlet-name>
<url-pattern>/glasses*//*</url-pattern>
    </servlet-mapping>
    <servlet>
        <servlet-name>LogoutServlet</servlet-name>
        <servlet-class>com.rk.web.servlet.user.LogoutServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>LogoutServlet</servlet-name>
        <url-pattern>/logout</url-pattern>
    </servlet-mapping>
    <servlet>
        <servlet-name>AddtoCart</servlet-name>
        <servlet-class>com.rk.web.servlet.cart.SetUpCartServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AddtoCart</servlet-name>
        <url-pattern>/cart</url-pattern>
    </servlet-mapping>
    <servlet>
        <servlet-name>SaveOrder</servlet-name>
        <servlet-class>com.rk.web.servlet.cart.SaveOrderServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>SaveOrder</servlet-name>
        <url-pattern>/saveOrder</url-pattern>
    </servlet-mapping>
    <servlet>
        <servlet-name>CancelOrder</servlet-name>
        <servlet-class>com.rk.web.servlet.cart.CancelOrderServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>CancelOrder</servlet-name>
        <url-pattern>/cancelOrder</url-pattern>
    </servlet-mapping>

    <!--GUEST-->
    <servlet>
        <servlet-name>Registration</servlet-name>
        <servlet-class>com.rk.web.servlet.RegistrationServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>Registration</servlet-name>
        <url-pattern>/registration</url-pattern>
    </servlet-mapping>
    <servlet>
        <servlet-name>Login</servlet-name>
        <servlet-class>com.rk.web.servlet.LoginServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>Login</servlet-name>
        <url-pattern>/login</url-pattern>
    </servlet-mapping>

    <error-page>
        <location>/error.html</location>
    </error-page>

    <!--FILTER-->
    <filter>
        <filter-name>UserRoleFilter</filter-name>
        <filter-class>com.rk.web.filter.UserRoleFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>UserRoleFilter</filter-name>
        <url-pattern>/</url-pattern>
        <url-pattern>/home</url-pattern>
        <url-pattern>/glasses/*</url-pattern>
        <url-pattern>/catalog</url-pattern>
        <url-pattern>/search</url-pattern>
        <url-pattern>/sun</url-pattern>
        <url-pattern>/optical</url-pattern>
        <url-pattern>/contacts</url-pattern>
        <url-pattern>/articles</url-pattern>
        <url-pattern>/edit</url-pattern>
        <url-pattern>/addGlasses</url-pattern>
        <url-pattern>/delete</url-pattern>
        <url-pattern>/logout</url-pattern>
        <url-pattern>/cart</url-pattern>
        <url-pattern>/saveOrder</url-pattern>
        <url-pattern>/cancelOrder</url-pattern>
    </filter-mapping>

    <filter>
        <filter-name>AdminRoleFilter</filter-name>
        <filter-class>com.rk.web.filter.AdminRoleFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>AdminRoleFilter</filter-name>
        <url-pattern>/edit</url-pattern>
        <url-pattern>/addGlasses</url-pattern>
        <url-pattern>/delete</url-pattern>
    </filter-mapping>
</web-app>*/
