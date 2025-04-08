package com.zenveus.student_service.service;

import com.zenveus.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import com.zenveus.student_service.model.Student;

import com.zenveus.student_service.dto.School;
import com.zenveus.student_service.dto.StudentResponse;



import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;


    public ResponseEntity<?> createStudent(Student student){
        try{

            School school = restTemplate.getForObject("http://localhost:8082/school/" + student.getSchoolId(), School.class);


            if(school == null){
                return new ResponseEntity<>("School Not Found", HttpStatus.NOT_FOUND);
            }
            if(student.getName() == null || student.getName().isEmpty()){
                return new ResponseEntity<>("Student Name is required", HttpStatus.BAD_REQUEST);
            }
            if(student.getAge() <= 0){
                return new ResponseEntity<>("Student Age is required", HttpStatus.BAD_REQUEST);
            }


            return new ResponseEntity<Student>(studentRepository.save(student), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> fetchStudentById(String id){
        Optional<Student> student =  studentRepository.findById(id);
        if(student.isPresent()){
            School school = restTemplate.getForObject("http://localhost:8082/school/" + student.get().getSchoolId(), School.class);
            StudentResponse studentResponse = new StudentResponse(
                    student.get().getId(),
                    student.get().getName(),
                    student.get().getAge(),
                    student.get().getGender(),
                    school
            );
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Student Found",HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> fetchStudents(){
        List<Student> students = studentRepository.findAll();
        if(students.size() > 0){
            return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Students",HttpStatus.NOT_FOUND);
        }
    }
}
