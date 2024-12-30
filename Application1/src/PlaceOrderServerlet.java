import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = "/order")
public class PlaceOrderServerlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String oid=req.getParameter("orderId");
        String cid=req.getParameter("customerId");
        String date=req.getParameter("date");
      /*  System.out.println(oid);
        System.out.println(cid);
        System.out.println(date);*/

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","Ijse@123");

            PreparedStatement pst=connection.prepareStatement("insert into orders values(?,?,?)");
            pst.setString(1,oid);
            pst.setDate(2, Date.valueOf(date));
            pst.setString(3,cid);
            pst.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
