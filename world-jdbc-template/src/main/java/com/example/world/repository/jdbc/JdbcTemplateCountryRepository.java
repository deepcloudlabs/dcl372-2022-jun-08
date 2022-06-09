package com.example.world.repository.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@Repository
public class JdbcTemplateCountryRepository implements CountryRepository {
    private static final String SELECT_COUNTRY_BY_CODE = """ 
    		SELECT CODE,NAME,POPULATION,SURFACEAREA,CONTINENT
    		FROM COUNTRY
    		WHERE CODE=:code 
    		""";
    private static final String SELECT_COUNTRIES_BY_PAGE = """ 
    		SELECT CODE,NAME,POPULATION,SURFACEAREA,CONTINENT
    		FROM COUNTRY
    		LIMIT :offset,:size 
    		""";
	private static final String INSERT_INTO_COUNTRY = """
			INSERT INTO COUNTRY (CODE,CONTINENT,NAME,POPULATION,SURFACEAREA)
			VALUES (:code,:continent,:name,:population,:surfaceArea)
			""";
	private static final String UPDATE_COUNTRY = """
			UPDATE COUNTRY 
			SET NAME=:name, POPULATION=:population, SURFACEAREA=:surfaceArea
			WHERE CODE=:code
			""";
	private static final String DELETE_COUNTRY_BY_CODE = """
			DELETE FROM COUNTRY
			WHERE CODE=:code
			""";
	private static final String DELETE_CITY_BY_COUNTRY_CODE = """
			DELETE FROM CITY
			WHERE COUNTRYCODE=:code
			""";
	private static final String DELETE_COUNTRY_LANGUAGE_BY_COUNTRY_CODE = """
			DELETE FROM COUNTRYLANGUAGE
			WHERE COUNTRYCODE=:code
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
		return jdbcTemplate.query(
				SELECT_COUNTRIES_BY_PAGE,
				Map.of(
				   "offset", pageNo*pageSize,
				   "size" , pageSize
				)
				,
				COUNTRY_ROW_MAPPER
			);
	}

	@Override
	@Transactional
	public void insert(Country country) {
		jdbcTemplate.update(INSERT_INTO_COUNTRY, 
		  Map.of(
			"code", country.getCode(),
			"name", country.getName(),
			"continent", country.getContinent(),
			"population", country.getPopulation(),
			"surfaceArea", country.getSurfaceArea()
		  )
		);
	}

	@Override
	@Transactional
	public void update(Country country) {
		jdbcTemplate.update(UPDATE_COUNTRY, 
				  Map.of(
					"code", country.getCode(),
					"name", country.getName(),
					"population", country.getPopulation(),
					"surfaceArea", country.getSurfaceArea()
				  )
				);
	}

	@Override
	@Transactional
	public Country deleteOneByCode(String code) {
		final Optional<Country> country = findOneByCode(code);
		if (country.isPresent()) {
			jdbcTemplate.update(DELETE_CITY_BY_COUNTRY_CODE, Map.of("code",code));
			jdbcTemplate.update(DELETE_COUNTRY_LANGUAGE_BY_COUNTRY_CODE, Map.of("code",code));
			final int numOfRemoved = jdbcTemplate.update(DELETE_COUNTRY_BY_CODE, Map.of("code",code));
			if (numOfRemoved > 0) {
				return country.get();
			}
		}
		throw new IllegalArgumentException("Cannot find the country to delete");
	}
}
