package com.excilys.formation.java.cdb.persistence.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.models.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    List<Computer> findByNameContainingIgnoreCase(String itemName);

}
