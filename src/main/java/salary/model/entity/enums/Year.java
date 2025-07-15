package salary.model.entity.enums;

public enum Year {
    Y1404("1404"),
    Y1405("1405");

    private final String label;

    Year(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
