package entity.internship;

public enum InternshipLevel {
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