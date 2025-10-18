/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */

@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 5)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);
        blueprints.put(new Tuple<>(bp0.getAuthor(), bp0.getName()), bp0);

        // Nuevos planos para cumplir el requisito
        Point[] pts1 = new Point[]{new Point(10, 10), new Point(20, 20), new Point(30, 30)};
        Blueprint bp1 = new Blueprint("john", "house", pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);

        Point[] pts2 = new Point[]{new Point(5, 5), new Point(25, 25), new Point(45, 45)};
        Blueprint bp2 = new Blueprint("john", "office", pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);

        Point[] pts3 = new Point[]{new Point(0, 0), new Point(50, 50), new Point(100, 100)};
        Blueprint bp3 = new Blueprint("mary", "school", pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);

        Point[] pts4 = new Point[]{new Point(12, 8), new Point(35, 22), new Point(67, 89)};
        Blueprint bp4 = new Blueprint("alice", "library", pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);

        Point[] pts5 = new Point[]{
            new Point(250, 50),
            new Point(300, 200),
            new Point(400, 200),
            new Point(320, 280),
            new Point(350, 400),
            new Point(250, 330),
            new Point(150, 400),
            new Point(180, 280),
            new Point(100, 200),
            new Point(200, 200),
            new Point(250, 50)
        };
        Blueprint bp5 = new Blueprint("juan", "estrella", pts5);
        blueprints.put(new Tuple<>(bp5.getAuthor(), bp5.getName()), bp5);
        
        Point[] pts6 = new Point[]{
            new Point(100, 100),
            new Point(400, 100),
            new Point(400, 400),
            new Point(100, 400),
            new Point(100, 100)
        };
        Blueprint bp6 = new Blueprint("juan", "cuadrado", pts6);
        blueprints.put(new Tuple<>(bp6.getAuthor(), bp6.getName()), bp6);
        

    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }



    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> result = new HashSet<>();
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(author)) {
                result.add(entry.getValue());
            }
        }
        if (result.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return result;
    }
    @Override
    public void updateBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());

        if (!blueprints.containsKey(key)) {
            throw new BlueprintPersistenceException("Blueprint not found for update: " + bp.getAuthor() + "/" + bp.getName());
        }

        // Obtenemos el blueprint existente
        Blueprint existing = blueprints.get(key);

        // Actualizamos sólo sus puntos
        existing.setPoints(bp.getPoints());
    }

}
