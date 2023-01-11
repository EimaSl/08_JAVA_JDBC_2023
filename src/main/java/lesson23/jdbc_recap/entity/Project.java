package lesson23.jdbc_recap.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO representing databace table which of project related data
 * pojo - play old java project
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private int id;
    private String name;
    private int budget;
}
