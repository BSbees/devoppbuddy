package com.devopsbuddy.backend.persistence.domain.backend;

import com.devopsbuddy.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //Default logger
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);
    private static final int DEFAULT_TOKEN_LENGTH_IN_MINUTES = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expire_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime expireDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user, LocalDateTime creationDateTime, int expirationInMinutes){
        if ((token == null) || (user == null) || (creationDateTime == null)){
            throw new IllegalArgumentException("Token, user and creation date mustn't be null");
        }
        if (expirationInMinutes == 0){
            LOG.warn("The token expiration length in minutes is zero. Assigning new default value {}", DEFAULT_TOKEN_LENGTH_IN_MINUTES);
            expirationInMinutes = DEFAULT_TOKEN_LENGTH_IN_MINUTES;
        }
        this.token = token;
        this.user = user;
        expireDate = creationDateTime.plusMinutes(expirationInMinutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        return getId() == that.getId() &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getExpireDate(), that.getExpireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getToken(), getUser(), getExpireDate());
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
