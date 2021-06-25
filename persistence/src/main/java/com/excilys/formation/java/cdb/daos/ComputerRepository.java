package com.excilys.formation.java.cdb.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.cdb.models.Company;
import com.excilys.formation.java.cdb.models.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    /**
     * Delete associated manufacturer.
     * @param id company's id
     */
    @Transactional
    void deleteByManufacturer(Company id);

    /**
     * Counts all computers with name matching given filter.
     * @param filter name to search
     * @return numbers of computers
     */
    int countByNameContaining(String filter);

    /**
     * Retrieve a Page containing all computers with name matching given filter.
     * @param name to filter by
     * @param pageable object with sort direction and order
     * @return a Page of computers
     */
    Page<Computer> findAllByNameContaining(String name, Pageable pageable);
}
