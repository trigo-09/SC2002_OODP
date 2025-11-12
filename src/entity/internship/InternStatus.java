package entity.internship;

import java.io.Serializable;

/**
 * internship status
 */
public enum InternStatus implements Serializable {
	PENDING, // waiting for staff approval
	APPROVED, // staff approved
	REJECTED, // staff rejected
	FILLED // Application slot filled up
}