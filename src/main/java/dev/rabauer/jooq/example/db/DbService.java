package dev.rabauer.jooq.example.db;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

@ApplicationScoped
public class DbService {

    private DSLContext dsl;

    public void create(long schoolId)
    {
        dsl.transaction(configuration -> {
            DSLContext tx = DSL.using(configuration);

            TeacherRepository teacherRepo = new TeacherRepository(tx);
            SchoolClassRepository classRepo = new SchoolClassRepository(tx);

            Long teacherId = teacherRepo.create("Alice", "alice@school.org");
            classRepo.create("Math 101", schoolId, teacherId);
        });
    }

}
