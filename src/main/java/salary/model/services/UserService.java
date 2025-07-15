package salary.model.services;

import salary.model.entity.User;
import salary.model.repository.UserRepository;
import salary.controller.exceptions.UserNotFoundException;


import java.util.List;
import java.util.Optional;

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
            Optional<User> loginUser = Optional.ofNullable(userRepository.findByUserAndPassword(username, password));
            return loginUser.orElseThrow(UserNotFoundException::new);
        }
    }
}
