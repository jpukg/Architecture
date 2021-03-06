package cours.ulaval.glo4003.domain.conflictdetection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cours.ulaval.glo4003.domain.Schedule;
import cours.ulaval.glo4003.domain.Section;
import cours.ulaval.glo4003.domain.conflictdetection.conflict.Conflict;

public class ConflictDetector extends Source {
	@Inject
	private ConcomittingCoursesFilter concomittingCoursesFilter;
	@Inject
	private CourseLevelFilter courseLevelFilter;
	@Inject
	private SameTeacherFilter sameTeacherFilter;
	@Inject
	private UnavailableTeacherFilter unavailableTeacherFilter;
	private Sink sink = new Sink();

	public void detectConflict(Schedule schedule) {
		List<Conflict> conflicts = run(schedule);
		schedule.addAll(conflicts);
		schedule.calculateScore();
	}

	public void detectConflictForSection(Schedule schedule, Section section) {
		List<Conflict> conflicts = run(schedule, section);
		schedule.addAll(conflicts);
		schedule.calculateScore();
	}

	public boolean willSectionGenerateConflict(Schedule schedule, Section section) {
		List<Conflict> conflicts = run(schedule, section);
		return !conflicts.isEmpty();
	}

	private List<Conflict> run(Schedule schedule) {
		connectFilters();
		return startPipe(schedule);
	}

	private List<Conflict> run(Schedule schedule, Section section) {
		connectFilters();
		return startPipe(schedule, section);
	}

	private void connectFilters() {
		this.connectToFilter(concomittingCoursesFilter);
		concomittingCoursesFilter.connectToFilter(courseLevelFilter);
		courseLevelFilter.connectToFilter(sameTeacherFilter);
		sameTeacherFilter.connectToFilter(unavailableTeacherFilter);
		unavailableTeacherFilter.connectToFilter(sink);
	}

	private List<Conflict> startPipe(Schedule schedule) {
		List<Conflict> conflicts = new ArrayList<Conflict>();
		if (firstFilter != null) {
			conflicts.addAll(firstFilter.run(schedule));
		}
		return conflicts;
	}

	private List<Conflict> startPipe(Schedule schedule, Section section) {
		List<Conflict> conflicts = new ArrayList<Conflict>();
		if (firstFilter != null) {
			conflicts.addAll(firstFilter.run(schedule, section));
		}
		return conflicts;
	}

	// WARNING, DO NOT USE - the following methods are for tests only
	public void setConcomittingCoursesFilter(ConcomittingCoursesFilter concomittingCoursesFilter) {
		this.concomittingCoursesFilter = concomittingCoursesFilter;
	}

	public void setCourseLevelFilter(CourseLevelFilter courseLevelFilter) {
		this.courseLevelFilter = courseLevelFilter;
	}

	public void setSameTeacherFilter(SameTeacherFilter sameTeacherFilter) {
		this.sameTeacherFilter = sameTeacherFilter;
	}

	public void setUnavailableTeacherFilter(UnavailableTeacherFilter unavailableTeacherFilter) {
		this.unavailableTeacherFilter = unavailableTeacherFilter;
	}

	public void setSink(Sink sink) {
		this.sink = sink;
	}

}
