/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Super;
import java.util.List;

/**
 *
 * @author kennethan
 */
public interface OrganizationDao {

    Organization getOrgById(int id);

    List<Organization> getAllOrgs();

    Organization addOrg(Organization organization);

    void updateOrg(Organization organization);

    void deleteOrgById(int id);

    List<Organization> getOrgBySuper(Super superhero);

}
