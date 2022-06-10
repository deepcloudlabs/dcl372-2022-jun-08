package com.example.world.entity;

import java.util.List;

import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.validation.ISO3;

import lombok.Data;

@Document(collection = "countries1")
@Data
public class Country {
 @Id
 @ISO3
 private String code;
 private String name;
 @PositiveOrZero
 private int population;
 @Field(name = "surfacearea")
 @PositiveOrZero
 private double surfaceArea;
 private String continent;
 private List<City> cities;

}
