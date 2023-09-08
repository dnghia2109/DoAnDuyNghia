package vn.techmaster.finalproject.model.entity;

public enum Ranking {
    FREE("Free"),
    VIP("Vip"),
    VVIP("Vvip"),
    ;
    public final String label;
    private Ranking(String label) {
        this.label = label;
    }
}
