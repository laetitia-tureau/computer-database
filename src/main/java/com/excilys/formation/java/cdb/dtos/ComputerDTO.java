package com.excilys.formation.java.cdb.dtos;

/** Represents a computer.
 * @author Laetitia Tureau
 */
public class ComputerDTO {

    private String id;
    private String name;
    private String introduced;
    private String discontinued;

    /** Represents the computer's company.
     */
    private String manufacturer;

    /** Creates a computer using a ComputerBuilder.
     * @param builder A ComputerBuilder
     */
    public ComputerDTO(ComputerBuilderDTO builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.manufacturer = builder.manufacturer;
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
     * Gets the computer's company..
     * @return this.manufacturer
     */
    public String getManufacturer() {
        return this.manufacturer;
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

        private final String id;
        private final String name;
        private String introduced;
        private String discontinued;

        /** Represents the computer's company.
         */
        private String manufacturer;

        /** Creates a ComputerBuilder with the specified id.
         * @param builderName The computer's name.
         */
        public ComputerBuilderDTO(String builderName) {
            this.id = null;
            this.name = builderName;
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
         * Create a Computer from the ComputerBuilder.
         * @return computer resulting
         */
        public ComputerDTO build() {
            ComputerDTO computer = new ComputerDTO(this);
            return computer;
        }
    }
}
