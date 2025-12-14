package dev.rabauer.jooq.example.db;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

@ApplicationScoped
public class DbService {

    private final DSLContext dsl;

    public DbService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void create()
    {
        dsl.transaction(configuration -> {
            DSLContext tx = DSL.using(configuration);

            SchoolRepository schoolRepo = new SchoolRepository(tx);
            TeacherRepository teacherRepo = new TeacherRepository(tx);
            SchoolClassRepository classRepo = new SchoolClassRepository(tx);

            long schoolId = schoolRepo.create("MySchool");
            Long teacherId = teacherRepo.create("Alice", "alice@school.org");
            classRepo.create("Math 101", schoolId, teacherId);
        });
    }

}
