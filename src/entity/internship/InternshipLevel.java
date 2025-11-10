package entity.internship;

import java.io.Serializable;

public enum InternshipLevel implements Serializable {
	BASIC,
	INTERMEDIATE,
	ADVANCED;
    public boolean isEligible(int year){
        return switch (this){
            case BASIC -> true;
            case INTERMEDIATE, ADVANCED -> year >=3;
        };
    }
}