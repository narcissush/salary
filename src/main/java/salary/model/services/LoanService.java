package salary.model.services;

import salary.model.entity.Loan;
import salary.model.repository.LoanRepository;

import java.util.List;

public class LoanService {

    public static void save(Loan loan) throws Exception {
        LoanRepository repository = new LoanRepository();
        try {
            repository.save(loan);
        } finally {
            repository.close();
        }
    }

    public static void edit(Loan loan) throws Exception {
        LoanRepository repository = new LoanRepository();
        try {
            if (repository.findById(loan.getId()) != null) {
                repository.edit(loan);
            } else {
                throw new Exception("Loan not found");
            }
        } finally {
            repository.close();
        }
    }

    public static void delete(int id) throws Exception {
        LoanRepository repository = new LoanRepository();
        try {
            if (repository.findById(id) != null) {
                repository.delete(id);
            } else {
                throw new Exception("Loan not found");
            }
        } finally {
            repository.close();
        }
    }

    public static List<Loan> findAll() throws Exception {
        LoanRepository repository = new LoanRepository();
        try {
            return repository.findAll();
        } finally {
            repository.close();
        }
    }

    public static Loan findById(int id) throws Exception {
        LoanRepository repository = new LoanRepository();
        try {
            return repository.findById(id);
        } finally {
            repository.close();
        }
    }

    public static List<Loan> findByEmployeeId(int employeeId) throws Exception {
        LoanRepository repository = new LoanRepository();
        try {
            return repository.findByEmployeeId(employeeId);
        } finally {
            repository.close();
        }
    }
}
