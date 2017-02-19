package org.petehering.lotto.v3;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Node
{

    private final Integer number;
    private int hitCount;
    private double payout;
    private Map<Integer, Integer> hitsWith;
    private int value;

    public Node(Integer number)
    {
        this.number = Objects.requireNonNull(number);
        this.hitsWith = new TreeMap<>();
    }
    
    public void printTo(PrintStream out)
    {
        out.append("Node ")
                .append("{\n    number:")
                .append(Objects.toString(number))
                .append(",\n    hitCount:")
                .append(Objects.toString(hitCount))
                .append(",\n    value:")
                .append(Objects.toString(value))
                .append(",\n    payout:")
                .append(Objects.toString(payout))
                .append(",\n    hitsWith: [");
        
        List<Integer> keys = new ArrayList<>();
        keys.addAll(hitsWith.keySet());
        int length = keys.size();
        
        for(int i = 0; i < length; i++)
        {
            Integer k = keys.get(i);
            Integer v = hitsWith.get(k) == null? 0: hitsWith.get(k);
            out.append("[")
                    .append(k.toString())
                    .append(", ")
                    .append(v.toString())
                    .append("]");
            
            if(i < length - 2)
            {
                out.append(", ");
            }
        }
        
        out.append("\n}\n");
    }

    public int getNumber()
    {
        return number;
    }

    public int getHitCount()
    {
        return hitCount;
    }

    public double getPayout()
    {
        return payout;
    }

    public int getHitWith(int i)
    {
        return hitsWith.get(i) == null ? 0 : hitsWith.get(i);
    }

    public int getValue()
    {
        return value;
    }

    public void addToHitsWith(int i)
    {
        if (this.number != i)
        {
            if (!hitsWith.containsKey(i))
            {
                hitsWith.put(i, 1);
            }
            else
            {
                hitsWith.put(i, hitsWith.get(i) + 1);
            }
        }
    }

    public void addToPayout(double d)
    {
        this.payout += d;
    }

    public void addToHitCount(int i)
    {
        this.hitCount += i;
    }
    
    public void addToValue(int i)
    {
        value += i;
    }
}
