package org.petehering.lotto.v4;

import java.io.File;
import static java.lang.System.out;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Main
{
    private static File root;
    private static LocalDate query;
    
    public static void main(String[] args) throws Exception
    {
        parse(args);
        
        CsvDrawParser csv = new CsvDrawParser(root);
        TreeSet<Draw> draws = csv.parse();
        Map<Integer, Number> numbers = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        
        for(Draw d : draws)
        {
            int[] array = d.getNumbers();
            
            for(Integer i : array)
            {
                cal.setTime(d.getDate());
                
                if(!numbers.containsKey(i))
                {
                    numbers.put(i, new Number(i));
                }
                
                Number n = numbers.get(i);
                n.add(cal.get(YEAR), 1 + cal.get(MONTH), cal.get(DAY_OF_MONTH));
            }
        }
        
        TreeSet<Number> finished = new TreeSet<>();
        
        numbers.forEach((key, value)->{
            value.finish();
            finished.add(value);
        });
        
        finished.forEach(n ->
        {
            LocalDate next = n.getNext();
            long between = next.isBefore(query)?
                    ChronoUnit.DAYS.between(next, query):
                    ChronoUnit.DAYS.between(query, next);
            String text = String.format("%-5d%-20s%d", n.VALUE, next, between);
            out.println(text);
        });
    }
    
    private static void parse(String[] args)
    {
        for(String arg : args)
        {
            if(arg.startsWith("r="))
            {
                root = new File(arg.substring(2));
            }
            else if(arg.startsWith("q="))
            {
                query = LocalDate.parse(arg.substring(2));
            }
        }
        
        if(root == null)
        {
            root = new File(".");
        }
        
        if(query == null)
        {
            query = LocalDate.now();
        }
    }
}
