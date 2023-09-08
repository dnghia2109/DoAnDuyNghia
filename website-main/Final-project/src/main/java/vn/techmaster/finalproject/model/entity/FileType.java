package vn.techmaster.finalproject.model.entity;

public enum FileType {
    PHOTO("Photo"),
    PDF("PDF"),;

    public final String label;
    private FileType(String label) {
        this.label = label;
    }
}
