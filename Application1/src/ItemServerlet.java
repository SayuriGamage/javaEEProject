import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.json.Json.createObjectBuilder;

@WebServlet(urlPatterns = "/item")
public class ItemServerlet extends HttpServlet {

    private final List<ItemDTO> itemsList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","Ijse@123");

                      PreparedStatement preparedStatement= connection.prepareStatement("insert into item values(?,?,?,?)");
                       preparedStatement.setString(1,req.getParameter("code"));
                       preparedStatement.setString(2,req.getParameter("description"));
                       preparedStatement.setInt(3,Integer.parseInt(req.getParameter("qtyOnHand")));
                       preparedStatement.setDouble(4,Double.parseDouble(req.getParameter("unitPrice")));
                       preparedStatement.executeUpdate();

            resp.setContentType("application/json");

            resp.getWriter().write("{\"message\":\"item save\"}");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","Ijse@123");

          ResultSet resultSet= connection.prepareStatement(" select * from item").executeQuery();
          itemsList.clear();
           resultSet.next();
            JsonArrayBuilder allitems=Json.createArrayBuilder();

          while (resultSet.next()){
              String code=resultSet.getString("code");
              String description=resultSet.getString("description");
              int qty=resultSet.getInt("qtyOnHand");
              double price=resultSet.getDouble("unitPrice");

              System.out.println("data tika enne nane");
              System.out.println(code + " " + description + " " + qty + " " + price);

              ItemDTO itemDTO= new ItemDTO(code,description,qty,price);
              itemsList.add(itemDTO);
         JsonObjectBuilder item= createObjectBuilder();
         item.add("code",code);
         item.add("description",description);
         item.add("qtyOnHand",qty);
         item.add("unitPrice",price);
         allitems.add(item);
          }
       resp.setContentType("application/json");
          resp.getWriter().write(allitems.build().toString());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
