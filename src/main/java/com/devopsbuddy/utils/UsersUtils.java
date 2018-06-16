package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;

public class UsersUtils {

    /**
     * Non-instantiable
     */
    private UsersUtils() {
        throw new AssertionError("Not instantiable");
    }

    /**
     * Creates a basic user with random data
     * @return User with random data
     */
    public static User createBasicUser() {
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
        user.setUsername("basicUser");
        user.setEnabled(true);
        return user;
    }
}
