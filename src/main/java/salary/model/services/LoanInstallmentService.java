package salary.model.services;

import salary.model.entity.LoanInstallment;
import salary.model.repository.LoanInstallmentRepository;

import java.util.List;

public class LoanInstallmentService {

    public static void save(LoanInstallment installment) throws Exception {
        try(LoanInstallmentRepository repository = new LoanInstallmentRepository()) {
            repository.save(installment);
        }

    }

    public static void edit(LoanInstallment installment) throws Exception {
        try(LoanInstallmentRepository repository = new LoanInstallmentRepository()) {
            if (repository.findById(installment.getId()) != null) {
                repository.edit(installment);
            } else {
                throw new Exception("Installment not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try(LoanInstallmentRepository repository = new LoanInstallmentRepository()) {
            if (repository.findById(id) != null) {
                repository.delete(id);
            } else {
                throw new Exception("Installment not found");
            }
        }
    }

    public static List<LoanInstallment> findAll() throws Exception {
        try(LoanInstallmentRepository repository = new LoanInstallmentRepository()) {
            return repository.findAll();
        }
    }

    public static LoanInstallment findById(int id) throws Exception {
        try(LoanInstallmentRepository repository = new LoanInstallmentRepository()) {
            return repository.findById(id);
        }
    }

    public static List<LoanInstallment> findByEmployeeLoanId(int employeeLoanId) throws Exception {
        try(LoanInstallmentRepository repository = new LoanInstallmentRepository()) {

            return repository.findByEmployeeLoanId(employeeLoanId);
        }
    }
}
