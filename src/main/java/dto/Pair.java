package dto;

public class Pair {

    Repository reference, referred;

    public Pair(Repository reference, Repository referred) {
        this.reference = reference;
        this.referred = referred;
    }

    public Repository getReference() {
        return this.reference;
    }

    public Repository getReferred() {
        return this.referred;
    }
}
