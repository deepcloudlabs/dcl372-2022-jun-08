package com.example.world.repository.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@Repository
public class JdbcTemplateCountryRepository implements CountryRepository {
    private static final String SELECT_COUNTRY_BY_CODE = """ 
    		SELECT CODE,NAME,POPULATION,SURFACEAREA,CONTINENT
    		FROM COUNTRY
    		WHERE CODE=:code 
    		""";
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Country> COUNTRY_ROW_MAPPER= (resultSet, index) -> {
		var country = new Country();
		country.setCode(resultSet.getString("CODE"));
		country.setContinent(resultSet.getString("CONTINENT"));
		country.setPopulation(resultSet.getInt("POPULATION"));
		country.setSurfaceArea(resultSet.getDouble("SURFACEAREA"));
		country.setName(resultSet.getString("NAME"));
		return country;
	};
    
	public JdbcTemplateCountryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Country> findOneByCode(String code) {
		return Optional.ofNullable(
				  jdbcTemplate.queryForObject(
				    SELECT_COUNTRY_BY_CODE, // query 
				    Map.of("code", code),    // query parameters
				    new BeanPropertyRowMapper<Country>(Country.class) // ResultSet -> Entity
				  )
				);
	}

	@Override
	public List<Country> findAll(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Country country) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Country country) {
		// TODO Auto-generated method stub

	}

	@Override
	public Country deleteOneByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
