package salary.model.services;

import salary.model.entity.User;

import java.util.Collections;
import java.util.List;

public class UserService implements Service<User> {

    @Override
    public void save(User user) throws Exception {

    }

    @Override
    public void edit(User user) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<User> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public User findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
