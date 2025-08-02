package salary.model.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SalaryItem {
    private final SimpleStringProperty title;
    private final SimpleStringProperty amount;


    public SalaryItem(String title, String amount) {
        this.title = new SimpleStringProperty(title);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String value) {
        title.set(value);
    }

    public String getAmount() {
        return amount.get();
    }

    public void setAmount(String value) {
        amount.set(value);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty amountProperty() { return amount; }
}

