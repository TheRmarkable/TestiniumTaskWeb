Web Automation Framework

Project Overview

This repository contains the automation framework for the Web version of the Money Transfer application. The framework is built using Selenium, TestNG, and Java, with a focus on scalability and maintainability.

Features

Page Object Model (POM): Organized structure for web element locators and actions.

Data-Driven Testing: Externalized test data using JSON/Excel files.

Cross-Browser Testing: Support for Chrome, Firefox, and Safari.

Reporting: Integrated with ExtentReports for detailed execution reports.

Reusable Utilities: Includes browser setup, logging, and screenshot capture.

Prerequisites

Java JDK 11 or later

Maven (Build Tool)

Git: Installed and configured.

Setup Instructions

Clone this repository:

git clone <repository-url>

Navigate to the project directory:

cd web-automation-framework

Install dependencies:

mvn install

Update the config.properties file in the src/test/resources directory with the following details:

Base URL

Browser type (e.g., Chrome, Firefox)

Test data file path

Running Tests

To run all tests:

mvn test

To execute tests for a specific tag:

Reports will be generated in the target/reports folder.

Folder Structure

src/main/java: Contains the core framework code.

src/test/java: Contains test classes and test data.

src/test/resources: Includes configuration files and test data files.


Troubleshooting

Dependency Errors:

Run mvn clean install to resolve dependency issues.

Configuration Issues:

Verify the config.properties file has correct values.



License

This project is licensed under the MIT License.

