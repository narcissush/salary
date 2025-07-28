package salary.model.entity;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalaryItem {
        private final SimpleStringProperty title;
        private final SimpleDoubleProperty amount;

        public SalaryItem(String title, Double amount) {
            this.title = new SimpleStringProperty(title);
            this.amount = new SimpleDoubleProperty(amount);
        }

        public String getTitle() {
            return title.get();
        }

        public void setTitle(String value) {
            title.set(value);
        }

        public double getAmount() {
            return amount.get();
        }

        public void setAmount(double value) {
            amount.set(value);
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public SimpleDoubleProperty amountProperty() {
            return amount;
        }
    }

