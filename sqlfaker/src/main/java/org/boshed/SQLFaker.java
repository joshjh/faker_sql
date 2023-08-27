package org.boshed;
import java.util.Locale;
import java.util.Random;
import org.boshed.Fakes.*;

import com.github.javafaker.Faker;



/**
 * Hello world!
 *
 */
public class SQLFaker
{
   
    public static void main( String[] args )
    {
        int fakeCount = Integer.valueOf(args[0]);
        FakePerson[] fakePeople = new FakePerson[fakeCount+1];
        FakeCompany[] fakeCompanies = new FakeCompany[fakeCount+1];
        Faker Faker = new Faker(new Locale("en-GB"));
        SQLEngine SQLEngine = new SQLEngine();

        Random randGen = new Random();
        for (int i=0; i < fakePeople.length; i++) {
            fakePeople[i] = new FakePerson(Faker, randGen);
            // System.out.println(fakePeople[i].SQLInsertString());
            // System.out.println(fakePeople[i].SQLInsertPreparedStatement(SQLEngine.conn));
        }
         for (int i=0; i < fakeCompanies.length; i++) {
            fakeCompanies[i] = new FakeCompany(Faker, randGen);
            // System.out.println(fakePeople[i].SQLInsertString());
            // System.out.println(fakeCompanies[i].SQLInsertPreparedStatement(SQLEngine.conn));
        }
       
        // create the tables
        SQLEngine.prepTable(fakeCompanies[0]);
        SQLEngine.prepTable(fakePeople[0]);

        for (FakeCompany company:fakeCompanies) {SQLEngine.sqlInsert(company);}
        for (FakePerson person:fakePeople) {SQLEngine.sqlInsert(person);}
        
        }
}

