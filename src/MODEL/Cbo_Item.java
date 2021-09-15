package MODEL;

public class Cbo_Item {
    private  String Display;
    private  String Value;

    public Cbo_Item(String display, String value) {
        Display = display;
        Value = value;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
