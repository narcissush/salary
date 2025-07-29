package salary.model.services;

import salary.model.entity.Payslip;
import salary.model.repository.EmployeePayslipRepository;

import java.util.List;

public class EmployeePaysLipService {

    public static void save(Payslip payslip) throws Exception {
        try (EmployeePayslipRepository repository = new EmployeePayslipRepository()) {
            repository.save(payslip);
        }
    }

    public static void edit(Payslip payslip) throws Exception {
        try (EmployeePayslipRepository repository = new EmployeePayslipRepository()) {
            if (repository.findById(payslip.getId()) != null) {
                repository.edit(payslip);
            } else {
                throw new Exception("Payslip not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (EmployeePayslipRepository repository = new EmployeePayslipRepository()) {
            if (repository.findById(id) != null) {
                repository.delete(id);
            } else {
                throw new Exception("Payslip not found");
            }
        }
    }

    public static List<Payslip> findAll() throws Exception {
        try (EmployeePayslipRepository employeePayslipRepository = new EmployeePayslipRepository()) {
            return employeePayslipRepository.findAll();
        }
    }

    public static Payslip findById(int id) throws Exception {
        try (EmployeePayslipRepository employeePayslipRepository = new EmployeePayslipRepository()) {
            return employeePayslipRepository.findById(id);
        }
    }

}
