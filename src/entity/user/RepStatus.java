package entity.user;

import java.io.Serializable;

/**
 * account state of company representative
 */
public enum RepStatus implements Serializable {
	PENDING,
	REGISTERED,
    REJECTED;

	public String coloredString() {
		return switch(this){
			case PENDING -> "\u001B[33m" + this + "\u001B[0m";
			case REGISTERED -> "\u001B[32m" + this + "\u001B[0m";
			case REJECTED -> "\u001B[31m" + this + "\u001B[0m";
		};
	}
}