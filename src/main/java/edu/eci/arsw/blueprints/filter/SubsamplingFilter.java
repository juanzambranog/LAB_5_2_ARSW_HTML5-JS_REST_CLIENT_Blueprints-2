package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component("subsamplingFilter")
public class SubsamplingFilter implements BluePrintFilter {
    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> filtered = new ArrayList<>();
        List<Point> original = bp.getPoints();
        for (int i = 0; i < original.size(); i++) {
            if (i % 2 == 0) {
                filtered.add(original.get(i));
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), filtered.toArray(new Point[0]));
    }
}