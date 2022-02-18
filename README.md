# IoT-PoC

This repository contains a Proof of Concept application for an IoT use case.
The idea is that a temperature sensor periodically sends temperature data to a service which stores it in a database.
An API is provided, which supports various calculations on the stored temperature data like retrieving the average temperature over a specified time period.

The following components are part of this application:

- Time series optimized database
- Web API for storing and accessing the data
- Mocked temperature sensor to provide data

## Setup instructions

To run this Software, you need to follow these steps:

1. Make sure that you have docker and docker-compose installed
2. Run `docker-compose up -d db` in the root directory of this repository
3. Open your browser and navigate to `http://localhost:8086`
4. Create a user, organization and bucket. The organization and bucket should be called "iot". This can be changed in the .env file
5. Navigate to Data > API Tokens and create a new "All Access API Token"
6. Copy the token to the `INFLUXDB_TOKEN` property in the .env file
7. Navigate to the backend directory and execute `mvn spring-boot:build-image`
8. In the root directory, run `docker-compose up -d`