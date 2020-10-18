/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.service;

import com.ka.superherosightings.entities.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author kennethan
 */
public interface SuperService {

    /* Power */
    Power getPowerById(int id);

    List<Power> getAllPowers();

    Power addPower(Power power);

    void updatePower(Power power);

    void deletePowerById(int id);

    Power getPowerBySuper(Super superhero);

    /* Super */
    Super getSuperById(int id);

    List<Super> getAllSupers();

    Super addSuper(Super superhero);

    void updateSuper(Super superhero);

    void deleteSuperById(int id);

    List<Super> getSupersByLocation(Location location);

    List<Super> getSupersByOrganization(Organization organization);

    /* Organization */
    Organization getOrgById(int id);

    List<Organization> getAllOrgs();

    Organization addOrg(Organization organization);

    void updateOrg(Organization organization);

    void deleteOrgById(int id);

    List<Organization> getOrgBySuper(Super superhero);

    /* Location */
    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    List<Location> getLocationsBySuper(Super superhero);

    /* Sighting */
    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    List<Sighting> getSightingsByDate(LocalDate date);

    List<Sighting> getLastTenSightings();

}
