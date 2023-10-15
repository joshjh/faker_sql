package org.boshed;
import java.util.Locale;
import java.util.Random;
import org.boshed.Fakes.*;

import com.github.javafaker.Faker;

public class SQLFaker
{
   
    public static void main( String[] args )
    {
        int fakeCount = Integer.valueOf(args[0]);
        FakePerson[] fakePeople = new FakePerson[fakeCount+1];
        FakeCompany[] fakeCompanies = new FakeCompany[fakeCount+1];
        FakeDog[] fakeDogs = new FakeDog[fakeCount+1];
        FakeTeam[] fakeTeams = new FakeTeam[fakeCount+1];
        Faker Faker = new Faker(new Locale("en-GB"));
        SQLEngine SQLEngine = new SQLEngine();

        Random randGen = new Random();
        for (int i=0; i < fakePeople.length; i++) {
            fakePeople[i] = new FakePerson(Faker, randGen);
            fakeCompanies[i] = new FakeCompany(Faker, randGen);
            fakeDogs[i] = new FakeDog(Faker, randGen);
            fakeTeams[i] = new FakeTeam(Faker, randGen);
        }
       
        // create the tables
        // SQLEngine.prepTable(fakeCompanies[0]);
        // SQLEngine.prepTable(fakePeople[0]);
        // SQLEngine.prepTable(fakeDogs[0]);
        SQLEngine.prepTable(fakeTeams[0]);
       // for (FakeCompany company:fakeCompanies) {SQLEngine.sqlInsert(company);}
        //for (FakePerson person:fakePeople) {SQLEngine.sqlInsert(person);}
       // for (FakeDog dog:fakeDogs) {SQLEngine.sqlInsert(dog);}
       for (FakeTeam fakeTeam:fakeTeams) {SQLEngine.sqlInsert(fakeTeam);}
        }
}

