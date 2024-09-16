## Clickbus API

I created this repository to answer a junior technical test for Clickbus Company, but just to add it into my portfolio.

## Used Technologies

- Spring Boot 3.3.0
- JUnit 5
- TestContainers
- Docker
- Docker Compose
- Swagger
- MySQL

## How To Run It?

First, you will need Docker to run it, you could follow the steps below to make it run.

1. Clone this repository
2. Open the terminal inside the repository folder
3. Run the command `docker image build -t clickbus:1.0 .`
4. Run the command `docker compose up -d`

## Endpoints

You can see all the endpoints with Swagger, you just need to access http://localhost:8080/api/v1/swagger-ui.html

![plot](imgs/Screenshot%202024-09-16%20121210.png)