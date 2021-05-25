package com.excilys.formation.java.cdb.dtos;

/**
 * Represents a company.
 * @author Laetitia Tureau
 */
public class CompanyDTO {

    private String id;
    private String name;

    /**
     * Creates a company using a CompanyBuilderDTO.
     * @param builder A CompanyBuilderDTO
     */
    public CompanyDTO(CompanyBuilderDTO builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    /**
     * Gets the company's id.
     * @return this.id
     */
    public String getId() {
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
    public static class CompanyBuilderDTO {

        private String id;
        private String name;

        /**
         * Creates a CompanyBuilderDTO with the specified id.
         * @param builderName The company's name.
         */
        public CompanyBuilderDTO(String builderName) {
            this.id = null;
            this.name = builderName;
        }

        /**
         * Creates a CompanyBuilderDTO with specific id and name.
         * @param builderID   The company's id.
         * @param builderName The company's name.
         */
        public CompanyBuilderDTO(String builderID, String builderName) {
            this.id = builderID;
            this.name = builderName;
        }

        /**
         * Create a CompanyDTO from the CompanyBuilderDTO.
         * @return computer resulting
         */
        public CompanyDTO build() {
            CompanyDTO companyDTO = new CompanyDTO(this);
            return companyDTO;
        }
    }
}
