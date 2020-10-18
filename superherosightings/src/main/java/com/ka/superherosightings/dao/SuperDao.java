/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.entities.Location;
import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Super;
import java.util.List;

/**
 *
 * @author kennethan
 */
public interface SuperDao {

    Super getSuperById(int id);

    List<Super> getAllSupers();

    Super addSuper(Super superhero);

    void updateSuper(Super superhero);

    void deleteSuperById(int id);

    List<Super> getSupersByLocation(Location location);

    List<Super> getSupersByOrganization(Organization organization);

}
