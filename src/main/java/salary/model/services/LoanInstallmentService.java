package salary.model.services;

import salary.model.entity.LoanInstallment;
import salary.model.repository.LoanInstallmentRepository;

import java.util.List;

public class LoanInstallmentService {

    public static void save(LoanInstallment installment) throws Exception {
        new LoanInstallmentRepository().save(installment);
    }

    public static void edit(LoanInstallment installment) throws Exception {
        LoanInstallmentRepository repo = new LoanInstallmentRepository();
        if (repo.findById(installment.getId()) != null) {
            repo.edit(installment);
        } else {
            throw new Exception("Installment not found");
        }
    }

    public static void delete(int id) throws Exception {
        LoanInstallmentRepository repo = new LoanInstallmentRepository();
        if (repo.findById(id) != null) {
            repo.delete(id);
        } else {
            throw new Exception("Installment not found");
        }
    }

    public static List<LoanInstallment> findAll() throws Exception {
        LoanInstallmentRepository repo = new LoanInstallmentRepository();
        return repo.findAll();
    }

    public static LoanInstallment findById(int id) throws Exception {
        return new LoanInstallmentRepository().findById(id);
    }

    public static List<LoanInstallment> findByEmployeeLoanId(int employeeLoanId) throws Exception {
        return new LoanInstallmentRepository().findByEmployeeLoanId(employeeLoanId);
    }
}
