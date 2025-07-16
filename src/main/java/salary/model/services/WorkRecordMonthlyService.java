package salary.model.services;

import salary.model.entity.WorkRecordMonthly;
import salary.model.repository.UserRepository;
import salary.model.repository.WorkRecordMonthlyRepository;

import java.util.Collections;
import java.util.List;

public class WorkRecordMonthlyService {

    public static void save(WorkRecordMonthly workRecordMonthly) throws Exception {
        try (WorkRecordMonthlyRepository workRecordMonthlyRepository = new WorkRecordMonthlyRepository()) {
            WorkRecordMonthlyService.save(workRecordMonthly);
        }
    }

    public static void edit(WorkRecordMonthly workRecordMonthly) throws Exception {
        try (WorkRecordMonthlyRepository workRecordMonthlyRepository = new WorkRecordMonthlyRepository()) {
            if (workRecordMonthlyRepository.findById(workRecordMonthly.getId()) != null) {
                workRecordMonthlyRepository.edit(workRecordMonthly);
            } else {
                throw new Exception("User not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (WorkRecordMonthlyRepository workRecordMonthlyRepository = new WorkRecordMonthlyRepository()) {
            if (workRecordMonthlyRepository.findById(id) != null) {
                workRecordMonthlyRepository.delete(id);
            } else {
                throw new Exception("User not found");
            }
        }
    }

    public static List<WorkRecordMonthly> findAll() throws Exception {
        try (WorkRecordMonthlyRepository workRecordMonthlyRepository = new WorkRecordMonthlyRepository()) {
            return workRecordMonthlyRepository.findAll();
        }    }

    public static WorkRecordMonthly findById(int id) throws Exception {
        try (WorkRecordMonthlyRepository workRecordMonthlyRepository = new WorkRecordMonthlyRepository()) {
            return workRecordMonthlyRepository.findById(id);
        }    }


}
