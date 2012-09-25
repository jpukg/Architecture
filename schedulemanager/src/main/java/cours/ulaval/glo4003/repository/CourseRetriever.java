package cours.ulaval.glo4003.repository;

import org.springframework.stereotype.Component;

import cours.ulaval.glo4003.model.CoursesPool;

@Component
public interface CourseRetriever {

	CoursesPool getCourses() throws Exception;
}