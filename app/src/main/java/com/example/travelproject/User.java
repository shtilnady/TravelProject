package com.example.travelproject;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public String email;
    public List<Trip> trips;
    public List<User> friends;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        trips = new ArrayList<Trip>();
        friends = new ArrayList<User>();
    }

    public User(){

    }
}
