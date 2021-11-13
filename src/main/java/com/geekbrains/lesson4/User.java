package com.geekbrains.lesson4;

public class User {

    private final String name;
    private final String surname;
    private final String address;
    private final String email;
    private final String phone;
    private final String vkLink;
    private final String facebookLink;

    private User(UserBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.address = builder.address;
        this.email = builder.email;
        this.phone = builder.phone;
        this.vkLink = builder.vkLink;
        this.facebookLink = builder.facebookLink;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getVkLink() {
        return vkLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private String name;
        private String surname;
        private String address;
        private String email;
        private String phone;
        private String vkLink;
        private String facebookLink;

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder withVkLink(String vkLink) {
            this.vkLink = vkLink;
            return this;
        }

        public UserBuilder withFacebookLink(String facebookLink) {
            this.facebookLink = facebookLink;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
