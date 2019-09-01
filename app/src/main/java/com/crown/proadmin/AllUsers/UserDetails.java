package com.crown.proadmin.AllUsers;

public class UserDetails {
    private String id;
    private String userName;
    private String accountType;
    private String profilePictureUrl;

    public UserDetails(String id, String userName, String accountType, String profilePictureUrl) {
        this.id = id;
        this.userName = userName;
        this.accountType = accountType;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}
