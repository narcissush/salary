package salary.model.services;

import salary.model.entity.LoanType;
import salary.model.repository.LoanTypeRepository;

import java.util.List;

public class LoanTypeService {

    public static void save(LoanType loanType) throws Exception {
        try (LoanTypeRepository repository = new LoanTypeRepository()) {
            repository.save(loanType);
        }
    }

    public static void edit(LoanType loanType) throws Exception {
        try (LoanTypeRepository repository = new LoanTypeRepository()) {
            if (repository.findById(loanType.getId()) != null) {
                repository.edit(loanType);
            } else {
                throw new Exception("LoanType not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (LoanTypeRepository repository = new LoanTypeRepository()) {
            if (repository.findById(id) != null) {
                repository.delete(id);
            } else {
                throw new Exception("LoanType not found");
            }
        }
    }

    public static List<LoanType> findAll() throws Exception {
        try (LoanTypeRepository repository = new LoanTypeRepository()) {
            return repository.findAll();
        }
    }

    public static LoanType findById(int id) throws Exception {
        try (LoanTypeRepository repository = new LoanTypeRepository()) {
            return repository.findById(id);
        }
    }

    public static LoanType findByType(String type) throws Exception {
        try (LoanTypeRepository repository = new LoanTypeRepository()) {
            return repository.findByType(type);
        }
    }
}
