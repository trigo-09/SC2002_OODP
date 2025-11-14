package entity.internship;

import java.io.Serializable;

/**
 * internship status
 */
public enum InternStatus implements Serializable {
	PENDING, // waiting for staff approval
	APPROVED, // staff approved
	REJECTED, // staff rejected
	FILLED; // Application slot filled up

    /**
     * Returns a string current enum instance with color formatting based on its status.
     * Each status is assigned a specific color:
     * - PENDING: Yellow
     * - APPROVED: Green
     * - REJECTED: Red
     * - FILLED: Red
     *
     * @return A colored string representation of the enum instance.
     */
	public String coloredString() {
		return switch(this){
			case PENDING -> "\u001B[33m" + this + "\u001B[0m";
			case APPROVED -> "\u001B[32m" + this + "\u001B[0m";
			case REJECTED -> "\u001B[31m" + this + "\u001B[0m";
			case FILLED -> "\u001B[31m" + this + "\u001B[0m";
		};
	}
}