package dev.rabauer.jooq.example.db;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;

import java.util.List;

import static dev.rabauer.jooq.example.tables.Student.STUDENT;

@ApplicationScoped
public class StudentRepository {

    private final DSLContext dsl;

    public StudentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(String name, Long classId) {
        return dsl.insertInto(STUDENT)
            .set(STUDENT.NAME, name)
            .set(STUDENT.SCHOOL_CLASS_ID, classId)
            .returning(STUDENT.ID)
            .fetchOne()
            .getId();
    }

    public List<Student> findByClass(Long classId) {
        return dsl.selectFrom(STUDENT)
            .where(STUDENT.SCHOOL_CLASS_ID.eq(classId))
            .fetchInto(Student.class);
    }
}
