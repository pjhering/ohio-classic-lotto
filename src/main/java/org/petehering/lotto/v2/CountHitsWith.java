package org.petehering.lotto.v2;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CountHitsWith extends Analyzer implements Report
{

    private final Map<Integer, Map<Integer, Integer>> hits;

    public CountHitsWith()
    {
        super("counts hits with other numbers");
        this.hits = new TreeMap<>();
    }

    @Override
    public Report analyze(List<Draw> draws) throws Exception
    {
        draws.forEach(d ->
        {
            int[] nmbrs = d.getNumbers();

            for(int n : nmbrs)
            {
                for(int m : nmbrs)
                {
                    if(n != m)
                    {
                        inc(n, m);
                    }
                }
            }
        });
        return this;
    }

    private void inc(int n, int i)
    {
        if(!hits.containsKey(n))
        {
            Map<Integer, Integer> map = new TreeMap<>();
            for(int j = 1; j < 50; j++)
            {
                map.put(j, 0);
            }
            hits.put(n, map);
        }

        Map<Integer, Integer> map = hits.get(n);

        if(!map.containsKey(i))
        {
            map.put(i, 1);
        }
        else
        {
            map.put(i, map.get(i) + 1);
        }
    }

    @Override
    public void print(PrintStream out) throws Exception
    {
        out.print("     ");
        for(int i = 1; i < 50; i++)
        {
            out.printf("%3d ", i);
        }
        out.println();
        
        hits.forEach((i, map) ->
        {
            System.out.printf("%2d   ", i);

            map.forEach((n, count) ->
            {
                System.out.printf("%3d ", count);
            });

            System.out.println();
        });
    }
}
