/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.entities.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
public class SightingDaoDBTest {

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

    public SightingDaoDBTest() {
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
    public void testAddGetSighting() {
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
        sighting.setDate(LocalDate.of(2020, Month.SEPTEMBER, 3));
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings() {
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
        sighting.setDate(LocalDate.of(2020, Month.SEPTEMBER, 3));
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setSuperhero(hero);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.of(2020, Month.SEPTEMBER, 4));
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSightings() {
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
        sighting.setDate(LocalDate.of(2020, Month.SEPTEMBER, 3));
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sighting.setDate(LocalDate.of(2020, Month.SEPTEMBER, 5));
        sightingDao.updateSighting(sighting);
        assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSighting() {
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
        sighting.setDate(LocalDate.of(2020, Month.SEPTEMBER, 3));
        sighting = sightingDao.addSighting(sighting);

        sightingDao.deleteSightingById(sighting.getId());

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetSightingByDate() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Super hero2 = new Super();
        hero2.setName("Batman");
        hero2.setDescription("The Dark Knight");
        hero2.setOrganizations(new ArrayList<Organization>());
        hero2 = superDao.addSuper(hero2);

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
        sighting.setDate(LocalDate.of(2020, Month.SEPTEMBER, 3));
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setSuperhero(hero2);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.of(2020, Month.SEPTEMBER, 3));
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getSightingsByDate(LocalDate.of(2020, Month.SEPTEMBER, 3));

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

}
