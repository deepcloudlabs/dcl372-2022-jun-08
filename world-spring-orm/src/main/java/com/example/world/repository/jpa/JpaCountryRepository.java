package com.example.world.repository.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@Repository
public class JpaCountryRepository implements CountryRepository {
	private final EntityManager entityManager;
	
	public JpaCountryRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Optional<Country> findOneByCode(String code) {
		return Optional.ofNullable(entityManager.find(Country.class, code));
	}

	@Override
	public List<Country> findAll(int pageNo, int pageSize) {
		return entityManager.createNamedQuery("AllFromCountry", Country.class)
				            .setFirstResult(pageNo*pageSize)
				            .setMaxResults(pageSize)
				            .getResultList();
	}

	@Override
	@Transactional
	public void insert(Country country) {
		entityManager.persist(country);
	}

	@Override
	@Transactional
	public void update(Country country) {
		var managedCountry= entityManager.find(Country.class, country.getCode());
		if (Objects.nonNull(managedCountry)) {
			managedCountry.setPopulation(country.getPopulation());
			managedCountry.setSurfaceArea(country.getSurfaceArea());
			// entityManager.merge(managedCountry);
			// entityManager.flush();
		}
	}

	@Override
	@Transactional
	public Country deleteOneByCode(String code) {
		var managedCountry= entityManager.find(Country.class, code);
		if (Objects.isNull(managedCountry))
			throw new IllegalArgumentException("Cannot find country to delete.");
		entityManager.remove(managedCountry);
		return managedCountry;
	}

	@Override
	public List<String> getContinents() {
		return entityManager.createNativeQuery("select distinct continent from country")
				.getResultList();
	}

	@Override
	public List<Country> getCountriesByContinent(String continent) {
		return entityManager.createNamedQuery("ByContinentFromCountry", Country.class)
							.setParameter("continent", continent)
				            .getResultList();
	}

}
