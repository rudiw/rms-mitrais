package com.mitrais.rms.controller;

import com.mitrais.rms.dao.UserDao;
import com.mitrais.rms.dao.impl.UserDaoImpl;
import com.mitrais.rms.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/users/*")
public class UserServlet extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.err.println("servletPath: " + req.getServletPath());
        System.err.println("pathInfo: " + req.getPathInfo());
        switch (req.getPathInfo()) {
            case "/list":
                findAll(req, resp);
                break;
            case "/delete":
                try {
                    delete(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/new":
                showNewForm(req, resp);
                break;
            case "/edit":
                try {
                    showEditForm(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/update":
                try {
                    update(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/insert":
                try {
                    insert(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String path = getTemplatePath(req.getServletPath() + req.getPathInfo());

        final UserDao userDao = UserDaoImpl.getInstance();
        final List<User> users = userDao.findAll();
        req.setAttribute("users", users);

        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        final Long id = Long.parseLong(request.getParameter("id"));
        final UserDao userDao = UserDaoImpl.getInstance();
        userDao.delete(id);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String path = getTemplatePath("/form");

        final RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        dispatcher.forward(request, response);
    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        final String userName = request.getParameter("username");
        final String userPass = request.getParameter("userpass");

        final User upUser = new User(null, userName, userPass);
        final UserDao userDao = UserDaoImpl.getInstance();
        userDao.add(upUser);

        response.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        final Long id = Long.parseLong(request.getParameter("id"));

        final UserDao userDao = UserDaoImpl.getInstance();
        final Optional<User> opt = userDao.find(id);

        final String path = getTemplatePath("/form");

        final RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        request.setAttribute("user", opt.get());
        dispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        final long id = Long.parseLong(request.getParameter("id"));
        final String userName = request.getParameter("username");
        final String userPass = request.getParameter("userpass");

        final User upUser = new User(null, userName, userPass);
        final UserDao userDao = UserDaoImpl.getInstance();
        userDao.modify(upUser);
        response.sendRedirect("list");
    }
}
