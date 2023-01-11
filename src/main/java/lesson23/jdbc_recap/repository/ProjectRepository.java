package lesson23.jdbc_recap.repository;

import lesson23.jdbc_recap.entity.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static lesson23.jdbc_recap.utils.DatabaseUtils.dbCon;

/**
 * Repository (Persistence layer) is responsible for database related operations
 * eg. Service is responsible for Business logic
 * Isivaizduokime repository kaip layer
 */
public class ProjectRepository {

    private static final String SELECT_FROM_PROJECTS = "SELECT * FROM projects";
    private static final String INSERT_PROJECTS = "INSERT INTO PROJECTS (name, budget) VALUES ('%s',%d)";

    public void add(Project project) {
        try {
            Statement statement = dbCon.createStatement();
            statement.executeUpdate(String.format(INSERT_PROJECTS, project.getName(), project.getBudget()));
            System.out.println("Project added");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Project> findAll() {

        List<Project> projects = new ArrayList<>();

        try {
            Statement statement = dbCon.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_PROJECTS);

            projects = constructProjectList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    private static List<Project> constructProjectList(ResultSet resultSet) throws SQLException {

        List<Project> projects = new ArrayList<>();
        while (resultSet.next()) {
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setName(resultSet.getString("name"));
            project.setBudget(resultSet.getInt("budget"));
            projects.add(project);
        }
        return projects;
    }

}
