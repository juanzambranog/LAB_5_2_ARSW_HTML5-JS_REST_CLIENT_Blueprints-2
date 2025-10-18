package edu.eci.arsw.blueprints.filter;



import edu.eci.arsw.blueprints.model.Blueprint;

public interface BluePrintFilter {
    Blueprint filter(Blueprint bp);
}