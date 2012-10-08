package cours.ulaval.glo4003.model;

import java.util.ArrayList;
import java.util.List;

public class Prerequisite {

	private List<String> acronyms = new ArrayList<String>();
	private Boolean isConcomitant = false;

	public List<String> getAcronyms() {
		return acronyms;
	}

	public void setAcronyms(List<String> acronyms) {
		this.acronyms = acronyms;
	}

	public Boolean isConcomitant() {
		return isConcomitant;
	}

	public void setIsConcomitant(Boolean isConcomitant) {
		this.isConcomitant = isConcomitant;
	}
}
