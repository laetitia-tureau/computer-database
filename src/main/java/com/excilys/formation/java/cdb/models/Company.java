package com.excilys.formation.java.cdb.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** Represents a company.
 * @author Laetitia Tureau
 */
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    /** Creates a company using a CompanyBuilder.
     * @param builder A CompanyBuilder
     */
    public Company(CompanyBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
    
    public Company() {
        
    }

    /**
     * Gets the company's id.
     * @return this.id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the company's name.
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.id).append("\t|\t").append(this.name);
        return str.toString();
    }

    /**
     * Implementation of Builder pattern that allows to create a company.
     * @author Laetitia Tureau
     */
    public static class CompanyBuilder {

        private Long id;
        private String name;

        /** Creates a CompanyBuilder.*/
        public CompanyBuilder() {
        }

        /** Creates a CompanyBuilder with specific id and name.
         * @param builderID The company's id.
         * @param builderName The company's name.
         */
        public CompanyBuilder(Long builderID, String builderName) {
            this.id = builderID;
            this.name = builderName;
        }

        /**
         * Initialize attribute id of the CompanyBuilder.
         * @param builderId A Long
         * @return this
         */
        public CompanyBuilder id(Long builderId) {
            this.id = builderId;
            return this;
        }

        /**
         * Initialize attribute name of the CompanyBuilder.
         * @param builderName A String
         * @return this
         */
        public CompanyBuilder name(String builderName) {
            this.name = builderName;
            return this;
        }

        /**
         * Create a Company from the CompanyBuilder.
         * @return computer resulting
         */
        public Company build() {
            Company company = new Company(this);
            return company;
        }
    }
}
