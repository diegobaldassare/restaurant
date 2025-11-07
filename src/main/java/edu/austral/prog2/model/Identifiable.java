package edu.austral.prog2.model;

/**
 * Interface for entities that have a unique identifier.
 * This allows generic operations on entities with IDs.
 */
public interface Identifiable {
    /**
     * Returns the unique identifier of this entity.
     * 
     * @return the ID of the entity
     */
    int getId();
}

