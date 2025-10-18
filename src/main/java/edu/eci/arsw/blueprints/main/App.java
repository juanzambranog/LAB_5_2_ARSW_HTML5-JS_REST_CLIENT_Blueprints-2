package edu.eci.arsw.blueprints.main;


import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class App {

    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(App.class);

        BlueprintsServices services = ac.getBean(BlueprintsServices.class);

        Point[] pts1 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp1 = new Blueprint("juan", "planoCasa", pts1);

        Point[] pts2 = new Point[]{new Point(5, 5), new Point(15, 15)};
        Blueprint bp2 = new Blueprint("juan", "planoCarro", pts2);

        Point[] pts3 = new Point[]{new Point(0, 0), new Point(30, 30)};
        Blueprint bp3 = new Blueprint("maria", "planoParque", pts3);

        services.addNewBlueprint(bp1);
        services.addNewBlueprint(bp2);
        services.addNewBlueprint(bp3);

        System.out.println("=== TODOS LOS BLUEPRINTS ===");
        services.getAllBlueprints().forEach(System.out::println);

        System.out.println("\n=== BLUEPRINTS DE JUAN ===");
        services.getBlueprintsByAuthor("juan").forEach(System.out::println);

        System.out.println("\n=== BLUEPRINT ESPECÍFICO (maria - planoParque) ===");
        System.out.println(services.getBlueprint("maria", "planoParque"));
    }
}