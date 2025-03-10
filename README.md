# PTBOX OSINT Backend

Backend part of the project for collecting and analyzing OSINT data. Built with Spring Boot, Docker, PostgreSQL, and the Amass tool.

---

## Table of Contents
1. [Project Description](#project-description)
2. [Technologies](#technologies)
3. [How to Run the Project](#how-to-run-the-project)
   - [Prerequisites](#prerequisites)
   - [Steps to Run](#steps-to-run)
4. [Frontend Repository](#frontend-repository)

---

## Project Description

This project is the backend for collecting OSINT data using the Amass tool. It provides an API for running scans, storing results in a PostgreSQL database, and interacting with the frontend.

---

## Technologies

- **Spring Boot** (Kotlin)
- **Docker** (containerization)
- **PostgreSQL** (database)
- **Amass** (OSINT data collection)



## How to Run the Project

### Prerequisites

Before running, ensure you have installed:
- Docker
- Docker Compose

### Steps to Run

1. **Clone the repository**:
   - **Note:** Before running docker-compose, make sure to set the correct relative path to your frontend's Dockerfile in the docker-compose.yml file
   ```bash
   git clone https://github.com/your-username/ptbox-osint-backend.git
   cd ptbox-osint-backend
   docker-compose up --build
   ```
   
   ---
   
   ### Frontend repository:
   - https://github.com/QwertySudo666/ptbox-osint-fe
