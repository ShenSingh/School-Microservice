package com.zenveus.student_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.zenveus.student_service.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String>{
}
