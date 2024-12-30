import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/uitem")
public class UpdateItemServerlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       JsonObject jsonObject= Json.createReader(req.getReader()).readObject();

       String id=jsonObject.getString("itemCode");
       String qty=jsonObject.getString("qry");
        System.out.println("methana quntity eka update wenne naaaa");
        System.out.println(id);
        System.out.println(qty);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","Ijse@123");

            PreparedStatement pst=connection.prepareStatement("update item set qtyOnHand=qtyOnHand-? where code=?");

            pst.setString(1,qty);
            pst.setString(2,id);
            pst.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
