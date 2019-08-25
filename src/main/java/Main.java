import com.dbtest.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.net.URL;
import java.util.List;

public class Main {

    private static EntityManager em;

    public static void main(String[] args) {

        File file = Main.getFileFromResources("META-INF/persistence.xml");


        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("EmployeePU");
        em = emf.createEntityManager();

        createEmployee(1, "Ravi", "Raj", "Textile");
        createEmployee(2, "Amit", "Raj", "IT");
        createEmployee(3, "Nitish", "Kumar", "Marketing");

        Query query = em.createNamedQuery("Employee_findAll", Employee.class);

        List<Employee> results = query.getResultList();

        results.forEach(i -> System.out.println(i.toString()));

    }

    private static File getFileFromResources(String fileName) {

        ClassLoader classLoader = Main.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }


    private static void createEmployee(int id, String firstName,
                                       String lastName, String dept) {
        em.getTransaction().begin();
        Employee emp = new Employee(id, firstName, lastName, dept);
        em.persist(emp);
        em.getTransaction().commit();

    }

}
