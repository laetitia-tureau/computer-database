package com.excilys.formation.java.cdb.persistence.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.java.cdb.models.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> {

}
