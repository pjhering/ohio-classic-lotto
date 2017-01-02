package org.petehering.lotto.v2;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HitFrequency extends Analyzer implements Report
{

    private final Map<Integer, Integer> hits;

    public HitFrequency()
    {
        super("Hit Frequency");
        this.hits = new TreeMap<>();
    }

    @Override
    public Report analyze(List<Draw> draws) throws Exception
    {
        draws.forEach(d ->
        {
            int[] ns = d.getNumbers();
            for(int n : ns)
            {
                inc(n);
            }
        });

        return this;
    }

    private void inc(Integer i)
    {
        if(!hits.containsKey(i))
        {
            hits.put(i, 1);
        }
        else
        {
            hits.put(i, hits.get(i) + 1);
        }
    }

    @Override
    public void print(PrintStream out) throws Exception
    {
        hits.forEach((i, c) ->
        {
            out.printf("%d\t%d%n", i, c);
        });
    }
}
