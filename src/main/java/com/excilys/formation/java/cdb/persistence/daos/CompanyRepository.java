package com.excilys.formation.java.cdb.persistence.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.java.cdb.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
