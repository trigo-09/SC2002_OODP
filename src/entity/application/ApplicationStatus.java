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
    WITHDRAWN // student withdraw application
}