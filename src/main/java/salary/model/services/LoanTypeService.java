package salary.model.services;

import salary.model.entity.LoanType;
import salary.model.repository.LoanTypeRepository;

import java.util.List;

public class LoanTypeService {

    public static void save(LoanType loanType) throws Exception {
        new LoanTypeRepository().save(loanType);
    }

    public static void edit(LoanType loanType) throws Exception {
        LoanTypeRepository repo = new LoanTypeRepository();
        if (repo.findById(loanType.getId()) != null) {
            repo.edit(loanType);
        } else {
            throw new Exception("LoanType not found");
        }
    }

    public static void delete(int id) throws Exception {
        LoanTypeRepository repo = new LoanTypeRepository();
        if (repo.findById(id) != null) {
            repo.delete(id);
        } else {
            throw new Exception("LoanType not found");
        }
    }

    public static List<LoanType> findAll() throws Exception {
        return new LoanTypeRepository().findAll();
    }

    public static LoanType findById(int id) throws Exception {
        return new LoanTypeRepository().findById(id);
    }
    public static LoanType findByType(String type) throws Exception {
        return new LoanTypeRepository().findByType(type);
    }
}
