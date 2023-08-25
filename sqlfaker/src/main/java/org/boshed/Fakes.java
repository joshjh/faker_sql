package org.boshed;

import java.util.Locale;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

public abstract class Fakes {

    abstract String SQLInsertString();

    public static class FakePerson extends Fakes{
        private Faker Faker = new Faker(new Locale("en-GB"));
        Address address;
        String email;
        String phonenumber;
        String firstname;
        String surname;
        String jobtitle;
        String house;
        
        public FakePerson() {
            this.firstname = Faker.name().firstName();
            this.surname = Faker.name().lastName();
            this.address = Faker.address();
            this.email = Faker.internet().emailAddress();
            this.phonenumber = Faker.phoneNumber().cellPhone();
            this.jobtitle = Faker.job().title();
            this.house = Faker.gameOfThrones().house();
            
        }

        public String SQLInsertString() {
            return String.format("INSERT INTO people (firstname, surname, address, email, phonenumber, jobtitle, house) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');", 
            firstname, surname, address.fullAddress(), email, phonenumber, jobtitle, house); 
        }
    }
}
