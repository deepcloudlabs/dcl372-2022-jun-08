package com.example.world.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@SuppressWarnings("serial")
@Embeddable
@Data
public class CountryLanguagePK implements Serializable {
 @Column(nullable=false)
 private String language;
 @Column(name = "countrycode",nullable=false)
 private String code;

}