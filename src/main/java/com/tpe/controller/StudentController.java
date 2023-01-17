package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //Restfull API kullanip Control yaptigimiz icin Controller yerine RestController yazdik
@RequestMapping ("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping //http://localhost:8080/students + GET
    public ResponseEntity<List<Student>> getAll() { //ResponseEntity ile status code gönderimini sagliyoruz.

        List<Student> students = studentService.getAll();

        return ResponseEntity.ok(students); //200 kodunu HTTP status kodu olarak gönderir.
    }

    //Student objesi olusturalim
    @PostMapping ////http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        //olusturdugumuz objeyi json a dönüstüren anatation @RequestBody.
        //domain deki student class'in olusturdugumuz kontrolleri burada gecerli kilmak icin @Valid anatation kullaniyoruz.
        // @Valid : parametreler valid mi kontrol eder, bu örenekte Student
        //objesi oluşturmak için  gönderilen fieldlar yani
        //name gibi özellikler düzgün set edilmiş mi ona bakar.
        // @RequestBody = gelen parametreyi, requestin bodysindeki bilgisi ,
        //Student objesine map edilmesini sağlıyor.
        studentService.createStudent(student);
        Map<String,String> map = new HashMap<>();
        map.put("message","Student is created successfuly");
        map.put("status" ,"true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);  // 201
    }

    //id ile ögrenci görelim with @Requestparam //birden fazla data alinacaksa @Requestparam, 1 data alinacaksa @PathVariable
    @GetMapping("/query") //http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id) {
        Student student = studentService.findStudent(id);

        return ResponseEntity.ok(student);
    }

    //id ile ögrenci görelim with @PathVariable
    @GetMapping("{id}") //http://localhost:8080/students/1 + GET
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //!!! Delete
    @DeleteMapping("/{id}") //http://localhost:8080/students/1 + DELETE
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id){

        studentService.deleteStudent(id);
        Map<String,String> map = new HashMap<>();
        map.put("message","Student is deleted successfuly");
        map.put("status" ,"true");
        return new ResponseEntity<>(map, HttpStatus.OK); //return ResponseEntity.ok(student); ayni
    }

    //!!!Update
    @PutMapping("{id}") //http://localhost:8080/students/1 + PUT
    public ResponseEntity<Map<String, String>> updateStudent(@PathVariable("id") Long id,
                                                             @Valid @RequestBody StudentDTO studentDTO){
                                                            //REquestBody Json data icin kullaniliyor.
                                                            //Bu sekilde id yi ayri diger datalari json ile gönderdik
        studentService.updateStudent(id,studentDTO);
        Map<String,String> map = new HashMap<>();
        map.put("message","Student is update successfuly");
        map.put("status" ,"true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //!!!Pageable
    @GetMapping("/page") //http://localhost:8080/students/page?page=0&size=2&sort=firstName&direction=ASC
                        //Burada yaptigimiz cok fazla datayi tek sayfada degil farkli sayfalara bölerek
                        // almamizi saglayan getMapping islemi
    public ResponseEntity<Page<Student>> getAllWithPage (
            @RequestParam("page") int page, //hangi page gönderilecek ... 0 dan basliyor
            @RequestParam("size") int size, //page basi kac student  alinacak
            @RequestParam("sort") String prop, //siralama hangi fielda göre yapilacak
            @RequestParam("direction") Sort.Direction direction) { //dogal sirali mi olsun?

        Pageable  pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentPage = studentService.getAllWithPage(pageable);

        return ResponseEntity.ok(studentPage);

    }

    @GetMapping("/querylastname") //http://localhost:8080/students/querylastname?lastName=Bey
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName){

        List<Student> list = studentService.findStudent(lastName);

        return ResponseEntity.ok(list);

    }


    //!!! Get All Student By grade (JPQL) Java persistence (kalici) Query Language
    @GetMapping("/grade/{grade}") //http://localhost:8080/students/grade/75
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable ("grade") Integer grade) {

        List<Student> list = studentService.findAllEqualsGrade(grade);

        return ResponseEntity.ok(list);
    }

    //!!! DB den direk DTO olarak data alabilir miyim?
    @GetMapping("/query/dto") //http://localhost:8080/students/query/dto?id=1
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id){

     StudentDTO studentDTO = studentService.findStudentDTOById(id);

     return ResponseEntity.ok(studentDTO);
    }



}
