# Central Monitoring Service

## Overview

The Central Monitoring Service receives sensor data from Kafka and monitors environmental conditions based on predefined thresholds. It activates alarms if the thresholds are exceeded.

## Prerequisites

1. **Java 17 or higher**: Ensure that you have Java Development Kit (JDK) 17 or a later version installed on your machine.

2. **Apache Kafka**: Ensure that Kafka is running. You can use the same Kafka instance as used for the Warehouse Service.

3. **Maven 3.9.6 or higher**: For building the project make sure you have installed the maven.

4. **Docker (Optional)**: For running Kafka and Zookeeper via Docker if you prefer not to install them directly.

## Setup

1. **Clone the Repository**:

    ```sh
    git clone https://github.com/your-repo/central-monitoring-service.git
    cd central-monitoring-service
    ```

2. **Install Dependencies**:

   For Maven:

    ```sh
    mvn clean install
    ```


3. **Configure Kafka**:

   Ensure that Kafka is running on `localhost:9092`. You can use Docker as described in the Warehouse Service README.

## Running the Service Locally

1. **Start the Service**:

   For Maven:

    ```sh
    mvn spring-boot:run
    ```

2. **Verify**:

   Check the application logs to verify that sensor data is being received and processed correctly. You should see log entries for alarms triggered by exceeding thresholds.

## Common Issues

- **Kafka Connection Issues**: Ensure Kafka is running and configured correctly. Check the logs for any errors related to Kafka connectivity.
- **Logging**: Ensure that logging is configured correctly to capture and display alarm messages.