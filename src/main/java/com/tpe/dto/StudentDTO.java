package com.tpe.dto;


import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
//Lombok tool'u sayesinde gette ve setter'lari bizim yerimize olusturuyor. O yüzden bizim olusturmamiza gerek kalmiyor
@Setter
@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor

public class StudentDTO { //bu class güvenlik islemini yapmamizi sagliyor.


    private Long id;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be white space")
    @Size(min=2, max=25, message =
            "First name '${validateValue}' must be between {min} and {max} long")

     private String firstName; //final yazdik cünkü yukarida kullandigimiz RequiredArgsCont. anatation icin
    // belirtec koymus oluyoruz ki; bu final ile isaretlenenlerle cont. olusturuyor.

    @NotNull
     private String lastName;


     private Integer grade;

    @Email(message = "Provide valid email")
    private String email;

    private String phoneNumber;

    private LocalDateTime createDate = LocalDateTime.now();

    public StudentDTO (Student student) { //StudentDTO std1 = new StudentDTO(student)
        this.id=student.getId();
        this.firstName=student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.grade = student.getGrade();
        this.phoneNumber = student.getPhoneNumber();
        this.createDate = student.getCreateDate();
    }
}
