package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("redundancyFilter")
public class RedundancyFilter implements BluePrintFilter {
    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filtered = new ArrayList<>();
        Point prev = null;
        for (Point p : bp.getPoints()) {
            if (prev == null || !(prev.getX() == p.getX() && prev.getY() == p.getY())) {
                filtered.add(p);
            }
            prev = p;
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), filtered.toArray(new Point[0]));
    }
}