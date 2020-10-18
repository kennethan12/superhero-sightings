/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.dao.LocationDaoDB.LocationMapper;
import com.ka.superherosightings.dao.OrganizationDaoDB.OrganizationMapper;
import com.ka.superherosightings.dao.PowerDaoDB.PowerMapper;
import com.ka.superherosightings.dao.SuperDaoDB.SuperMapper;
import com.ka.superherosightings.entities.Location;
import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Power;
import com.ka.superherosightings.entities.Sighting;
import com.ka.superherosightings.entities.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kennethan
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperhero(getSuperForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        for (Sighting sighting : sightings) {
            sighting.setSuperhero(getSuperForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
        }
        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String ADD_SIGHTING = "INSERT INTO Sighting(superId, locationId, date) VALUES (?,?,?)";
        jdbc.update(ADD_SIGHTING,
                sighting.getSuperhero().getId(),
                sighting.getLocation().getId(),
                Timestamp.valueOf(sighting.getDate().atTime(12, 0)));
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET superId = ?, locationId = ?, date = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getSuperhero().getId(),
                sighting.getLocation().getId(),
                Timestamp.valueOf(sighting.getDate().atTime(12, 0)),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        final String GET_SIGHTINGS_BY_DATE = "SELECT * FROM Sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS_BY_DATE, new SightingMapper(),
                Timestamp.valueOf(date.atTime(12, 0)));
        for (Sighting sighting : sightings) {
            sighting.setSuperhero(getSuperForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
        }
        return sightings;
    }

    private Super getSuperForSighting(Sighting sighting) {
        final String GET_SUPER_FOR_SIGHTING = "SELECT s.id, s.name, s.description, s.powerId "
                + "FROM Sighting si "
                + "JOIN Super s ON si.superId = s.id "
                + "WHERE si.id = ?";
        Super superhero = jdbc.queryForObject(GET_SUPER_FOR_SIGHTING, new SuperMapper(), sighting.getId());
        superhero.setPower(getPowerForSuper(superhero.getId()));
        superhero.setOrganizations(getOrganizationsForSuper(superhero.getId()));
        return superhero;
    }

    private Power getPowerForSuper(int id) {
        try {
            final String sql = "SELECT p.id, p.name FROM Power p "
                    + "JOIN Super s ON p.id = s.powerId WHERE s.id = ?";
            return jdbc.queryForObject(sql, new PowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Organization> getOrganizationsForSuper(int id) {
        final String sql = "SELECT o.id, o.name, o.description, o.address, o.contact "
                + "FROM OrganizationMember om "
                + "JOIN Organization o ON om.organizationId = o.id "
                + "WHERE om.superId = ?";
        return jdbc.query(sql, new OrganizationMapper(), id);
    }

    private Location getLocationForSighting(Sighting sighting) {
        final String GET_LOCATION_FOR_SIGHTING = "SELECT l.id, l.name, l.description, l.address, l.latitude, l.longitude "
                + "FROM Sighting si "
                + "JOIN Location l ON si.locationId = l.id "
                + "WHERE si.id = ?";
        return jdbc.queryForObject(GET_LOCATION_FOR_SIGHTING, new LocationMapper(), sighting.getId());
    }

    @Override
    public List<Sighting> getLastTenSightings() {
        final String GET_LAST_TEN_SIGHTINGS = "SELECT * FROM Sighting ORDER BY date DESC, id DESC LIMIT 10";
        List<Sighting> sightings = jdbc.query(GET_LAST_TEN_SIGHTINGS, new SightingMapper());
        for (Sighting sighting : sightings) {
            sighting.setSuperhero(getSuperForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
        }
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }

    }
}
