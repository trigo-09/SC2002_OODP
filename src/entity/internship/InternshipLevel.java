package entity.internship;

import java.io.Serializable;

/**
 * Represents the level of an internship.
 * This enumeration defines three levels: BASIC, INTERMEDIATE, and ADVANCED.
 */
public enum InternshipLevel implements Serializable {
	BASIC,
	INTERMEDIATE,
	ADVANCED;

    /**
     * checks if students year matches require internship level
     * @param year year of student
     * @return boolean
     */
    public boolean isEligible(int year){
        return switch (this){
            case BASIC -> true;
            case INTERMEDIATE, ADVANCED -> year >=3;
        };
    }
}