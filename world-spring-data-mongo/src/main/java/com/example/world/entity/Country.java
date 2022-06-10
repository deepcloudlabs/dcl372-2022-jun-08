package com.example.world.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "countries1")
@Data
public class Country {
 @Id
 private String code;
 private String name;
 private int population;
 @Field(name = "surfacearea")
 private double surfaceArea;
 private String continent;
 private List<City> cities;

}
