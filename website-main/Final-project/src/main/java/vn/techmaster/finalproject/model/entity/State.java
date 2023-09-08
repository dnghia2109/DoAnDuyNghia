package vn.techmaster.finalproject.model.entity;

public enum State {
    PENDING("Pending"),
    ACTIVE("Active"),
    DISABLED("Disabled")
   ;
    public final String label;
    private State(String label) {
        this.label = label;
    }
}
