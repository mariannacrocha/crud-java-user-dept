package com.desafio.userdept.repositories;

import com.desafio.userdept.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndEmailAndDepartmentId(String name, String email, Long department_id);


}
