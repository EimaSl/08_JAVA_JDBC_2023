package lesson23.jdbc_recap;

import lesson23.jdbc_recap.entity.Project;
import lesson23.jdbc_recap.repository.ProjectRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProjectRepository projectRepository = new ProjectRepository();
        List<Project> allProjects = projectRepository.findAll();

        System.out.println(allProjects);

        Project naujasProjektas = new Project();
        naujasProjektas.setName("Kepykla");
        naujasProjektas.setBudget(15000);
        projectRepository.add(naujasProjektas);

        System.out.println();
        System.out.println(projectRepository.findAll());

    }
}
