package entity.application;

public enum ApplicationStatus {
    PENDING, //once created
    APPROVED, // application accepte by rep
    REJECTED, // application rejected by rep
    ACCEPTED, // application accepted by rep and student
    WITHDRAWN // student withdraw application
}