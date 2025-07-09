package salary.model.entity;


import java.io.Serializable;


public class EmployeeAllowances implements Serializable {
    //30 روز
    public static final double HOUSING_ALLOWANCE = 9_000_000;
    public static final double MARAGE_ALLOWANCE = 5_000_000;
    public static final double CHILD_ALLOWANCE = 9_316_038;
    public static final double FOOD_ALLOWANCE = 22_000_000;

    private EmployeeAllowances() {}
}
