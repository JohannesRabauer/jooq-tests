package dev.rabauer.jooq.example.db;

import java.time.Instant;

public record SchoolClass (
   Long id,
   String name,
   Long schoolId,
   Long teacherId,
   Instant createdAt
){}
