package cours.ulaval.glo4003.persistence;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cours.ulaval.glo4003.model.CoursesPool;
import cours.ulaval.glo4003.repository.ICourseRetriever;
import cours.ulaval.glo4003.utils.ResourcesLoader;
import cours.ulaval.glo4003.utils.ResourcesNames;

public class CourseBeanRetriever implements ICourseRetriever {

	@Autowired
	@Qualifier("ResourcesLoader")
	private ResourcesLoader resourcesLoader;
	private Unmarshaller unmarshaller;

	public CourseBeanRetriever() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(CoursesPool.class);
		unmarshaller = context.createUnmarshaller();
	}

	@Override
	public CoursesPool getCourses() throws JAXBException {
		InputStream stream = resourcesLoader.loadResource(
				CourseBeanRetriever.class, ResourcesNames.COURSES_FILE);
		CoursesPool courses = (CoursesPool) unmarshaller.unmarshal(stream);

		return courses;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public void setResourcesLoader(ResourcesLoader loader) {
		this.resourcesLoader = loader;
	}
}
