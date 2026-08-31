package cl.prgm.portaltrip.domain.model;

import java.math.BigDecimal;

public enum TripType {

	EXPRESS("express", new BigDecimal("1")),
	EXPLORATION("exploration", new BigDecimal("1.3")),
	PREMIUM("premium", new BigDecimal("1.65"));

	private final String code;
	private final BigDecimal multiplier;

	TripType(String code, BigDecimal multiplier) {
		this.code = code;
		this.multiplier = multiplier;
	}

	public String code() {
		return code;
	}

	public BigDecimal multiplier() {
		return multiplier;
	}

	public static TripType fromCode(String code) {
		for (TripType type : values()) {
			if (type.code.equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown trip type: " + code);
	}

}
