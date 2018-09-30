package com.devopsbuddy.backend.persistence.domain.backend;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by tedonema on 28/03/2016.
 */
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

    /** The Serial Version UID for Serializable classes. */
    private static final long serialVersionUID = 1L;

    public UserRole() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return getId() == userRole.getId() &&
                Objects.equals(getUser(), userRole.getUser()) &&
                Objects.equals(getRole(), userRole.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getRole());
    }

    public void setId(long id) {
        this.id = id;
    }


}
