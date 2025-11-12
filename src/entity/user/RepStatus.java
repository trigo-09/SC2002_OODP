package entity.user;

import java.io.Serializable;

/**
 * account state of company representative
 */
public enum RepStatus implements Serializable {
	PENDING,
	REGISTERED,
    REJECTED
}