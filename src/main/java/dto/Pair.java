package dto;

public class Pair {

    Repository reference, referred;

    public Pair(Repository reference, Repository referred) {
        this.reference = reference;
        this.referred = referred;
    }
    public Repository getReference()
    {
        return this.reference;
    }
    public void setReference(Repository reference)
    {
        this.reference = reference;
    }
    public void setReferred(Repository referred)
    {
        this.referred = referred;
    }
    public Repository getReferred()
    {
        return this.referred;
    }
}
