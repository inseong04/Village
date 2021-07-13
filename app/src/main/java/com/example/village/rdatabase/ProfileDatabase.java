package com.example.village.rdatabase;

public class ProfileDatabase {

    public class ProfileUser{
        public String name;
        public ProfileUser(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }
}
