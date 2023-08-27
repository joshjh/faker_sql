package org.boshed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import com.github.javafaker.Address;
import com.github.javafaker.Company;
import com.github.javafaker.Faker;

public abstract class Fakes {
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXTY".toCharArray();

    abstract String SQLInsertString();
    interface Fields<T> {
        public T[] listFields();
        public String getTableName();
        // not a fan because the size is static but we can use a list in order to .contains (string)
        public List<String> getUniqueFields();
    };
    abstract PreparedStatement SQLInsertPreparedStatement(Connection conn);
    public enum FakePersonFields {firstname, surname, dob, address, email, phonenumber, jobtitle, house, employeenumber}
    public enum FakeCompanyFields {companyName, country, city, industry, url, streetAddress, employees} 
    public enum FakeDogFields {name, breed, gender, memephrase, size, sound, dogtag}

    public static class FakePerson extends Fakes implements Fields<FakePersonFields> {
        String tableName = "people";
        Address address;
        String email;
        String phonenumber;
        String firstname;
        String surname;
        String jobtitle;
        String house;
        String employeenumber;
        LocalDate dob;
        static final String[] uniqueFields = {FakePersonFields.employeenumber.toString()};

        public FakePerson(Faker Faker, Random randGen) {
            this.firstname = Faker.name().firstName();
            this.surname = Faker.name().lastName();
            this.address = Faker.address();
            this.email = Faker.internet().emailAddress();
            this.phonenumber = Faker.phoneNumber().cellPhone();
            this.jobtitle = Faker.job().title();
            this.house = Faker.gameOfThrones().house();
            this.employeenumber = String.format(String.valueOf(Math.abs(randGen.nextLong())) + String.valueOf(alphabet[randGen.nextInt(alphabet.length)]));
            // we need dob in java.time.LocalDate because Postgreql JDBC likes it.
            this.dob = LocalDate.ofInstant(Faker.date().birthday(17, 75).toInstant(), ZoneId.systemDefault());
        
        }

        public String SQLInsertString() {
            return String.format("INSERT INTO people (firstname, surname, dob, address, email, phonenumber, jobtitle, house, employeenumber) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, %s);", 
            firstname, surname, dob, address.fullAddress(), email, phonenumber, jobtitle, house, employeenumber); 
        }

        public PreparedStatement SQLInsertPreparedStatement(Connection conn) {
            try {
                int field_index;
                String SQLString = "INSERT INTO people ("; 
                // use the enum fields to build the column part of the statement
                FakePersonFields[] fields = FakePersonFields.values();
                //sqlStatement.set* has field_index +1 because the Statement.Set function counts the ? in the statement starting at 1, not 0.
                for (field_index=0; field_index < fields.length; field_index++) {
                    SQLString += fields[field_index].name() + ", ";
                    
                }
                SQLString = SQLString.strip().substring(0, SQLString.length()-2) + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                // System.out.println(SQLString);
                PreparedStatement sqlStatement = conn.prepareStatement(SQLString);
                field_index = 1;
                sqlStatement.setString(field_index, firstname); field_index++;
                sqlStatement.setString(field_index, surname); field_index++;
                sqlStatement.setObject(field_index, dob); field_index++;
                sqlStatement.setString(field_index, address.fullAddress()); field_index++;
                sqlStatement.setString(field_index, email); field_index++;
                sqlStatement.setString(field_index, phonenumber); field_index++;
                sqlStatement.setString(field_index, jobtitle); field_index++;
                sqlStatement.setString(field_index, house); field_index++;
                sqlStatement.setString(field_index, employeenumber); field_index++;
            
                return sqlStatement;

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("SQL statement build has failed!");
            }
        }
        
        public FakePersonFields[] listFields() { return FakePersonFields.values();}
        public String getTableName() {return tableName;}

        public List<String> getUniqueFields() {
            List<String> uniqueFieldList = Arrays.asList(uniqueFields);
            return uniqueFieldList;
        }

    }

    public static class FakeCompany extends Fakes implements Fields<FakeCompanyFields> {
        String tableName = "companies";
        Address address;
        String companyName;
        String country;
        String city;
        String industry;
        String url;
        String streetAddress;
        int employees;
        static final String[] uniqueFields = {FakeCompanyFields.companyName.toString()};

        public FakeCompany(Faker Faker, Random randGen) {
            Company company = Faker.company();
            this.companyName = company.name();
            this.address = Faker.address();
            this.streetAddress = address.buildingNumber() + address.streetAddress() + address.city();
            this.city = address.city();
            this.country = address.countryCode();
            this.industry = company.industry();
            this.url = company.url();
            employees = randGen.nextInt(500);
        }

        public String SQLInsertString() {
            return String.format("INSERT INTO companies (companyname, streetaddress, city, country, industry, url) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');", 
            companyName, streetAddress, city, country, industry, url); 
        }
        
        public PreparedStatement SQLInsertPreparedStatement(Connection conn) {
            try {
                int field_index;
                String SQLString = "INSERT INTO companies ("; 
                // use the enum fields to build the column part of the statement
                FakeCompanyFields[] fields = FakeCompanyFields.values();
                //sqlStatement.set* has field_index +1 because the Statement.Set function counts the ? in the statement starting at 1, not 0.
                for (field_index=0; field_index < fields.length; field_index++) {
                    SQLString += fields[field_index].name() + ", ";
                    
                }
                SQLString = SQLString.strip().substring(0, SQLString.length()-2) + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
                // System.out.println(SQLString);
                PreparedStatement sqlStatement = conn.prepareStatement(SQLString);
                field_index = 1;
                sqlStatement.setString(field_index, companyName); field_index++;
                sqlStatement.setString(field_index, country); field_index++;
                sqlStatement.setObject(field_index, city); field_index++;
                sqlStatement.setString(field_index, industry); field_index++;
                sqlStatement.setString(field_index, url); field_index++;
                sqlStatement.setString(field_index, streetAddress); field_index++;
                sqlStatement.setInt(field_index, employees); field_index++;
                
                return sqlStatement;

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("SQL statement build has failed!");
            }
        }

        public String getTableName() {return tableName;}

        public FakeCompanyFields[] listFields() { return FakeCompanyFields.values();}

        public List<String> getUniqueFields() {
            List<String> uniqueFieldList = Arrays.asList(uniqueFields);
            return uniqueFieldList;

        }
    }

    public static class FakeDog extends Fakes implements Fields<FakeDogFields> {
        String tablename = "dogs";
        String name;
        String breed;
        String gender;
        String memephrase;
        String size;
        String sound;
        String[] uniqueFields = {FakeDogFields.dogtag.name()};
        public FakeDog(Faker Faker, Random Randgen) {
            this.name = Faker.dog().name();
            this.breed = Faker.dog().breed(); Faker.dog();
            this.gender = Faker.dog().gender();
            this.memephrase = Faker.dog().memePhrase();
            this.size = Faker.dog().size();
            this.sound = Faker.dog().sound();


        }

        @Override
        public FakeDogFields[] listFields() {return FakeDogFields.values();}

        @Override
        public String getTableName() {
            return tablename;
        }

        @Override
        public List<String> getUniqueFields() {
            return Arrays.asList(uniqueFields);
        }

        @Override
        String SQLInsertString() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'SQLInsertString'");
        }

        @Override
        PreparedStatement SQLInsertPreparedStatement(Connection conn) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'SQLInsertPreparedStatement'");
        }
        
    }

}


