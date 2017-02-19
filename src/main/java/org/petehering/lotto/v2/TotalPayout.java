package org.petehering.lotto.v2;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TotalPayout extends Analyzer implements Report
{

    private final Map<Integer, Double> totals;

    public TotalPayout()
    {
        super("total payout");
        this.totals = new TreeMap<>();
    }

    @Override
    public Report analyze(List<Draw> draws) throws Exception
    {
        draws.forEach(d ->
        {
            double p = d.getPayout() / 6.0;

            for(int n : d.getNumbers())
            {
                inc(n, p);
            }
        });
        return this;
    }

    private void inc(Integer i, double p)
    {
        if(!totals.containsKey(i))
        {
            totals.put(i, p);
        }
        else
        {
            totals.put(i, totals.get(i) + p);
        }
    }

    @Override
    public void print(PrintStream out) throws Exception
    {
        Map<Double, Integer> reversed = new TreeMap<>();
        
        totals.forEach((i, d) ->
        {
            reversed.put(d, i);
        });

        reversed.forEach((d, i) ->
        {
            out.printf("%2d%11.2f%n", i, d);
        });
    }
}
