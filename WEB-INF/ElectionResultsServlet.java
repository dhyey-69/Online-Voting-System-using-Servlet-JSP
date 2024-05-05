import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class ElectionResultsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Result> results = fetchResults();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Election Results</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Election Results</h2>");
        out.println("<table border=\"1\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Candidate</th>");
        out.println("<th>Total Votes</th>");
        out.println("<th>Party</th>");
        out.println("<th>Bio</th>");
        out.println("<th>Image</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (Result result : results) {
            out.println("<tr>");
            out.println("<td>" + result.getCandidate().getName() + "</td>");
            out.println("<td>" + result.getTotalVotes() + "</td>");
            out.println("<td>" + result.getCandidate().getParty() + "</td>");
            out.println("<td>" + result.getCandidate().getBio() + "</td>");
            out.println("<td><img src=\"" + result.getCandidate().getImageUrl() + "\" alt=\"" + result.getCandidate().getName() + "\"></td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    public List<Result> fetchResults() {
        List<Result> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/election", "root", "root");
             PreparedStatement statement = connection.prepareStatement("SELECT candidate_id, total_votes FROM election_results");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int candidateId = resultSet.getInt("candidate_id");
                int totalVotes = resultSet.getInt("total_votes");

                Candidate candidate = getCandidateById(candidateId);
                if (candidate != null) {
                    results.add(new Result(candidate, totalVotes));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Candidate getCandidateById(int candidateId) {
        Candidate candidate = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/election", "root", "root");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM candidates WHERE candidate_id = ?")) {
            statement.setInt(1, candidateId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String party = resultSet.getString("party");
                    String bio = resultSet.getString("bio");
                    String imageUrl = resultSet.getString("image_url");
                    candidate = new Candidate(candidateId, name, party, bio, imageUrl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidate;
    }
}
