/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ka.superherosightings.controller;

import com.ka.superherosightings.entities.*;
import com.ka.superherosightings.service.SuperService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author kennethan
 */
@Controller
public class OrganizationController {

    @Autowired
    SuperService service;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> orgs = service.getAllOrgs();
        List<Super> supers = service.getAllSupers();
        model.addAttribute("orgs", orgs);
        model.addAttribute("supers", supers);
        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization org, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");

        List<Super> supers = new ArrayList<Super>();
        if (superIds != null && !Arrays.stream(superIds).anyMatch("No Super"::equals)) {
            for (String id : superIds) {
                supers.add(service.getSuperById(Integer.parseInt(id)));
            }
        }
        org.setSupers(supers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            service.addOrg(org);
        }

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetails")
    public String organizationDetails(Integer id, Model model) {
        Organization org = service.getOrgById(id);
        model.addAttribute("org", org);
        return "organizationDetails";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id, Model model) {
        service.deleteOrgById(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization org = service.getOrgById(id);
        List<Super> supers = service.getAllSupers();
        model.addAttribute("org", org);
        model.addAttribute("supers", supers);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization org, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");

        List<Super> supers = new ArrayList<Super>();
        if (superIds != null && !Arrays.stream(superIds).anyMatch("No Supers"::equals)) {
            for (String id : superIds) {
                supers.add(service.getSuperById(Integer.parseInt(id)));
            }
        }
        org.setSupers(supers);

        service.updateOrg(org);
        return "redirect:/organizations";
    }
}
