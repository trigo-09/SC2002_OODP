package entity.internship;

import java.io.Serializable;

public enum InternshipLevel implements Serializable {
	BASIC,
	INTERMEDIATE,
	ADVANCED;

    /**
     * checks if students year matches require internship level
     * @param year
     * @return boolean
     */
    public boolean isEligible(int year){
        return switch (this){
            case BASIC -> true;
            case INTERMEDIATE, ADVANCED -> year >=3;
        };
    }
}