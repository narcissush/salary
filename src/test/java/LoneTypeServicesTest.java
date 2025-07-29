import salary.model.entity.LoanType;
import salary.model.repository.LoanTypeRepository;
import salary.model.services.LoanTypeService;

public class LoneTypeServicesTest {
    public static void main(String[] args) throws Exception {
        LoanType loanType = LoanType.builder()
                .loanType("مسکن")
                .loanAmount(1000000000)
                .loanInterest(23)
                .totalInstallments(60)
                .build();

            LoanTypeService.save(loanType);
        System.out.println(loanType);


    }
}
