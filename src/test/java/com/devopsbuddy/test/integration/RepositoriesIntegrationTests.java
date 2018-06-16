package com.devopsbuddy.test.integration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UsersUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class RepositoriesIntegrationTests {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init(){
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void createNewPlan() throws Exception{
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId()).get();
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void createNewRole() throws Exception{
        Role basicRole = createBasicRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId()).get();
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception{
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        User basicUser = UsersUtils.createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole = createBasicRole(RolesEnum.BASIC);
        //list of user-role assiociating table entities (here only one)
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        //never basicUser.setUserRoles - can cascade delete every other userRole, roles etc.
        basicUser.getUserRoles().addAll(userRoles);

        for (UserRole u : userRoles) {
            roleRepository.save(u.getRole());
        }

        basicUser = userRepository.save(basicUser);
        User newUser = userRepository.findById(basicUser.getId()).get();
        Assert.assertNotNull(newUser);
        Assert.assertTrue(newUser.getId() != 0);
        Assert.assertNotNull(newUser.getPlan());
        Assert.assertTrue(newUser.getPlan().getId() != 0);
        Set<UserRole> newUserRoles = newUser.getUserRoles();
        for (UserRole u :
                newUserRoles) {
            Assert.assertNotNull(u.getRole());
            Assert.assertTrue(u.getRole().getId() != 0);
        }

    }

    private Role createBasicRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    private Plan createBasicPlan() {
        return new Plan(PlansEnum.BASIC);
    }
}
