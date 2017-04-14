package org.petehering.lotto.v4;

import java.io.File;
import static java.lang.System.out;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        File root = args.length > 0 ? new File(args[0]) : new File(".");
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
        
        finished.forEach(value -> out.println(value));
    }
}
