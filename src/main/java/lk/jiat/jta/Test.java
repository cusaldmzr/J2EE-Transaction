package lk.jiat.jta;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.jta.ejb.UserBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/test")
public class Test extends HttpServlet {

    @EJB
    private UserBean userBean;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //userBean.register("Supun", "supun@gmail.com", "1234");

        userBean.transfer(10020004001L,10020004002L, new BigDecimal(5000));


    }
}
