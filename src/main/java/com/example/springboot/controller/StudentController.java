package com.example.springboot.controller;


import com.example.springboot.domain.Student;
import com.example.springboot.dto.StudentDTO;
import com.example.springboot.service.StudentService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    /*
        @GetMapping("/welcome")//localhost:8080/student/welcome
        public String welcome() {
            return "Welcome to Student controller";
        }
    */
    @GetMapping("/welcome")//localhost:8080/student/welcome
    public String welcome(HttpServletRequest request) {
        logger.warn("------------Welcome{}",request.getServletPath());
        return "Welcome to Student controller";
    }

    //get all student
    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Student>> getAll() {
        //@RequestBody gelen parametreyi reguestin bodysindeki bilgisi
        //student objesine map edilmesini sağlıyor
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    //create new student
    @PostMapping
    @PreAuthorize("hasRole('Student')")
    public ResponseEntity<Map<String, String>> createStudent(@Valid @RequestBody Student student) {
        //@Valid parametreler valid mi kontrol eder bu örnekte student
        //objesini oluşturmak için gönderilen fieldlar yani name gibi
        //özellikler düzgün test edilmişmi ona bakar
        studentService.createStudent(student);
        Map<String, String> map = new HashMap<>();
        map.put("message", "student is created successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    //get a student by ID ReguestParam
    @GetMapping("/query")
    @PreAuthorize("hasRole('Admin') or hasRole('Student')")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //get a student by ID by PathVariable
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') or hasRole('Student')")
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "student is deleted successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //Update student, Dto kullanılacaktır
    @PutMapping("{id}")
    public ResponseEntity<Map<String, String>> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(id, studentDTO);
        Map<String, String> map = new HashMap<>();
        map.put("message", "student is update successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //pageable
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam("direction") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, prop));
        Page<Student> studentPage = studentService.getAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);
    }

    //get by Lastname
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName) {
        List<Student> list = studentService.findStudent(lastName);
        return ResponseEntity.ok(list);
    }

    //Get All students By grade (JPQL)
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Student>> getStudentEqualsGrade(@PathVariable("grade") Integer grade) {
        List<Student> list = studentService.findAllEqualsGrade(grade);
        return ResponseEntity.ok(list);
    }

    // DB den direkt DTO olarak data alma
    @GetMapping("query/dto")
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id) {
        StudentDTO studentDTO = studentService.findStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }

}
