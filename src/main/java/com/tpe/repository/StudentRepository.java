package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByEmail(String email);
    //************************Bu aciklama cok önemli***********************
    // Spring Data JPA içinde existById() var fakat Spring Data JPA bize sondaki eki istediğimiz
// değişken ismi ile değiştirmemize izin veriyor, mevcut metodu bu şekilde türetebiliyoruz.
    List<Student> findByLastName(String lastName);


    //!!! JPQL ********************************
    @Query("select s from Student s where s.grade=:pGrade")  //Student -->s burada pGrade i biz istedigimiz gibi yaziyoruz.
    //yukarida degisken atarken : yi kullaniyoruz esitlikten sonra
    //Query icerisinde parametreyi asagida @Param anatation ile pGrade olarak setledim.
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    //Native SQL
    @Query(value = "select * from t_student s where s.grade=:pGrade", nativeQuery = true)
    List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);

    //Bu ksim önemli cünkü gölge objeyi(studentDTO) JPQL ile direk uzun path yolunu göstererek
    // DB den gelen gercek objeye setleyebiliyoruz.
    @Query("select new com.tpe.dto.StudentDTO(s) from Student s where s.id=:id")
    Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);
}
