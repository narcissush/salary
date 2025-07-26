package salary.model.services;

import salary.model.entity.EmployeeLoan;
import salary.model.repository.EmployeeLoanRepository;

import java.util.List;

public class EmployeeLoanService {

    public static void save(EmployeeLoan loan) throws Exception {
        new EmployeeLoanRepository().save(loan);
    }

    public static void edit(EmployeeLoan loan) throws Exception {
        EmployeeLoanRepository repo = new EmployeeLoanRepository();
        if (repo.findById(loan.getId()) != null) {
            repo.edit(loan);
        } else {
            throw new Exception("EmployeeLoan not found");
        }
    }

    public static void delete(int id) throws Exception {
        EmployeeLoanRepository repo = new EmployeeLoanRepository();
        if (repo.findById(id) != null) {
            repo.delete(id);
        } else {
            throw new Exception("EmployeeLoan not found");
        }
    }

    public static List<EmployeeLoan> findAll() throws Exception {
        return  new EmployeeLoanRepository().findAll();
    }

    public static EmployeeLoan findById(int id) throws Exception {
        return new EmployeeLoanRepository().findById(id);
    }

    public static List<EmployeeLoan> findByEmployeeId(int employeeId) throws Exception {
        return new EmployeeLoanRepository().findByEmployeeId(employeeId);
    }
}
