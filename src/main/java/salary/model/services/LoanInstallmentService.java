package salary.model.services;

import salary.model.entity.LoanInstallment;
import salary.model.repository.LoanInstallmentRepository;

import java.util.List;

public class LoanInstallmentService {

    private static final LoanInstallmentRepository loanInstallmentRepository;

    static {
        try {
            loanInstallmentRepository = new LoanInstallmentRepository();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing LoanInstallmentRepository", e);
        }
    }

    public static void save(LoanInstallment loanInstallment) throws Exception {
        loanInstallmentRepository.save(loanInstallment);
    }

    public static void edit(LoanInstallment loanInstallment) throws Exception {
        if (loanInstallmentRepository.findById(loanInstallment.getId()) != null) {
            loanInstallmentRepository.edit(loanInstallment);
        } else {
            throw new Exception("LoanInstallment not found");
        }
    }

    public static void delete(int id) throws Exception {
        if (loanInstallmentRepository.findById(id) != null) {
            loanInstallmentRepository.delete(id);
        } else {
            throw new Exception("LoanInstallment not found");
        }
    }

    public static List<LoanInstallment> findAll() throws Exception {
        return loanInstallmentRepository.findAll();
    }

    public static LoanInstallment findById(int id) throws Exception {
        return loanInstallmentRepository.findById(id);
    }

    public static List<LoanInstallment> findByLoanId(int loanId) throws Exception {
        return loanInstallmentRepository.findByLoanId(loanId);
    }
}
