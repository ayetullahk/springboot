package com.example.springboot.dto;

import com.example.springboot.domain.Student;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {

    private Long id;//wrapper class kullanmamızın sebebi eger değer atanmaz ise null olarak atansın
    //int olarak tanımlasaydık default olarak 0 değeri verilecekti

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "Last name can not be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long ")
    private String firstName;

    private String lastName;

    private Integer grade;

    @Email(message = "provide valid email")
    private String email;

    private String phoneNumber;

    private LocalDateTime createDate = LocalDateTime.now();

    public StudentDTO(Student student){
        this.id=student.getId();
        this.firstName=student.getName();
        this.lastName=student.getLastName();
        this.grade=student.getGrade();
        this.email=student.getEmail();
        this.phoneNumber=student.getPhoneNumber();
        this.createDate=student.getCreateDate();
    }


}
