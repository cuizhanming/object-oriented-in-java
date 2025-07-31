package com.cuizhanming.oop.behavioral;

/**
 * Template Method Pattern - Defines skeleton of algorithm, subclasses fill in details
 * 模板方法模式 - 定义算法骨架，子类填充细节
 */
public class TemplateMethodPattern {

    // Abstract class defining template method
    public abstract static class DataProcessor {

        // Template method - defines the algorithm skeleton
        public final void processData() {
            readData();
            if (isValidData()) {
                transformData();
                saveData();
                cleanup();
            } else {
                handleInvalidData();
            }
        }

        // Abstract methods - must be implemented by subclasses
        protected abstract void readData();
        protected abstract void transformData();
        protected abstract void saveData();

        // Hook methods - can be overridden by subclasses
        protected boolean isValidData() {
            return true; // Default implementation
        }

        protected void cleanup() {
            System.out.println("Default cleanup: Releasing resources");
        }

        protected void handleInvalidData() {
            System.out.println("Default error handling: Invalid data detected");
        }
    }

    // Concrete implementations
    public static class CSVDataProcessor extends DataProcessor {
        private String csvData;

        @Override
        protected void readData() {
            csvData = "name,age,city\nJohn,25,NYC\nJane,30,LA";
            System.out.println("Reading CSV data from file");
        }

        @Override
        protected void transformData() {
            System.out.println("Converting CSV to JSON format");
            // Simulate CSV to JSON transformation
        }

        @Override
        protected void saveData() {
            System.out.println("Saving JSON data to database");
        }

        @Override
        protected boolean isValidData() {
            boolean isValid = csvData != null && csvData.contains(",");
            System.out.println("CSV validation: " + (isValid ? "Valid" : "Invalid"));
            return isValid;
        }

        @Override
        protected void cleanup() {
            System.out.println("CSV cleanup: Closing file streams");
            super.cleanup();
        }
    }

    public static class XMLDataProcessor extends DataProcessor {
        private String xmlData;

        @Override
        protected void readData() {
            xmlData = "<users><user><name>John</name><age>25</age></user></users>";
            System.out.println("Reading XML data from web service");
        }

        @Override
        protected void transformData() {
            System.out.println("Parsing XML and converting to object model");
            // Simulate XML parsing
        }

        @Override
        protected void saveData() {
            System.out.println("Saving objects to cache");
        }

        @Override
        protected boolean isValidData() {
            boolean isValid = xmlData != null && xmlData.startsWith("<");
            System.out.println("XML validation: " + (isValid ? "Valid" : "Invalid"));
            return isValid;
        }

        @Override
        protected void cleanup() {
            System.out.println("XML cleanup: Closing HTTP connections");
            super.cleanup();
        }
    }

    public static class DatabaseDataProcessor extends DataProcessor {
        private boolean connectionSuccess = true;

        @Override
        protected void readData() {
            System.out.println("Reading data from database query");
        }

        @Override
        protected void transformData() {
            System.out.println("Applying business rules transformation");
        }

        @Override
        protected void saveData() {
            System.out.println("Saving processed data to data warehouse");
        }

        @Override
        protected boolean isValidData() {
            System.out.println("Database validation: " + (connectionSuccess ? "Valid" : "Invalid"));
            return connectionSuccess;
        }

        @Override
        protected void handleInvalidData() {
            System.out.println("Database error handling: Retrying connection");
        }

        public void simulateConnectionFailure() {
            this.connectionSuccess = false;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Template Method Pattern Demo ===");

        DataProcessor[] processors = {
            new CSVDataProcessor(),
            new XMLDataProcessor(),
            new DatabaseDataProcessor()
        };

        // Process data using different implementations
        for (int i = 0; i < processors.length; i++) {
            System.out.println("\n--- Processing with " +
                processors[i].getClass().getSimpleName() + " ---");
            processors[i].processData();
        }

        // Demonstrate error handling
        System.out.println("\n--- Demonstrating Error Handling ---");
        DatabaseDataProcessor dbProcessor = new DatabaseDataProcessor();
        dbProcessor.simulateConnectionFailure();
        dbProcessor.processData();
    }
}
