package com.example.world.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "code")
@NoArgsConstructor
@AllArgsConstructor
public class Country {
	private String code;
	private String name;
	private long population;
	private double surfaceArea;
	private String continent;

}
