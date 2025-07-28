package salary.model.services;

import salary.model.entity.EmployeeLoan;
import salary.model.repository.EmployeeLoanRepository;
import salary.model.repository.EmployeeRepository;

import java.util.List;

public class EmployeeLoanService {

    public static void save(EmployeeLoan loan) throws Exception {
        try (EmployeeLoanRepository repository = new EmployeeLoanRepository()) {
             repository.save(loan);
        }
    }

    public static void edit(EmployeeLoan loan) throws Exception {
        try (EmployeeLoanRepository repository = new EmployeeLoanRepository()) {
            if (repository.findById(loan.getId()) != null) {
                repository.edit(loan);
            } else {
                throw new Exception("EmployeeLoan not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (EmployeeLoanRepository repository = new EmployeeLoanRepository()) {
            if (repository.findById(id) != null) {
                repository.delete(id);
            } else {
                throw new Exception("EmployeeLoan not found");
            }
        }
    }

    public static List<EmployeeLoan> findAll() throws Exception {
        try (EmployeeLoanRepository repository = new EmployeeLoanRepository()) {
            return repository.findAll();
        }
    }

    public static EmployeeLoan findById(int id) throws Exception {
        try (EmployeeLoanRepository repository = new EmployeeLoanRepository()) {
            return  repository.findById(id);
        }
    }

    public static List<EmployeeLoan> findByEmployeeId(int employeeId) throws Exception {
        try (EmployeeLoanRepository repository = new EmployeeLoanRepository()) {
            return repository.findByEmployeeId(employeeId);
        }
    }
}
