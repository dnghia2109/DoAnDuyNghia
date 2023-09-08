package vn.techmaster.finalproject.model.entity;

public enum RoleEnum {

    ADMIN("Admin"),
    APPLICANT("Applicant"),
    EMPLOEYER("Employer");

    public final String label;
    private RoleEnum(String label) {
        this.label = label;
    }
}
