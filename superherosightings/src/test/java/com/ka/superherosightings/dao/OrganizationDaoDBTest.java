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
public class OrganizationDaoDBTest {

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

    public OrganizationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Organization> orgs = orgDao.getAllOrgs();
        for (Organization org : orgs) {
            orgDao.deleteOrgById(org.getId());
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
    public void testAddGetOrg() {
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescription("Best group, no doubt.");
        org.setAddress("");
        org.setContact("");
        org.setSupers(new ArrayList<Super>());
        org = orgDao.addOrg(org);

        Organization fromDao = orgDao.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }

    @Test
    public void testGetAllOrgs() {
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescription("Best group, no doubt.");
        org.setAddress("");
        org.setContact("");
        org.setSupers(new ArrayList<Super>());
        org = orgDao.addOrg(org);

        Organization org2 = new Organization();
        org2.setName("Justice League");
        org2.setDescription("They're okay.");
        org2.setAddress("");
        org2.setContact("");
        org2.setSupers(new ArrayList<Super>());
        org2 = orgDao.addOrg(org2);

        List<Organization> orgs = orgDao.getAllOrgs();
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));
    }

    @Test
    public void testUpdateOrg() {
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescription("Best group, no doubt.");
        org.setAddress("");
        org.setContact("");
        org.setSupers(new ArrayList<Super>());
        org = orgDao.addOrg(org);

        Organization fromDao = orgDao.getOrgById(org.getId());
        assertEquals(org, fromDao);

        org.setDescription("My favorite group ever");
        orgDao.updateOrg(org);
        assertNotEquals(org, fromDao);

        fromDao = orgDao.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }

    @Test
    public void testDeleteOrg() {
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescription("Best group, no doubt.");
        org.setAddress("");
        org.setContact("");
        org.setSupers(new ArrayList<Super>());
        org = orgDao.addOrg(org);

        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Captain America");
        hero.setDescription("America's sweetheart");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        org.getSupers().add(hero);
        hero.getOrganizations().add(org);
        orgDao.updateOrg(org);
        superDao.updateSuper(hero);

        Organization fromDao = orgDao.getOrgById(org.getId());
        assertEquals(org, fromDao);

        orgDao.deleteOrgById(org.getId());
        fromDao = orgDao.getOrgById(org.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetOrgBySuper() {
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescription("Best group, no doubt.");
        org.setAddress("");
        org.setContact("");
        org.setSupers(new ArrayList<Super>());
        org = orgDao.addOrg(org);

        Power power = new Power();
        power.setName("Super strength");
        power = powerDao.addPower(power);

        Super hero = new Super();
        hero.setName("Captain America");
        hero.setDescription("America's sweetheart");
        hero.setPower(power);
        hero.setOrganizations(new ArrayList<Organization>());
        hero = superDao.addSuper(hero);

        hero.getOrganizations().add(org);
        superDao.updateSuper(hero);

        org.getSupers().add(hero);
        orgDao.updateOrg(org);

        List<Organization> orgs = orgDao.getOrgBySuper(hero);
        assertEquals(1, orgs.size());
        assertTrue(orgs.contains(org));

    }
}
