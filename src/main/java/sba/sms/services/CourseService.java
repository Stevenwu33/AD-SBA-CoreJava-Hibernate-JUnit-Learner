package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */


public class CourseService implements CourseI{

    @Override
    public void createCourse(Course course) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
        }

    }

    @Override
    public Course getCourseById(int courseId) {
       try (Session session = HibernateUtil.getSessionFactory().openSession()){
//           return session.get(Course.class,courseId)   --this will return only the Course , but we want students t00!
           return session.createQuery(
                           "SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.courseId = :id", Course.class)
                   .setParameter("id", courseId)
                   .uniqueResult();
       }
    }

    @Override
    public List<Course> getAllCourses() {
            try(Session session = HibernateUtil.getSessionFactory().openSession()){
               return session.createQuery("FROM Course", Course.class).list();
            }
    }
}
