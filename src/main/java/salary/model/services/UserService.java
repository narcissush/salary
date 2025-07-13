package salary.model.services;

import salary.model.entity.User;
import salary.model.repository.UserRepository;

import java.util.List;

public class UserService {

    public static void save(User user) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            userRepository.save(user);
        }
    }

    public static void edit(User user) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            if (userRepository.findById(user.getId()) != null) {
                userRepository.edit(user);
            } else {
                throw new Exception("User not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            if (userRepository.findById(id) != null) {
                userRepository.delete(id);
            } else {
                throw new Exception("User not found");
            }
        }
    }

    public static List<User> findAll() throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findAll();
        }
    }

    public static User findById(int id) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findById(id);
        }
    }

    public static User findByUserAndPassword(String username,String password) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findByUserAndPassword(username,password);
        }
    }
}
