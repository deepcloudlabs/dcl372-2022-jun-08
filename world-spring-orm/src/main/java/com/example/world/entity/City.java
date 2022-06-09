package com.example.world.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@NamedQueries({
 @NamedQuery(name="fromCity.all",query="select c from City c"),
 @NamedQuery(name="fromCity.byCountry",query="select c from City c where c.country.code=:code")
})
@Data
public class City {
 @Id
 private int id;
 private String name;
 private Integer population;

 @JoinColumn(name = "countrycode", nullable = true,insertable=false,updatable=false)
 @OneToOne(fetch = FetchType.LAZY)
 private Country country;


}