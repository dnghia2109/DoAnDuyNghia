package vn.techmaster.finalproject.model.entity;

public enum ApplyState {
    NONE("None"),
    APPLIED("Applied"),
    ACCEPT("Accept"),
    REJECT("Reject"),;

    public final String label;
    private ApplyState(String label) {
        this.label = label;
    }
}
