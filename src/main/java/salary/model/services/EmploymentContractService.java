package salary.model.services;

import salary.model.entity.EmploymentContract;
import salary.model.repository.EmploymentContractRepositiry;

import java.util.List;

public class EmploymentContractService  {

    public static void save(EmploymentContract employmentContract) throws Exception {
        try (EmploymentContractRepositiry employmentContractRepositiry = new EmploymentContractRepositiry()) {
            employmentContractRepositiry.save(employmentContract);
        }
    }

    public static void edit(EmploymentContract employmentContract) throws Exception {
        try (EmploymentContractRepositiry employmentContractRepositiry = new EmploymentContractRepositiry()) {
            if (employmentContractRepositiry.findById(employmentContract.getId()) != null) {
                employmentContractRepositiry.edit(employmentContract);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (EmploymentContractRepositiry employmentContractRepositiry = new EmploymentContractRepositiry()) {
            if (employmentContractRepositiry.findById(id) != null) {
                employmentContractRepositiry.delete(id);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    public static List<EmploymentContract> findAll() throws Exception {
        try (EmploymentContractRepositiry employmentContractRepositiry = new EmploymentContractRepositiry()) {
            return employmentContractRepositiry.findAll();
        }
    }

    public static EmploymentContract findById(int id) throws Exception {
        try (EmploymentContractRepositiry employmentContractRepositiry = new EmploymentContractRepositiry()) {
            return employmentContractRepositiry.findById(id);
        }
    }


}
