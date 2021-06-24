package com.excilys.formation.java.cdb.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    /**
     * Find all computers matching given name.
     * @param itemName name to search
     * @return matching computers
     */
    List<Computer> findByNameContainingIgnoreCase(String itemName);

    /**
     * Delete associated manufacturer.
     * @param id company's id
     */
    @Transactional
    void deleteByManufacturer(Company id);

}
