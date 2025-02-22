# Mondelez Snacks Insights

Mondelez Insights is a Java-based backend application designed to provide insights into snack consumption by users. The application uses Spring Boot for configuration and dependency management, Jdbi for database interactions, and various other libraries for security, data processing, and API documentation.

### Prerequisites

- Java 17
- PostgreSQL
- Docker
- Gradle

### Installation

1. Clone the repository:
    ```sh
    git clone git@github.com:OjaswiniN/mondelez-snack-insights.git
    cd mondelez-snack-insights
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

3. Set up the PostgreSQL database using Docker:
    ```sh
    docker run --name mondelez_insights -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=mondelez_insights -p 5432:5432 postgres:12-alpine
    ```
    > OR
    
    ```sh
    docker-compose up
    ```

### Running the Application

1. Start the application:
    ```sh
    ./gradlew bootRun
    ```

2. The application will be available at [http://localhost:8080]