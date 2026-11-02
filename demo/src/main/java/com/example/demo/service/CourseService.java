package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * The service contains the application's course rules.
 *
 * Controllers receive HTTP requests.
 * Services perform the work.
 * Repositories store and retrieve data.
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course createCourse(Course course) {
        course.setId(null);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course existingCourse = courseRepository.findById(id).orElse(null);

        if (existingCourse == null) {
            return null;
        }

        existingCourse.setTitle(courseDetails.getTitle());
        existingCourse.setDescription(courseDetails.getDescription());
        return courseRepository.save(existingCourse);
    }

    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);
        return true;
    }
}