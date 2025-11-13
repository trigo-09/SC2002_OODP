package entity.application;

import java.io.Serializable;

/**
 * Application status for tracking
 */
public enum ApplicationStatus implements Serializable {
    PENDING, //once created
    APPROVED, // application accepted by rep
    REJECTED, // application rejected by rep
    ACCEPTED, // application accepted by rep and student
    WITHDRAWN; // student withdraw application

    public String coloredString() {
        return switch(this){
            case PENDING -> "\u001B[33m" + this + "\u001B[0m";
            case APPROVED -> "\u001B[32m" + this + "\u001B[0m";
            case REJECTED -> "\u001B[31m" + this + "\u001B[0m";
            case ACCEPTED -> "\u001B[32m" + this + "\u001B[0m";
            case WITHDRAWN -> "\u001B[31m" + this + "\u001B[0m";
        };
    }
}