package salary.model.services;

import salary.model.entity.EmploymentContract;
import salary.model.repository.EmploymentContractRepository;

import java.util.List;

public class EmploymentContractService {

    private static final EmploymentContractRepository repository = new EmploymentContractRepository();

    public static void save(EmploymentContract contract) throws Exception {
        repository.save(contract);
    }

    public static void edit(EmploymentContract contract) throws Exception {
        if (repository.findById(contract.getId()) != null) {
            repository.edit(contract);
        } else {
            throw new Exception("Employment contract not found");
        }
    }

    public static void delete(int id) throws Exception {
        if (repository.findById(id) != null) {
            repository.delete(id);
        } else {
            throw new Exception("Employment contract not found");
        }
    }

    public static List<EmploymentContract> findAll() throws Exception {
        return repository.findAll();
    }

    public static EmploymentContract findById(int id) throws Exception {
        return repository.findById(id);
    }

    public static List<EmploymentContract> findByEmployeeId(int employeeId) throws Exception {
        return repository.findByEmployeeId(employeeId);
    }
}
