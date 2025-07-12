package salary.model.services;

import salary.model.entity.LoanItem;
import salary.model.repository.LoanItemsRepository;

import java.util.List;

public class LoanItemsService implements Service<LoanItem> {


    @Override
    public void save(LoanItem loanItem) throws Exception {
        try (LoanItemsRepository loanItemsRepository = new LoanItemsRepository()) {
            loanItemsRepository.save(loanItem);
        }
    }

    @Override
    public void edit(LoanItem loanItem) throws Exception {
        try (LoanItemsRepository loanItemsRepository = new LoanItemsRepository()) {
            if (loanItemsRepository.findById(loanItem.getId()) != null) {
                loanItemsRepository.edit(loanItem);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (LoanItemsRepository loanItemsRepository = new LoanItemsRepository()) {
            if (loanItemsRepository.findById(id) != null) {
                loanItemsRepository.delete(id);
            } else {
                throw new Exception("LoanItem not found");
            }
        }
    }

    @Override
    public List<LoanItem> findAll() throws Exception {
        try (LoanItemsRepository loanItemsRepository = new LoanItemsRepository()) {
            return loanItemsRepository.findAll();
        }
    }

    @Override
    public LoanItem findById(int id) throws Exception {
        try (LoanItemsRepository loanItemsRepository = new LoanItemsRepository()) {
            return loanItemsRepository.findById(id);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
