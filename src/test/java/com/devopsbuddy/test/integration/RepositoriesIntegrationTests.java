package com.devopsbuddy.test.integration;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
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

    private static final int BASIC_PLAN_ID = 1;
    private static final int BASIC_ROLE_ID = 1;
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
        Plan retrievedPlan = planRepository.findById(BASIC_PLAN_ID).get();
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void createNewRole() throws Exception{
        Role basicRole = createBasicRole();
        roleRepository.save(basicRole);
        Role retrievedRole = roleRepository.findById(BASIC_ROLE_ID).get();
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception{
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        User basicUser = createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole = createBasicRole();
        //list of user-role assiociating table entities (here only one)
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setRole(basicRole);
        userRole.setUser(basicUser);
        userRoles.add(userRole);

        //never basicUser.setUserRoles - can cascade delete every other userRole, roles etc.
        basicUser.getUserRoles().addAll(userRoles);

        for (UserRole u :
                userRoles) {
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

    private User createBasicUser() {
        User user = new User();
        user.setCountry("country");
        user.setDescription("desc");
        user.setEmail("email");
        user.setFirstName("fname");
        user.setLastName("lname");
        user.setPassword("pass");
        user.setPhoneNumber("phone number");
        user.setProfileImageUrl("profile image url");
        user.setStripeCustomerId("Stripe ID");
        user.setUsername("username");
        return user;
    }

    private Role createBasicRole() {
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("Basic");
        return role;
    }

    private Plan createBasicPlan() {
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");
        return plan;
    }
}
