package com.company.centralmonitoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Central Monitoring Service.
 */
@SpringBootApplication
public class CentralMonitoringServiceApplication {

  /**
   * The main method that serves as the entry point of the application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(CentralMonitoringServiceApplication.class, args);
  }
}