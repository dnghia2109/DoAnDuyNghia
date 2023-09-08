package vn.techmaster.finalproject.model.entity;

public enum Skill {
    Java("Java"),
    CSharp("Csharp"),
    AWS("AWS"),
    PHP("PHP"),
    REACT("REACT"),
    CSHARP("CSHARP"),
    CPLUS("CPLUS"),
    SQL("SQL");
    public final String label;
    private Skill(String label) {
        this.label = label;
    }
}
