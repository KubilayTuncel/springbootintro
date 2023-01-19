package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor
public class Book { //one student many book

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName") //JSON cikti da isminin "bookName" olmasini sagladik.
    private String name;

    @JsonIgnore //sonsuz döngüye girilmesin diye json ignore eklendi. Dallanma student tarafindan geldigi icin buraya yazdik.
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Student getStudent() {
        return student;
    }
}
