package com.excilys.formation.java.cdb.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
