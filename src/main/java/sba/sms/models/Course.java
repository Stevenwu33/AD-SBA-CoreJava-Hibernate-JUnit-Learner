package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseID")
    private int courseId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Instructor")
    private String instructor;

    @ManyToMany(mappedBy = "courses",
                cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST},
                fetch = FetchType.LAZY )  //See what this means on notes maven page 30
    private Set<Student> students = new HashSet<>();

    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;

    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor)
                && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, name, instructor, students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + courseId +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                ", students=" + students +
                '}';
    }
}
