package dev.rabauer.jooq.example.db;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

import static dev.rabauer.jooq.example.tables.Teacher.TEACHER;

@ApplicationScoped
public class TeacherRepository {

    private final DSLContext dsl;

    public TeacherRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(String name, String email) {
        return dsl.insertInto(TEACHER)
            .set(TEACHER.NAME, name)
            .set(TEACHER.EMAIL, email)
            .returning(TEACHER.ID)
            .fetchOne()
            .getId();
    }

    public Optional<Teacher> findById(Long id) {
        return dsl.selectFrom(TEACHER)
            .where(TEACHER.ID.eq(id))
            .fetchOptionalInto(Teacher.class);
    }

    public List<Teacher> findAll() {
        return dsl.selectFrom(TEACHER)
            .fetchInto(Teacher.class);
    }
}
