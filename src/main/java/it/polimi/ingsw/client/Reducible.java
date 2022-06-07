package it.polimi.ingsw.client;

import java.io.Serializable;

/**
 * An interface to indicate the ability of a class to be reduced in another class
 * that can be sent over the network
 * @param <T> the class that this can be reduced into
 */
public interface Reducible<T extends Serializable > {

    /**
     * Reduces this in the corresponding class
     * @return a reduced version of this
     */
    T reduce();
}
