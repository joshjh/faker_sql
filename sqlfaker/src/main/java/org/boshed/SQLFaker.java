package org.boshed;
import java.util.Iterator;

import org.boshed.Fakes.FakePerson;



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
        for (int i=0; i < fakePeople.length; i++) {fakePeople[i] = new FakePerson();
            System.out.println(fakePeople[i].SQLInsertString());
        }
        }
}

