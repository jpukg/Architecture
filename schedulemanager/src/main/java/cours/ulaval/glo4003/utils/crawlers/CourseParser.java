package cours.ulaval.glo4003.utils.crawlers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cours.ulaval.glo4003.domain.Course;
import cours.ulaval.glo4003.domain.Cycle;
import cours.ulaval.glo4003.domain.Prerequisite;
import cours.ulaval.glo4003.domain.TimeDedicated;

public class CourseParser extends Parser {

	private static final String COURSE_HOURS = "Heures de cours";
	private static final String FULL_TITLE_SELECTOR = "table.datadisplaytable tr td.nttitle";
	private static final String DESCRIPTION_SELECTOR = "table.datadisplaytable tr td.ntdefault";
	private static final String AND_PREREQUISITE_DELIMITER = "ET";

	private String parsedDescription;
	private String parsedTitle;

	private Course course = new Course();

	public CourseParser(String uri) throws Exception {
		super(uri);
		parseDescription();
		parseTitle();
	}

	public boolean courseShouldBeAddedToCourseList() {
		return parsedDescription.contains(COURSE_HOURS);
	}

	public Course getCourse() {
		return course;
	}

	public Course getCompleteCourse() {
		addAcronym();
		addTitle();
		addDescription();
		addCredits();
		addCycle();
		addTimeDedicated();
		addPrerequisites();
		return course;
	}

	public void addAcronym() {
		String acronym = parsedTitle.split(" - ")[0].replace(" ", "-");
		course.setAcronym(acronym);
	}

	public void addTitle() {
		String stripTitle = parsedTitle.split(" - ")[1];
		course.setTitle(stripTitle);
	}

	public void addDescription() {
		String description = parsedDescription.split("<br />")[0];
		description = description.trim();
		course.setDescription(description);
	}

	public void addCredits() {
		String creditsString = parsedDescription.split("<br />")[1].split(",")[0];
		int credits = Integer.valueOf(creditsString.trim());
		if (credits == 0) {
			String creditsORString = parsedDescription.split("<br />")[1].split(",")[1];
			if (creditsORString.contains("OR")) {
				credits = Integer.valueOf(creditsORString.split(" OR ")[1]);
			} else {
				credits = 0;
			}
		}
		course.setCredits(credits);
	}

	public void addCycle() {
		String cycle = parsedDescription.split("<br />")[6].trim().split("</span>")[1].trim().replace(" cycle", "");
		course.setCycle(Cycle.valueOf(cycle));
	}

	public void addTimeDedicated() {
		int courseHours = Integer.valueOf(parsedDescription.split("<br />")[2].trim().split(",")[0]);
		int laboHours = Integer.valueOf(parsedDescription.split("<br />")[3].trim().split(",")[0]);
		int otherHours = Integer.valueOf(parsedDescription.split("<br />")[4].trim().split(",")[0]);

		course.setTimeDedicated(new TimeDedicated(courseHours, laboHours, otherHours));
	}

	public void addPrerequisites() {
		List<String> prerequisitesAcronyms = getPrerequisitesAcronyms();
		List<String> prerequisitesDelimiters = getPrerequisitesDelimiters();

		int delimiterCounter = 0;
		int acronymCounter = 0;
		List<Prerequisite> prerequisites = new ArrayList<Prerequisite>();
		while (acronymCounter < prerequisitesAcronyms.size()) {
			Prerequisite prerequisite = new Prerequisite();
			while (delimiterCounter < prerequisitesDelimiters.size()
					&& !prerequisitesDelimiters.get(delimiterCounter).equals(AND_PREREQUISITE_DELIMITER)) {
				prerequisite.addAcronym(prerequisitesAcronyms.get(acronymCounter));
				delimiterCounter++;
				acronymCounter++;
			}

			delimiterCounter++;
			prerequisite.addAcronym(prerequisitesAcronyms.get(acronymCounter++));
			prerequisites.add(prerequisite);
		}

		course.setPrerequisites(prerequisites);
	}

	private List<String> getPrerequisitesDelimiters() {
		List<String> prerequisitesDelimiters = new ArrayList<String>();
		Elements elements = htmlParser.parse(document, DESCRIPTION_SELECTOR);
		elements = elements.select("b");
		for (Element element : elements) {
			prerequisitesDelimiters.add(element.ownText());
		}

		return prerequisitesDelimiters;
	}

	private List<String> getPrerequisitesAcronyms() {
		List<String> prerequisitesAcronyms = new ArrayList<String>();
		Elements elements = htmlParser.parse(document, DESCRIPTION_SELECTOR);
		elements = elements.select("a");
		for (Element element : elements) {
			if (!element.ownText().contains("Régulier") && !element.ownText().contains("Retour à la page précédente")
					&& !element.ownText().contains("Nouvelle recherche")) {
				prerequisitesAcronyms.add(element.ownText().replace(" ", "-"));
			}
		}

		return prerequisitesAcronyms;
	}

	// For tests only
	public CourseParser(HTMLLoader loader, HTMLParser parser) {
		htmlLoader = loader;
		htmlParser = parser;
	}

	public void parseDescription() {
		parsedDescription = htmlParser.parse(document, DESCRIPTION_SELECTOR).first().html();
	}

	public void parseTitle() {
		parsedTitle = htmlParser.parse(document, FULL_TITLE_SELECTOR).first().ownText();
	}
}
