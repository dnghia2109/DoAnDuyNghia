package vn.techmaster.finalproject.model.entity;

public enum CompanyType {
    OUTSOURSING("Outsoursing"),
    PRODUCT("Product"),
    BANK("Bank");

    public final String label;
    private CompanyType(String label) {
        this.label = label;
    }
}
