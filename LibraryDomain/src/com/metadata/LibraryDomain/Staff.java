package com.metadata.LibraryDomain;

/**
 * Created by Christopher on 4/4/2017.
 */
public class Staff {

    protected String userName, name, password;

    public Staff(String userName, String name, String password){
        this.userName = userName;
        this.name = name;
        this.password = password;
    }
    public String getUserName(){
        return userName;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
}
