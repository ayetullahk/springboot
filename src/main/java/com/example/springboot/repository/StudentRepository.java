package com.example.springboot.repository;

import com.example.springboot.domain.Student;
import com.example.springboot.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByEmail(String email);
    //JPA içinde exsistById() var fakat spring data JPA bize sondaki eki
    //istediğimiz değişken ismi şle değiştirmemize izin veriyor
    List<Student> findByLastName(String lastName);


    //LPQL ile yazalım
    @Query("select s from Student s where s.grade=:pGrade")
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    //native query(sql)
    @Query(value = "select *from student s where s.grade=:pGrade",nativeQuery = true)
    List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);

    @Query("select new com.example.springboot.dto.StudentDTO(s) from Student s where s.id=:id")
    Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);
}
