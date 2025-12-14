package dev.rabauer.jooq.example.db;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;

import java.util.Optional;

import static dev.rabauer.jooq.example.tables.School.SCHOOL;

@ApplicationScoped
public class SchoolRepository {

    private final DSLContext dsl;

    public SchoolRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(String name) {
        return dsl.insertInto(SCHOOL)
            .set(SCHOOL.NAME, name)
            .returning(SCHOOL.ID)
            .fetchOne()
            .getId();
    }

    public Optional<School> findById(Long id) {
        return dsl.selectFrom(SCHOOL)
            .where(SCHOOL.ID.eq(id))
            .fetchOptionalInto(School.class);
    }
}
