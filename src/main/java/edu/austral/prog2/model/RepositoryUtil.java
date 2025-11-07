package edu.austral.prog2.model;

import java.util.List;

/**
 * Utility class providing generic repository operations for entities that implement Identifiable.
 * This eliminates code duplication when searching for entities by ID.
 */
public class RepositoryUtil {
    
    /**
     * Finds an entity in a list by its ID.
     * 
     * @param <T> the type of entity that implements Identifiable
     * @param items the list of entities to search
     * @param id the ID to search for
     * @return the entity with the matching ID, or null if not found
     */
    public static <T extends Identifiable> T findById(List<T> items, int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}

