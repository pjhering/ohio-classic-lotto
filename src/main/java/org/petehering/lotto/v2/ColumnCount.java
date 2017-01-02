package org.petehering.lotto.v2;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ColumnCount extends Analyzer implements Report
{

    private final Map<Integer, int[]> hits;

    public ColumnCount()
    {
        super("column count");
        this.hits = new TreeMap<>();
    }

    @Override
    public Report analyze(List<Draw> draws) throws Exception
    {
        draws.forEach(d ->
        {
            int[] n = d.getNumbers();

            for(int i = 0; i < n.length; i++)
            {
                inc(n[i], i);
            }
        });
        return this;
    }

    private void inc(Integer n, int i)
    {
        if(!hits.containsKey(n))
        {
            hits.put(n, new int[6]);
        }

        hits.get(n)[i] += 1;
    }

    @Override
    public void print(PrintStream out) throws Exception
    {
        hits.forEach((n, h) ->
        {
            double a = avg(h);
            System.out.printf("%2d\t%3d%3d%3d%3d%3d%3d%10.2f%n", n, h[0], h[1], h[2], h[3], h[4], h[5], a);
        });
    }
    
    private double avg(int[] a)
    {
        double count = 0.0;
        double total = 0.0;
        
        for(int i = 0; i < a.length; i++)
        {
            count += a[i];
            total += a[i] * (i + 1);
        }
        
        return total / count;
    }
}
