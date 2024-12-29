import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.json.Json.createObjectBuilder;


@WebServlet(urlPatterns = "/lcustomer")
public class LoadCustomerServerlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId"); // Get customer ID from request
        resp.setContentType("application/json");

        try (PrintWriter out = resp.getWriter()) {
            if (customerId != null && !customerId.isEmpty()) {
                String customerName = loadCustomerName(customerId); // Fetch customer name from DB
                if (customerName != null) {
                    JsonObject json = createObjectBuilder()
                            .add("customerName", customerName)
                            .build();
                    out.print(json);
                    resp.setStatus(HttpServletResponse.SC_OK);

                }
            }

        }
    }

    private String loadCustomerName(String customerId) {
        String query = "SELECT name FROM customer WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}