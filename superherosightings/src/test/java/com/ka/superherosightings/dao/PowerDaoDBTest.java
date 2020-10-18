/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Power;
import com.ka.superherosightings.entities.Super;
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
public class PowerDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperDao superDao;

    @Autowired
    OrganizationDao orgDao;

    public PowerDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Super> supers = superDao.getAllSupers();
        for (Super superhero : supers) {
            superDao.deleteSuperById(superhero.getId());
        }

        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }
    }

    @Test
    public void testAddGetPower() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, fromDao);
    }

    @Test
    public void testGetAllPowers() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setName("Flying");
        power2 = powerDao.addPower(power2);

        Power power3 = new Power();
        power3.setName("Super speed");
        power3 = powerDao.addPower(power3);

        List<Power> powers = powerDao.getAllPowers();

        assertEquals(3, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
        assertTrue(powers.contains(power3));
    }

    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, fromDao);

        power.setName("Super speed");
        powerDao.updatePower(power);

        assertNotEquals(power, fromDao);

        fromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, fromDao);
    }

    @Test
    public void testDeletePower() {
        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super superhero = new Super();
        superhero.setName("Superman");
        superhero.setDescription("The face of superheros (not really!)");
        superhero.setPower(power);
        superhero.setOrganizations(new ArrayList<Organization>());
        superhero = superDao.addSuper(superhero);

        Power fromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, fromDao);

        powerDao.deletePowerById(power.getId());

        fromDao = powerDao.getPowerById(power.getId());
        assertNull(fromDao);

        superhero = superDao.getSuperById(superhero.getId());
        assertNull(superhero.getPower());

    }

}
