package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter //Lombok tool'u sayesinde gette ve setter'lari bizim yerimize olusturuyor. O yüzden bizim olusturmamiza gerek kalmiyor
@Setter
@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_student")
public class Student {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    //@Getter variable seviyesinde de bu anatation kullanilabiliyor
    @Setter(AccessLevel.NONE)//bu anatation class seviyesinde yazdigimiz icin olusacak tüm setler icerisinde
    //bu value icin set edilemez olarak ayarlayabiliyoruz.
    private Long id;

    @NotNull (message = "First name can not be null")
    @NotBlank(message = "First name can not be white space")
    @Size(min=2, max=25, message =
            "First name '${validateValue}' must be between {min} and {max} long")
    @Column(nullable = false, length = 25)
    /*final*/ private String firstName; //final yazdik cünkü yukarida kullandigimiz RequiredArgsCont. anatation icin
                                    // belirtec koymus oluyoruz ki; bu final ile isaretlenenlerle cont. olusturuyor.

    @NotNull
    @Column(nullable = false, length = 25)
    /*final*/ private String lastName;


    /*final*/ private Integer grade;

    @Column(nullable = false,length = 50, unique = true)
    @Email(message = "Provide valid email")
    /*final*/ private String email;

    /*final*/ private String phoneNumber;

    @Setter(AccessLevel.NONE) //bu anatation class seviyesinde yazdigimiz icin olusacak tüm setler icerisinde
                                //bu value icin set edilemez olarak ayarlayabiliyoruz.
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();

    @JoinColumn(name="user_id")
    @OneToOne
    private User user;

}
