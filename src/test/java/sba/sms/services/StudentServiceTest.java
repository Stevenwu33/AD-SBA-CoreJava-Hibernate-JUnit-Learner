package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;
import sba.sms.utils.HibernateUtil;

import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StudentServiceTest {

    private StudentService studentService;
    private CourseService courseService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    private static String email;

    @BeforeEach
    public void setup(){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        studentService = new StudentService();  // Instantiate your service
        courseService = new CourseService();

    }

    @AfterEach
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


    @Test
    void getAllStudents() {

        Student student = new Student("test@email.com", "Test Student", "password123");
        studentService.createStudent(student);
        List<Student> students = studentService.getAllStudents();
        assertNotNull(students);
        assertTrue(!students.isEmpty());
    }

    @Test
    void createStudent() {

        Student student = new Student();
        student.setName("Steven");
        student.setEmail("steven22@gmail.com");

        studentService.createStudent(student);
        assertNotNull(student.getEmail());

    }

    @Test
    void getStudentByEmail() {
    }

    @Test
    void validateStudent() {
    }

    @Test
    void registerStudentToCourse() {
    }

    @Test
    void getStudentCourses() {
    }
}