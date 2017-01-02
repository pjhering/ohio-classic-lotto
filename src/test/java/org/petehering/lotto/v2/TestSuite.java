package org.petehering.lotto.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestSuite
{

    private final List<Test> tests;
    private final String name;

    private TestSuite(String name)
    {
        this.name = Objects.requireNonNull(name);
        this.tests = new ArrayList<>();
    }
    
    public static TestSuite build(String name)
    {
        return new TestSuite(name);
    }

    public TestSuite add(Test test)
    {
        this.tests.add(test);
        return this;
    }

    public void run()
    {
        String header = ("Test Suite: '" + name + "'");
        StringBuilder sb = new StringBuilder();
        for(char c : header.toCharArray() )
        {
            sb.append('-');
        }
        System.out.printf("%s%n%s%n%s%n", sb.toString(), header, sb.toString());

        for(Test t : tests)
        {
            try
            {
                System.out.printf("test '%s' : %s%n",
                        t.getName(),
                        t.test()? "passed":"failed");
            }
            catch(Exception ex)
            {
                System.out.printf("test '%s' : %s - %s%n",
                        t.getName(),
                        ex.getClass().getName(),
                        ex.getMessage());
            }
        }
    }
}
