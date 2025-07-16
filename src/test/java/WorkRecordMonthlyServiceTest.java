import salary.model.entity.WorkRecordMonthly;
import salary.model.services.WorkRecordMonthlyService;

public class WorkRecordMonthlyServiceTest {
    public static void main(String[] args) throws Exception {
        WorkRecordMonthly workRecordMonthly =
                WorkRecordMonthly.builder()
                        .id(1)
                        .daysWorked(31)
                        .overtimeHours("05:20")
                        .underTimeHours("02:10")
                        .leave("12:20")
                        .advance(10_000_000)
                        .build();
        WorkRecordMonthlyService.save(workRecordMonthly);
        System.out.println(workRecordMonthly);

    }
}
