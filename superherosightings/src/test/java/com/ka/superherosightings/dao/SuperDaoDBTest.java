/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.entities.Location;
import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Power;
import com.ka.superherosightings.entities.Sighting;
import com.ka.superherosightings.entities.Super;
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
public class SuperDaoDBTest {

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

    public SuperDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<Super> supers = superDao.getAllSupers();
        for (Super hero : supers) {
            superDao.deleteSuperById(hero.getId());
        }

        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }
    }

    @Test
    public void testAddGetSuper() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Super fromDao = superDao.getSuperById(hero.getId());
        assertEquals(hero, fromDao);
    }

    @Test
    public void testGetAllSupers() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setName("Super speed");
        power2 = powerDao.addPower(power2);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Super hero2 = new Super();
        hero2.setName("Captain America");
        hero2.setDescription("America's sweetheart");
        hero2.setPower(power);
        hero2.setOrganizations(new ArrayList<Organization>());
        hero2 = superDao.addSuper(hero2);

        Super hero3 = new Super();
        hero3.setName("Flash");
        hero3.setDescription("Fast guy in DC");
        hero3.setPower(power2);
        hero3.setOrganizations(new ArrayList<Organization>());
        hero3 = superDao.addSuper(hero3);

        List<Super> supers = superDao.getAllSupers();
        assertEquals(3, supers.size());
        assertTrue(supers.contains(hero));
        assertTrue(supers.contains(hero2));
        assertTrue(supers.contains(hero3));
    }

    @Test
    public void testUpdateSuper() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Super fromDao = superDao.getSuperById(hero.getId());
        assertEquals(hero, fromDao);

        hero.setName("Superboy");
        superDao.updateSuper(hero);

        assertNotEquals(hero, fromDao);

        fromDao = superDao.getSuperById(hero.getId());
        assertEquals(hero, fromDao);
    }

    @Test
    public void testDeleteSuper() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Super fromDao = superDao.getSuperById(hero.getId());
        assertEquals(hero, fromDao);

        superDao.deleteSuperById(hero.getId());
        fromDao = superDao.getSuperById(hero.getId());
        assertNull(fromDao);

    }

    @Test
    public void testGetSupersByLocation() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setName("Super speed");
        power2 = powerDao.addPower(power2);

        Super hero = new Super();
        hero.setName("Superman");
        hero.setDescription("Kal-El");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        Super hero2 = new Super();
        hero2.setName("Captain America");
        hero2.setDescription("America's sweetheart");
        hero2.setPower(power);
        hero2.setOrganizations(new ArrayList<Organization>());
        hero2 = superDao.addSuper(hero2);

        Super hero3 = new Super();
        hero3.setName("Flash");
        hero3.setDescription("Fast guy in DC");
        hero3.setPower(power2);
        hero3.setOrganizations(new ArrayList<Organization>());
        hero3 = superDao.addSuper(hero3);

        Location location = new Location();
        location.setAddress("Daily Planet address");
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setAddress("Flash address");
        location2 = locationDao.addLocation(location2);

        Sighting sighting = new Sighting();
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setSuperhero(hero2);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);

        Sighting sighting3 = new Sighting();
        sighting3.setSuperhero(hero3);
        sighting3.setLocation(location2);
        sighting3.setDate(LocalDate.now());
        sighting3 = sightingDao.addSighting(sighting3);

        List<Super> supers = superDao.getSupersByLocation(location);
        assertEquals(2, supers.size());
        assertTrue(supers.contains(hero));
        assertTrue(supers.contains(hero2));
        assertFalse(supers.contains(hero3));

    }

}
