package org.petehering.lotto.v2;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
        Set<Entry<Integer, Integer>> set = hits.entrySet();
        List<Entry<Integer, Integer>> list = new LinkedList<>();
        list.addAll(set);
        Collections.sort(list, new Comparator<Entry<Integer, Integer>>(){
            @Override
            public int compare(Entry<Integer, Integer> e1, Entry<Integer, Integer> e2)
            {
                if(e1.getValue() < e2.getValue())
                {
                    return -1;
                }
                
                if(e1.getValue() > e2.getValue())
                {
                    return 1;
                }
                
                if(e1.getKey() < e2.getKey())
                {
                    return -1;
                }
                
                if(e1.getKey() > e2.getKey())
                {
                    return 1;
                }
                
                return 0;
            }
        });
        list.forEach((e)->
        {
            out.printf("%d\t%d%n", e.getKey(), e.getValue());
        });
//        hits.forEach((i, c) ->
//        {
//            out.printf("%d\t%d%n", i, c);
//        });
    }
}
