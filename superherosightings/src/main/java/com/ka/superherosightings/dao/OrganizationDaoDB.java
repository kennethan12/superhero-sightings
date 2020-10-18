/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.dao;

import com.ka.superherosightings.dao.PowerDaoDB.PowerMapper;
import com.ka.superherosightings.dao.SuperDaoDB.SuperMapper;
import com.ka.superherosightings.entities.Organization;
import com.ka.superherosightings.entities.Power;
import com.ka.superherosightings.entities.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrgById(int id) {
        try {
            final String GET_ORG_BY_ID = "SELECT * FROM Organization WHERE id = ?";
            Organization organization = jdbc.queryForObject(GET_ORG_BY_ID, new OrganizationMapper(), id);
            organization.setSupers(getSupersForOrganization(organization));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrgs() {
        final String GET_ALL_ORGS = "SELECT * FROM Organization";
        List<Organization> organizations = jdbc.query(GET_ALL_ORGS, new OrganizationMapper());
        for (Organization org : organizations) {
            org.setSupers(getSupersForOrganization(org));
        }
        return organizations;
    }

    @Override
    @Transactional
    public Organization addOrg(Organization organization) {
        final String ADD_ORG = "INSERT INTO Organization(name, description, address, contact) VALUES(?,?,?,?)";
        jdbc.update(ADD_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertOrganizationMembers(organization);
        return organization;
    }

    @Override
    @Transactional
    public void updateOrg(Organization organization) {
        final String UPDATE_ORG = "UPDATE Organization SET name = ?, description = ?, "
                + "address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getId());

        final String DELETE_ORG_MEMBERS = "DELETE FROM OrganizationMember WHERE organizationId = ?";
        jdbc.update(DELETE_ORG_MEMBERS, organization.getId());
        insertOrganizationMembers(organization);
    }

    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_ORG_MEMBERS = "DELETE FROM OrganizationMember WHERE organizationId = ?";
        jdbc.update(DELETE_ORG_MEMBERS, id);

        final String DELETE_ORG = "DELETE FROM Organization WHERE id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    public List<Organization> getOrgBySuper(Super superhero) {
        final String GET_ORG_BY_SUPER = "SELECT o.id, o.name, o.description, o.address, o.contact "
                + "FROM OrganizationMember om "
                + "JOIN Organization o ON om.organizationId = o.id "
                + "WHERE om.superId = ?";
        List<Organization> orgs = jdbc.query(GET_ORG_BY_SUPER, new OrganizationMapper(), superhero.getId());
        for (Organization org : orgs) {
            org.setSupers(getSupersForOrganization(org));
        }
        return orgs;
    }

    private List<Super> getSupersForOrganization(Organization organization) {
        final String GET_SUPERS = "SELECT s.id, s.name, s.description, s.powerId "
                + "FROM OrganizationMember om "
                + "JOIN Super s ON om.superId = s.id "
                + "WHERE om.organizationId = ?";
        List<Super> supers = jdbc.query(GET_SUPERS, new SuperMapper(), organization.getId());
        for (Super superhero : supers) {
            superhero.setPower(getPowerForSuper(superhero.getId()));
        }
        return supers;
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

    private void insertOrganizationMembers(Organization organization) {
        final String INSERT_ORG_MEMBERS = "INSERT INTO OrganizationMember VALUES(?,?)";
        for (Super superhero : organization.getSupers()) {
            jdbc.update(INSERT_ORG_MEMBERS, organization.getId(), superhero.getId());
        }
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setContact(rs.getString("contact"));
            return organization;
        }
    }
}
