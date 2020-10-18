/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.service;

import com.ka.superherosightings.dao.LocationDao;
import com.ka.superherosightings.dao.OrganizationDao;
import com.ka.superherosightings.dao.PowerDao;
import com.ka.superherosightings.dao.SightingDao;
import com.ka.superherosightings.dao.SuperDao;
import com.ka.superherosightings.entities.Location;
import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Power;
import com.ka.superherosightings.entities.Sighting;
import com.ka.superherosightings.entities.Super;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kennethan
 */
@Service
public class SuperServiceImpl implements SuperService {

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

    @Override
    public Power getPowerById(int id) {
        return powerDao.getPowerById(id);
    }

    @Override
    public List<Power> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public Power addPower(Power power) {
        return powerDao.addPower(power);
    }

    @Override
    public void updatePower(Power power) {
        powerDao.updatePower(power);
    }

    @Override
    public void deletePowerById(int id) {
        powerDao.deletePowerById(id);
    }

    @Override
    public Power getPowerBySuper(Super superhero) {
        return powerDao.getPowerBySuper(superhero);
    }

    @Override
    public Super getSuperById(int id) {
        return superDao.getSuperById(id);
    }

    @Override
    public List<Super> getAllSupers() {
        return superDao.getAllSupers();
    }

    @Override
    public Super addSuper(Super superhero) {
        return superDao.addSuper(superhero);
    }

    @Override
    public void updateSuper(Super superhero) {
        superDao.updateSuper(superhero);
    }

    @Override
    public void deleteSuperById(int id) {
        superDao.deleteSuperById(id);
    }

    @Override
    public List<Super> getSupersByLocation(Location location) {
        return superDao.getSupersByLocation(location);
    }

    @Override
    public List<Super> getSupersByOrganization(Organization organization) {
        return superDao.getSupersByOrganization(organization);
    }

    @Override
    public Organization getOrgById(int id) {
        return orgDao.getOrgById(id);
    }

    @Override
    public List<Organization> getAllOrgs() {
        return orgDao.getAllOrgs();
    }

    @Override
    public Organization addOrg(Organization organization) {
        return orgDao.addOrg(organization);
    }

    @Override
    public void updateOrg(Organization organization) {
        orgDao.updateOrg(organization);
    }

    @Override
    public void deleteOrgById(int id) {
        orgDao.deleteOrgById(id);
    }

    @Override
    public List<Organization> getOrgBySuper(Super superhero) {
        return orgDao.getOrgBySuper(superhero);
    }

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }

    @Override
    public List<Location> getLocationsBySuper(Super superhero) {
        return locationDao.getLocationsBySuper(superhero);
    }

    @Override
    public Sighting getSightingById(int id) {
        return sightingDao.getSightingById(id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingById(int id) {
        sightingDao.deleteSightingById(id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        return sightingDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getLastTenSightings() {
        return sightingDao.getLastTenSightings();
    }

}
