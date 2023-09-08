package vn.techmaster.finalproject.model.entity;

public enum JobType {
    PARTTIME("Part-time"),
    FULLTIME("Full-time"),;

    public final String label;
    private JobType(String label) {
        this.label = label;
    }
}
