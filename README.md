# jooq-first-touch

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.

## Database

```mermaid
erDiagram
TEACHER ||--o{ SCHOOL_CLASS : teaches
SCHOOL  ||--o{ SCHOOL_CLASS : contains
SCHOOL_CLASS ||--o{ STUDENT : has

    TEACHER {
        bigint id PK
        varchar name
        varchar email
        timestamp created_at
    }

    SCHOOL {
        bigint id PK
        varchar name
        timestamp created_at
    }

    SCHOOL_CLASS {
        bigint id PK
        varchar name
        bigint school_id FK
        bigint teacher_id FK
        timestamp created_at
    }

    STUDENT {
        bigint id PK
        varchar name
        bigint school_class_id FK
        timestamp created_at
    }
```