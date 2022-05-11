package it.polimi.ingsw.network;

import java.util.Objects;
import java.util.Random;

/**
 * A class representing a user in the game
 */
public class User {

    /**
     * The unique identifier of this user.
     */
    private final String identifier;

    /**
     * Creates a new user with the provided identifier.
     * @param id the identifier to use
     */
    public User(String id){
        identifier = id;
    }

    /**
     * Creates a new user, generating a new unique identifier.
     */
    public User(){
        identifier = generateNew();
    }
    public String getIdentifier() {
        return identifier;
    }

    private String generateNew(){
        int start = '0';
        int end = 'z';
        int size = 20;
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        random.ints(size, start, end+1).forEach(i -> stringBuilder.append((char) i));
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return identifier.equals(user.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
