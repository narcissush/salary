package salary.tools;

import com.github.mfathi91.time.PersianDate;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class DataConvert {
    public static LocalDate ShamsiToMiladi(String date) {
        return PersianDate.parse(date).toGregorian();
    }

    public static PersianDate MiladiToShamsi(LocalDate date) {
        return PersianDate.fromGregorian(date);
    }

    public static double ParseDouble(String text) {
        if (text == null || text.isEmpty()) return 0.0;
        text = text.replace("٬", "").replace("٫", ".").replace(" ", "");
        return Double.parseDouble(text);
    }


    public static String decimalFormat(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

}



