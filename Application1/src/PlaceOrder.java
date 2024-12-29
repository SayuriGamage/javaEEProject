import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class PlaceOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");

            //fetch items
       ResultSet resultSet= connection.prepareStatement("select  code from item").executeQuery();
            JsonArrayBuilder allitems=Json.createArrayBuilder();
          while (resultSet.next()){
             String code= resultSet.getString("code");
         JsonObjectBuilder item= Json.createObjectBuilder();
          item.add("code", code);
          allitems.add(item);

          }
       JsonObjectBuilder jsonObjectBuilder=   Json.createObjectBuilder();
       jsonObjectBuilder.add("item",allitems);
       resp.setContentType("application/json");
       resp.getWriter().write(jsonObjectBuilder.build().toString());
       loaditemvalues(resp);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loaditemvalues(HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "Ijse@123");

        resp.setContentType("application/json");

        JsonArrayBuilder allItems = Json.createArrayBuilder();
        ResultSet resultSet = connection.prepareStatement("SELECT  description, qtyOnHand, unitPrice FROM item where code=?").executeQuery();

        // Loop through the result set and add all fields
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");

            JsonObjectBuilder item = Json.createObjectBuilder();
            item.add("name", name);
            item.add("price", price);
            item.add("quantity", quantity);

            allItems.add(item);
        }

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("item", allItems);

        resp.getWriter().write(jsonObjectBuilder.build().toString());
    }
}
