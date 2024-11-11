This is the demo containing

- Java Spring backend, that supports user registration and login, and task creation for authorized users
- Basic frontend for testing

This demo uses Postgres database that can be put up automatically with the help of a Docker container.

To spin up the docker containers, type the following commands in the terminal from project root directory:

1. cd demo-backend
2. mvn package
3. cd ..
4. docker-compose up --build

The root directory also contains a Postman collection for testing basic endpoints.
