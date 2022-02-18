# IoT-PoC

This repository contains a Proof of Concept application for an IoT use case.
The idea is that a temperature sensor periodically sends temperature data to a service which stores it in a database.
An API is provided, which supports various calculations on the stored temperature data like retrieving the average temperature over a specified time period.

The following components are part of this application:

- Time series optimized database
- Web API for storing and accessing the data
- Mocked temperature sensor to provide data