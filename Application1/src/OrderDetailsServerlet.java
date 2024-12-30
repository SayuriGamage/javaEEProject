import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/orders")
public class OrderDetailsServerlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderk=req.getParameter("orderId");
        String itemk=req.getParameter("itemCode");

      /*  System.out.println("order detail eke values tika enawada");
        System.out.println(orderk);
        System.out.println(itemk);*/

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","Ijse@123");

            PreparedStatement pst=connection.prepareStatement("insert into orderDetails values(?,?)");
            pst.setString(1,orderk);
            pst.setString(2,itemk);
            pst.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
