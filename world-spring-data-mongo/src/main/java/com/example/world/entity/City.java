package com.example.world.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class City {
 @Field("_id")
 private int id;
 private String name;
 private Integer population;

}