package com.metadata.LibraryDomain;

/**
 * Created by Christopher on 4/4/2017.
 */
public class Staff {

        protected String id, name;

        public Staff(String idNumber, String name, String cardNumber){
            this.id = idNumber;
            this.name = name;
        }
        public String getID(){
            return id;
        }
        public String getName(){
            return name;
        }
}
