package com.perfios.banking.repository;

import com.perfios.banking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName,String password);
}
