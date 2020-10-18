/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.entities.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author kennethan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperDao superDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Super> supers = superDao.getAllSupers();
        for (Super hero : supers) {
            superDao.deleteSuperById(hero.getId());
        }

        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
    }

    @Test
    public void testAddGetLocation() {
        Location location = new Location();
        location.setName("Somewhere");
        location.setDescription("");
        location.setAddress("");
        location.setLatitude("");
        location.setLongitude("");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Somewhere");
        location.setDescription("");
        location.setAddress("");
        location.setLatitude("");
        location.setLongitude("");
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Another Place");
        location2.setDescription("");
        location2.setAddress("");
        location2.setLatitude("");
        location2.setLongitude("");
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Somewhere");
        location.setDescription("");
        location.setAddress("");
        location.setLatitude("");
        location.setLongitude("");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        location.setName("Somewhere Else");
        locationDao.updateLocation(location);
        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocation() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Location location = new Location();
        location.setName("Somewhere");
        location.setDescription("");
        location.setAddress("");
        location.setLatitude("");
        location.setLongitude("");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);

        locationDao.deleteLocationById(location.getId());

        Sighting sightingFromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(sightingFromDao);

        Location locationFromDao = locationDao.getLocationById(location.getId());
        assertNull(locationFromDao);
    }

    @Test
    public void testGetLocationBySuper() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Location location = new Location();
        location.setName("Somewhere");
        location.setDescription("");
        location.setAddress("");
        location.setLatitude("");
        location.setLongitude("");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);

        List<Location> locations = locationDao.getLocationsBySuper(hero);
        assertTrue(locations.contains(location));
    }

}
