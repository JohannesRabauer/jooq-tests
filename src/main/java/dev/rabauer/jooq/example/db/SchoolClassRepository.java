package dev.rabauer.jooq.example.db;

import jakarta.enterprise.context.ApplicationScoped;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;

import static dev.rabauer.jooq.example.tables.SchoolClass.SCHOOL_CLASS;
import static dev.rabauer.jooq.example.tables.Student.STUDENT;

@ApplicationScoped
public class SchoolClassRepository {

    private final DSLContext dsl;

    public SchoolClassRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(String name, Long schoolId, Long teacherId) {
        return dsl.insertInto(SCHOOL_CLASS)
            .set(SCHOOL_CLASS.NAME, name)
            .set(SCHOOL_CLASS.SCHOOL_ID, schoolId)
            .set(SCHOOL_CLASS.TEACHER_ID, teacherId)
            .returning(SCHOOL_CLASS.ID)
            .fetchOne()
            .getId();
    }

    public List<SchoolClass> findByTeacher(Long teacherId) {
        return dsl.selectFrom(SCHOOL_CLASS)
            .where(SCHOOL_CLASS.TEACHER_ID.eq(teacherId))
            .fetchInto(SchoolClass.class);
    }

    public List<SchoolClass> findBySchool(Long schoolId) {
        return dsl.selectFrom(SCHOOL_CLASS)
            .where(SCHOOL_CLASS.SCHOOL_ID.eq(schoolId))
            .fetchInto(SchoolClass.class);
    }

    public Map<SchoolClass, List<Student>> fetchTeacherClassesWithStudents(Long teacherId) {
        return dsl.select()
                .from(SCHOOL_CLASS)
                .join(STUDENT).on(STUDENT.SCHOOL_CLASS_ID.eq(SCHOOL_CLASS.ID))
                .where(SCHOOL_CLASS.TEACHER_ID.eq(teacherId))
                .fetchGroups(
                        r -> r.into(SCHOOL_CLASS).into(SchoolClass.class),
                        r -> r.into(STUDENT).into(Student.class)
                );
    }

}
