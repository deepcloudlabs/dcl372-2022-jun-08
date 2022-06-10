package com.example.world.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanCharacterConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {
		return value ? "T" : "F";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		return "T".equals(value);
	}

}
