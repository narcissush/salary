package salary.model.services;

import salary.model.entity.LoanInstallment;
import salary.model.repository.LoanInstallmentRepository;

import java.util.List;

public class LoanInstallmentService {


    public static void save(LoanInstallment loanInstallment) throws Exception {
        try (LoanInstallmentRepository loanInstallmentRepository = new LoanInstallmentRepository()) {
            loanInstallmentRepository.save(loanInstallment);
        }
    }

    public static void edit(LoanInstallment loanInstallment) throws Exception {
        try (LoanInstallmentRepository loanInstallmentRepository = new LoanInstallmentRepository()) {
            if (loanInstallmentRepository.findById(loanInstallment.getId()) != null) {
                loanInstallmentRepository.edit(loanInstallment);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (LoanInstallmentRepository loanInstallmentRepository = new LoanInstallmentRepository()) {
            if (loanInstallmentRepository.findById(id) != null) {
                loanInstallmentRepository.delete(id);
            } else {
                throw new Exception("LoanItem not found");
            }
        }
    }

    public static List<LoanInstallment> findAll() throws Exception {
        try (LoanInstallmentRepository loanInstallmentRepository = new LoanInstallmentRepository()) {
            return loanInstallmentRepository.findAll();
        }
    }

    public static LoanInstallment findById(int id) throws Exception {
        try (LoanInstallmentRepository loanInstallmentRepository = new LoanInstallmentRepository()) {
            return loanInstallmentRepository.findById(id);
        }
    }


}
