package com.excilys.formation.java.cdb.persistence.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.cdb.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
