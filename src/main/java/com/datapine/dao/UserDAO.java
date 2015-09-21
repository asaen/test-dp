package com.datapine.dao;

import com.datapine.domain.User;
import java.util.Iterator;

public interface UserDAO {

    void save(User user);

    User update(User user);

    void delete(User user);

    User findById(Long id);

    User findByEmail(String email);

    Iterator<User> findAllOrderById();

}
