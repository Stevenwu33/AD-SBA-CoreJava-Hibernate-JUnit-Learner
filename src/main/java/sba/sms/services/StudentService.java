package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

@Log
public class StudentService implements StudentI {


    @Override
    public List<Student> getAllStudents() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from Student", Student.class).list();
        }


    }

    @Override
    public void createStudent(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        }

    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                            "SELECT s FROM Student s LEFT JOIN FETCH s.courses  WHERE s.email = :email", Student.class)
                    .setParameter("email",email)
                    .uniqueResult();
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, email);
            if (student != null && student.getPassword().equals(password)){
                return true;
            }
        }
            return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            if (student != null && !student.getCourses().contains(course)){
                student.getCourses().add(course);
                session.merge(student);
            }
            transaction.commit();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                    "SELECT c FROM Student s JOIN s.courses c WHERE s.email = :email", Course.class)
                    .setParameter("email",email)
                    .list();
        }
    }
}
