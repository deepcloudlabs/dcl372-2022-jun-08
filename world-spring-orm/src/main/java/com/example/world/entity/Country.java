package com.example.world.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@NamedQueries({ 
  @NamedQuery(
        name = "AllFromCountry", 
        query = "select c from Country c"
  ),
  @NamedQuery(
        name = "ByContinentFromCountry", 
        query = "select c from Country c where c.continent=:continent"
  ) 
})
@NamedEntityGraphs({
  @NamedEntityGraph(
        name = "graph.Country.cities", 
        attributeNodes = @NamedAttributeNode(value = "cities", subgraph = "cities") , 
        subgraphs = @NamedSubgraph(
               name = "cities", 
               attributeNodes = @NamedAttributeNode("country") 
        ) 
  ),
  @NamedEntityGraph(
        name = "graph.Country.citylangs", 
        attributeNodes =  
           { 
             @NamedAttributeNode(value = "cities", subgraph = "cities") ,
             @NamedAttributeNode(value = "languages", subgraph = "languages")              
           },
        subgraphs = {
             @NamedSubgraph(
                    name = "cities", 
                    attributeNodes = @NamedAttributeNode("country")
             ), 
             @NamedSubgraph(
                    name = "languages", 
                    attributeNodes = @NamedAttributeNode("country")
             ), 
        }                   
  ),
  @NamedEntityGraph(
        name = "graph.Country.languages", 
        attributeNodes = @NamedAttributeNode(value = "languages", subgraph = "languages") , 
        subgraphs = @NamedSubgraph(
                name = "languages", 
                attributeNodes = @NamedAttributeNode("country") 
        ) 
  ) 
})
@Data
public class Country implements Serializable {
 @Id
 private String code;
 private String name;
 private int population;
 @Column(name = "surfacearea")
 private double surfaceArea;
 private String continent;

 @JoinColumn(name = "capital", nullable = false, updatable = false, insertable = false)
 @OneToOne(cascade={CascadeType.MERGE})
 private City capital;

 @OneToMany(mappedBy = "country",orphanRemoval=true)
 private Set<City> cities;

 @OneToMany(mappedBy = "country",orphanRemoval=true)
 private Set<CountryLanguage> languages;


}
