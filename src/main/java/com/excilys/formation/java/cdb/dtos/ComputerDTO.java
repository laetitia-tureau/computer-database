package com.excilys.formation.java.cdb.dtos;

/** Represents a computer.
 * @author Laetitia Tureau
 */
public class ComputerDTO {

    private String id;
    private String name;
    private String introduced;
    private String discontinued;

    /** Represents the computer's company.*/
    private String manufacturer;
    private String companyName;

    /** Creates a computer using a ComputerBuilder.
     * @param builder A ComputerBuilder
     */
    public ComputerDTO(ComputerBuilderDTO builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.manufacturer = builder.manufacturer;
        this.companyName = builder.companyName;
    }

    /** Gets the computer's id.
     * @return this.id
     */
    public String getId() {
        return this.id;
    }

    /** Gets the computer's name.
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /** Gets the computer's introduced date.
     * @return this.introduced
     */
    public String getIntroduced() {
        return this.introduced;
    }

    /** Gets the computer's discontinued date.
     * @return this.discontinued
     */
    public String getDiscontinued() {
        return this.discontinued;
    }

    /**
     * Gets the computer's company.
     * @return this.manufacturer
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * Gets the computer's company.
     * @return this.manufacturer
     */
    public String getCompanyName() {
        return this.companyName;
    }

    @Override
    public String toString() {
        String str = this.id + "\t|\t" + this.name + "\t|\t";
        str += introduced != null ? this.introduced + "\t|\t" : "NULL\t|\t";
        str += discontinued != null ? this.discontinued + "\t|\t" : "NULL\t|\t";
        str += manufacturer != null ? this.manufacturer + "\t|\t" : "NULL\t|\t";
        return str;
    }

    /**
     * Implementation of Builder pattern that allows to create a computer.
     * @author Laetitia Tureau
     */
    public static class ComputerBuilderDTO {

        private String id;
        private String name;
        private String introduced;
        private String discontinued;
        /** Represents the computer's company.*/
        private String manufacturer;
        private String companyName;

        /** Creates a ComputerBuilder.*/
        public ComputerBuilderDTO() {
        }

        /** Creates a ComputerBuilder with specific id and name.
         * @param builderID The computer's id.
         * @param builderName The computer's name.
         */
        public ComputerBuilderDTO(String builderID, String builderName) {
            this.id = builderID;
            this.name = builderName;
        }

        /**
         * Initialize attribute id of the ComputerBuilderDTO.
         * @param builderId A String id
         * @return this
         */
        public ComputerBuilderDTO id(String builderId) {
            this.id = builderId;
            return this;
        }

        /**
         * Initialize attribute name of the ComputerBuilderDTO.
         * @param builderName A String name
         * @return this
         */
        public ComputerBuilderDTO name(String builderName) {
            this.name = builderName;
            return this;
        }

        /**
         * Initialize attribute introduced of the ComputerBuilder.
         * @param introducedBuilder A LocalDate
         * @return this
         */
        public ComputerBuilderDTO introduced(String introducedBuilder) {
            this.introduced = introducedBuilder;
            return this;
        }

        /**
         * Initialize attribute discontinued of the ComputerBuilder.
         * @param discontinuedBuilder A LocalDate
         * @return this
         */
        public ComputerBuilderDTO discontinued(String discontinuedBuilder) {
            this.discontinued = discontinuedBuilder;
            return this;
        }

        /**
         * Initialize attribute manufacturer of the ComputerBuilder.
         * @param manufacturerBuilder A Company
         * @return this
         */
        public ComputerBuilderDTO manufacturer(String manufacturerBuilder) {
            this.manufacturer = manufacturerBuilder;
            return this;
        }

        /**
         * Initialize attribute companyName of the ComputerBuilder.
         * @param companyNameBuilder A Company
         * @return this
         */
        public ComputerBuilderDTO companyName(String companyNameBuilder) {
            this.companyName = companyNameBuilder;
            return this;
        }

        /**
         * Create a Computer from the ComputerBuilder.
         * @return computer resulting
         */
        public ComputerDTO build() {
            ComputerDTO computer = new ComputerDTO(this);
            return computer;
        }
    }
}
