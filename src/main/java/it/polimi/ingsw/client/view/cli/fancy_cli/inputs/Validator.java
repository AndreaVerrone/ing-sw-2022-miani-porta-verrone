package it.polimi.ingsw.client.view.cli.fancy_cli.inputs;

import java.util.function.Predicate;

/**
 * A class used to validate a particular input
 * @param <T> the type of the input that need to be validated
 */
public class Validator<T> {
    /**
     * The actual predicate of this validator
     */
    private final Predicate<T> predicate;
    /**
     * A convenience attribute to match any character in a string
     */
    public static final String MATCH_ANYTHING = "[\\d\\D]";

    private Validator(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    protected Predicate<T> getPredicate(){
        return predicate;
    }

    /**
     * Checks if the provided input passes this validation
     * @param input the input to test
     * @return {@code true} if the validation is passed, {@code false} otherwise
     */
    public boolean validate(T input){
        return predicate.test(input);
    }

    /**
     * Creates a new Validator that checks the exact opposite of this.
     * @return a new Validator with reversed condition
     */
    public Validator<T> reverse(){
        return new Validator<>(predicate.negate());
    }

    /**
     * Returns a composed validator that represents a short-circuiting logical AND of this validator and another.
     * When evaluating the composed validator, if this validator fails, then the other validator is not evaluated.
     * @param other a validator that will be logically-ANDed with this
     * @return composed validator that represents the short-circuiting logical AND of this validator and the other
     */
    public Validator<T> and(Validator<T> other){
       return new Validator<>(predicate.and(other.predicate));
    }

    /**
     * Returns a composed validator that represents a short-circuiting logical OR of this validator and another.
     * When evaluating the composed validator, if this validator succeeded,
     * then the other validator is not evaluated.
     * @param other a validator that will be logically-ORed with this
     * @return composed validator that represents the short-circuiting logical OR of this validator and the other
     */
    public Validator<T> or(Validator<T> other){
        return new Validator<>(predicate.or(other.predicate));
    }

    /**
     * Creates a new validator to check if the input begins with the provided match
     * @param match the pattern to check if the input begins with
     * @return a Validator for doing this check
     */
    public static Validator<String> beginsWith(String match){
        return new Validator<>(s -> s.matches(match + MATCH_ANYTHING + "*"));
    }

    /**
     * Creates a new validator to check if the input ends with the provided match
     * @param match the pattern to check if the input ends with
     * @return a Validator for doing this check
     */
    public static Validator<String> endsWith(String match){
        return new Validator<>(s -> s.matches(MATCH_ANYTHING+"*"+match));
    }

    /**
     * Creates a new validator to check if in any place of the input
     * there is the provided match.
     * @param match the pattern to check for existing
     * @return a Validator for doing this check
     */
    public static Validator<String> contains(String match){
        return new Validator<>(s -> s.matches(MATCH_ANYTHING+"*"+match+MATCH_ANYTHING+"*"));
    }

    /**
     * Creates a new validator to check if the input is the number specified
     * @param num the number to check for equality
     * @return a Validator for doing this check
     */
    public static Validator<Integer> isOfNum(int num){
        return new Validator<>(i -> i == num);
    }

    /**
     * Creates a new validator to check if the input is in the range
     * from start to end, extremis included.
     * @param start the lesser value of the range
     * @param end the highest value of the range
     * @return a Validator for doing this check
     */
    public static Validator<Integer> isInRange(int start, int end){
        return new Validator<>(i -> i >= start && i <= end);
    }
}
