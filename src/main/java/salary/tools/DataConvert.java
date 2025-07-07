package salary.tools;

import com.github.mfathi91.time.PersianDate;

import java.time.LocalDate;

public class DataConvert {
    public static LocalDate ShamsiToMiladi(String date) {
        return PersianDate.parse(date).toGregorian();
    }

    public static PersianDate MiladiToShamsi(LocalDate date) {
        return PersianDate.fromGregorian(date);
    }
}
