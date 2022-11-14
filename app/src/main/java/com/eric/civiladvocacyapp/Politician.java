package com.eric.civiladvocacyapp;

import java.io.Serializable;

public class Politician implements Serializable {

    private String name;
    private String office;
    private String party;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String facebookLink;
    private String twitterLink;
    private String youtubeLink;
    private String photoUrl;

    public Politician(String name, String office, String party, String address, String phone, String email, String website, String facebookLink, String twitterLink, String youtubeLink, String photoUrl) {
        this.name = name;
        this.office = office;
        this.party = party;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.facebookLink = facebookLink;
        this.twitterLink = twitterLink;
        this.youtubeLink = youtubeLink;
        this.photoUrl = photoUrl;
    }


    public String getName() { return name; }
    public String getOffice() { return office; }
    public String getParty() { return party; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getWebsite() { return website; }
    public String getFacebookLink() { return facebookLink; }
    public String getTwitterLink() { return twitterLink; }
    public String getYoutubeLink() { return youtubeLink; }
    public String getPhotoUrl() { return photoUrl; }

    public void setName(String name) { this.name = name; }
    public void setOffice(String office) { this.office = office; }
    public void setParty(String party) { this.party = party; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setWebsite(String website) { this.website = website; }
    public void setFacebookLink(String facebookLink) { this.facebookLink = facebookLink; }
    public void setTwitterLink(String twitterLink) { this.twitterLink = twitterLink; }
    public void setYoutubeLink(String youtubeLink) { this.youtubeLink = youtubeLink; }

    @Override
    public String toString() {
        return "Politician{" +
                "name='" + name + '\'' +
                ", office='" + office + '\'' +
                ", party='" + party + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", facebookLink='" + facebookLink + '\'' +
                ", twitterLink='" + twitterLink + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
