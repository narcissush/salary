package salary.model.services;

import salary.model.entity.User;
import salary.model.repository.EmployeeRepository;
import salary.model.repository.UserRepository;

import java.util.Collections;
import java.util.List;

public class UserService implements Service<User> {

    @Override
    public void save(User user) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            userRepository.save(user);
        }
    }

    @Override
    public void edit(User user) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            if (userRepository.findById(user.getId()) != null) {
                userRepository.edit(user);
            } else {
                throw new Exception("User not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            if (userRepository.findById(id) != null) {
                userRepository.delete(id);
            } else {
                throw new Exception("User not found");
            }
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findAll();
        }    }

    @Override
    public User findById(int id) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findById(id);
        }    }

    @Override
    public void close() throws Exception {

    }
}
