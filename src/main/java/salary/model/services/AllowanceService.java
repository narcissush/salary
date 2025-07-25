package salary.model.services;

import salary.model.entity.Allowance;
import salary.model.entity.enums.Year;
import salary.model.repository.AllowanceRepository;

public class AllowanceService {
    public static void save(Allowance allowance) throws Exception {
        try (AllowanceRepository allowanceRepository = new AllowanceRepository()) {
            allowanceRepository.save(allowance);
        }
    }

    public static void  edit(Allowance allowance) throws Exception {
        try (AllowanceRepository allowanceRepository = new AllowanceRepository()) {
             allowanceRepository.edit(allowance);
        }
    }


    public static Allowance findByYear(Year year) throws Exception {
        try (AllowanceRepository allowanceRepository = new AllowanceRepository()) {
            return allowanceRepository.findByYear(Year.valueOf(year.toString()));
        }
    }

}
