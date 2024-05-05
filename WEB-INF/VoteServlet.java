import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class VoteServlet extends HttpServlet {
    String DB_URL = "jdbc:mysql://localhost:3306/election";
    String DB_USERNAME = "root";
    String DB_PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    
        String username = (String) session.getAttribute("username");
        int candidateId = Integer.parseInt(request.getParameter("candidate_id"));
    
        if (hasUserVoted(username)) {
            response.sendRedirect("alreadyVoted.jsp");
            return;
        }
    
        if (vote(username, candidateId)) {
            updateElectionResult(candidateId);
            response.sendRedirect("votingSuccess.jsp");
        } else {
            response.sendRedirect("votingFailure.jsp");
        }
    }
    
    

    public boolean vote(String username, int candidateId) {
        if (hasUserVoted(username)) {
            return false;
        }
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO votes (user_id, candidate_id) VALUES (?, ?)")) {
            int userId = getUserIdByUsername(username);
            if (userId == -1) {
                return false;
            }
            statement.setInt(1, userId);
            statement.setInt(2, candidateId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean hasUserVoted(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM votes WHERE user_id = (SELECT user_id FROM users WHERE username = ?)")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public int getUserIdByUsername(String username) {
        int userId = -1;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public void updateElectionResult(int candidateId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String updateQuery = "INSERT INTO election_results (candidate_id, total_votes) " +
                                 "VALUES (?, 1) " +
                                 "ON DUPLICATE KEY UPDATE total_votes = total_votes + 1";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, candidateId);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
