package com.tdarquier.imagewar.msvc.users.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "google_id")
    @NotBlank
    private String googleId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 1, max = 255)
    private String username;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "votes_done")
    @Min(value = 0)
    @NotNull
    private Integer votesDone;

    @Column(name = "changed_username")
    private boolean changedUsername;

    public User() {
    }

    public User(@NotBlank String googleId, @Email @NotBlank String email,
            @NotBlank @Size(min = 1, max = 255) String username, String profilePictureUrl,
            @Past @NotBlank Date createdAt, @Min(0) @NotNull Integer votesDone) {
        this.googleId = googleId;
        this.email = email;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
        this.createdAt = createdAt;
        this.votesDone = votesDone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVotesDone() {
        return votesDone;
    }

    public void setVotesDone(Integer votesDone) {
        this.votesDone = votesDone;
    }

    public boolean isChangedUsername() {
        return changedUsername;
    }

    public void setChangedUsername(boolean changedUsername) {
        this.changedUsername = changedUsername;
    }


    
    public static boolean compareDates(Instant date1, Instant date2) {
        if (date1 == null && date2 == null) {
            return true;
        } else if (date1 == null || date2 == null) {
            return false;
        } else {
            return date1.truncatedTo(ChronoUnit.DAYS).equals(date2.truncatedTo(ChronoUnit.DAYS));
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(googleId, other.googleId) &&
                Objects.equals(email, other.email) &&
                Objects.equals(username, other.username) &&
                Objects.equals(profilePictureUrl, other.profilePictureUrl) &&
                compareDates(createdAt.toInstant(), other.createdAt.toInstant()) &&
                Objects.equals(votesDone, other.votesDone) &&
                Objects.equals(changedUsername, other.changedUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, googleId, email, username, profilePictureUrl, createdAt, votesDone, changedUsername);
    }

    

}
