package salary.tools;

import com.github.mfathi91.time.PersianDate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;

public class DataConvert {
    public static LocalDate ShamsiToMiladi(String date) {
        return PersianDate.parse(date).toGregorian();
    }

    public static PersianDate MiladiToShamsi(LocalDate date) {
        return PersianDate.fromGregorian(date);
    }

    public static double ParseFarsiDouble(String text) {
        if (text == null || text.isEmpty()) return 0.0;
        text = text.replace("٬", "").replace("٫", ".").replace(" ", "");
        return Double.parseDouble(text);
    }


}
