package salary.model.services;

import salary.model.entity.Loan;
import salary.model.repository.LoanRepository;

import java.util.Collections;
import java.util.List;

public class LoanService implements Service<Loan> {

    @Override
    public void save(Loan loan) throws Exception {
        try (LoanRepository loanRepository = new LoanRepository()) {
            loanRepository.save(loan);
        }
    }

    @Override
    public void edit(Loan loan) throws Exception {
        try (LoanRepository loanRepository = new LoanRepository()) {
            if (loanRepository.findById(loan.getId()) != null) {
                loanRepository.edit(loan);
            } else {
                throw new Exception("Loan not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (LoanRepository loanRepository = new LoanRepository()) {
            if (loanRepository.findById(id) != null) {
                loanRepository.delete(id);
            } else {
                throw new Exception("Loan not found");
            }
        }
    }

    @Override
    public List<Loan> findAll() throws Exception {
        try (LoanRepository loanRepository = new LoanRepository()) {
            return loanRepository.findAll();
        }
    }

    @Override
    public Loan findById(int id) throws Exception {
        try (LoanRepository loanRepository = new LoanRepository()) {
            return loanRepository.findById(id);
        }     }

    @Override
    public void close() throws Exception {

    }
}
