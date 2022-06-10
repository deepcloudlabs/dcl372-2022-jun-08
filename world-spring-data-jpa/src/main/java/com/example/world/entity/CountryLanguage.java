package com.example.world.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.world.entity.converter.BooleanCharacterConverter;

import lombok.Data;

@Entity
@Data
public class CountryLanguage {
 @EmbeddedId
 private CountryLanguagePK countryLanguagePK;
 @Column(name = "isOfficial")
 @Convert(converter = BooleanCharacterConverter.class)
 private boolean official;
 private double percentage;

 @OneToOne()
 @JoinColumn(name = "countrycode", insertable = false, updatable = false, nullable = false)
 private Country country;

}