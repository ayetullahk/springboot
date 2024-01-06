package com.example.springboot.domain;

import com.example.springboot.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "tbl_role")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)// tabloya enum int değeri ile değil string ifade ile kaydedilmesi için
    @Column(length = 30,nullable = false)
    private UserRole name;

    @Override
    public String toString() {
        return "Role[name="+name+"]";
    }
}
