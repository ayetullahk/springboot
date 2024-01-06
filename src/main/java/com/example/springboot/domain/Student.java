package com.example.springboot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//wrapper class kullanmamızın sebebi eger değer atanmaz ise null olarak atansın
    //int olarak tanımlasaydık default olarak 0 değeri verilecekti

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "Last name can not be white space")
    @Size(min = 2, max = 25, message = "First name '${validatedValue}' must be between {min} and {max} long ")
    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column
    private Integer grade;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "provide valid email")
    private String email;

    @Column
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> books=new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
