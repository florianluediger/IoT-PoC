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
2. Run docker-compose up in the root directory of this repository
3. Open your browser and navigate to http://localhost:8086
4. Create a user and match the credentials to the environment variables in the .env file