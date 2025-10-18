/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;
import edu.eci.arsw.blueprints.filter.BluePrintFilter;
import edu.eci.arsw.blueprints.filter.SubsamplingFilter;
import edu.eci.arsw.blueprints.filter.RedundancyFilter;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    private final BlueprintsPersistence persistence;

    private final BluePrintFilter filter;

    /**
     * Constructor con inyección de dependencias.
     * Recibe tanto el manejador de persistencia como el filtro que se desea usar.
     */
    @Autowired
    public BlueprintsServices(BlueprintsPersistence persistence, BluePrintFilter filter) {
        this.persistence = persistence;
        this.filter = filter;
    }

    /**
     * Agrega un nuevo plano al sistema
     * @param bp blueprint a registrar
     * @throws BlueprintPersistenceException si ya existe o hay error en la persistencia
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        persistence.saveBlueprint(bp);
    }

    /**
     * Retorna el blueprint de un autor y nombre específico, aplicando filtro.
     * @param author autor del plano
     * @param name nombre del plano
     * @return el blueprint filtrado
     * @throws BlueprintNotFoundException si no existe el plano
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = persistence.getBlueprint(author, name);
        return filter.filter(bp);
    }

    /**
     * Retorna todos los blueprints de un autor, aplicando el filtro correspondiente.
     * @param author nombre del autor
     * @return conjunto de planos filtrados
     * @throws BlueprintNotFoundException si no existen planos de ese autor
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> original = persistence.getBlueprintsByAuthor(author);
        Set<Blueprint> filtered = new HashSet<>();
        for (Blueprint bp : original) {
            filtered.add(filter.filter(bp));
        }
        return filtered;
    }

    /**
     * Retorna todos los blueprints del sistema, aplicando filtro.
     * @return conjunto de planos filtrados
     */
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> original = persistence.getAllBlueprints();
        Set<Blueprint> filtered = new HashSet<>();
        for (Blueprint bp : original) {
            filtered.add(filter.filter(bp));
        }
        return filtered;
    }

    /**
     * Update an existing blueprint
     * @param author blueprint's author
     * @param bpname blueprint's name
     * @param blueprint new blueprint data
     * @throws BlueprintNotFoundException if the blueprint doesn't exist
     * @throws BlueprintPersistenceException if there's an error updating
     */
    public void updateBlueprint(String author, String bpname, Blueprint blueprint)
            throws BlueprintNotFoundException, BlueprintPersistenceException {

        Blueprint existingBlueprint = persistence.getBlueprint(author, bpname);

        if (existingBlueprint == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + "/" + bpname);
        }

        persistence.updateBlueprint(blueprint);
    }
}




