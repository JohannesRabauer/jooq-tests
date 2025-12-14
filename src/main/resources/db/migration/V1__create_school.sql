create table teacher (
    id bigint generated always as identity primary key,
    name varchar(255) not null,
    email varchar(255) unique not null,
    created_at timestamp not null default now()
);

create table school (
    id bigint generated always as identity primary key,
    name varchar(255) not null,
    created_at timestamp not null default now()
);

create table school_class (
    id bigint generated always as identity primary key,
    name varchar(255) not null,
    school_id bigint not null,
    teacher_id bigint not null,
    created_at timestamp not null default now(),
    constraint fk_school_class_school
        foreign key (school_id)
        references school(id),
    constraint fk_school_class_teacher
        foreign key (teacher_id)
        references teacher(id)
);

create table student (
    id bigint generated always as identity primary key,
    name varchar(255) not null,
    school_class_id bigint not null,
    created_at timestamp not null default now(),
    constraint fk_student_school_class
        foreign key (school_class_id)
        references school_class(id)
);

create index idx_school_class_school_id on school_class(school_id);
create index idx_school_class_teacher_id on school_class(teacher_id);
create index idx_student_school_class_id on student(school_class_id);
