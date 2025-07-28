package salary.model.services;

import salary.model.entity.EmploymentContract;
import salary.model.repository.EmployeeLoanRepository;
import salary.model.repository.EmploymentContractRepository;

import java.util.List;

public class EmploymentContractService {


    public static void save(EmploymentContract contract) throws Exception {
        try (EmploymentContractRepository repository = new EmploymentContractRepository()) {
            repository.save(contract);
        }
    }

    public static void edit(EmploymentContract contract) throws Exception {
        try (EmploymentContractRepository repository = new EmploymentContractRepository()) {

            if (repository.findById(contract.getId()) != null) {
                repository.edit(contract);
            } else {
                throw new Exception("Employment contract not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (EmploymentContractRepository repository = new EmploymentContractRepository()) {

            if (repository.findById(id) != null) {
                repository.delete(id);
            } else {
                throw new Exception("Employment contract not found");
            }
        }
    }

    public static List<EmploymentContract> findAll() throws Exception {
        try (EmploymentContractRepository repository = new EmploymentContractRepository()) {

            return repository.findAll();
        }
    }

    public static EmploymentContract findById(int id) throws Exception {
        try (EmploymentContractRepository repository = new EmploymentContractRepository()) {

            return repository.findById(id);
        }
    }

    public static List<EmploymentContract> findByEmployeeId(int employeeId) throws Exception {
        try (EmploymentContractRepository repository = new EmploymentContractRepository()) {

            return repository.findByEmployeeId(employeeId);
        }
    }
}

