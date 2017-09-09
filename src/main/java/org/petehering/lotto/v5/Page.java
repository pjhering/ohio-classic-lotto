package org.petehering.lotto.v5;

import java.util.HashMap;
import java.util.Map;

public class Page implements Comparable<Page>
{

    public final int NUMBER;
    private final Map<Page, Integer> links;
    private double rank;
    private double pass1rank;
    private double pass2rank;

    public Page(int number)
    {
        this.NUMBER = number;
        this.rank = 0.020408163; // initial rank = 1 / 49
        this.links = new HashMap<>();
    }

    public void pass(int i)
    {
        switch(i)
        {
            case 0:
                links.keySet().forEach((p) ->
                {
                    double count = links.get(p);

                    if(count > 0.0)
                    {
                        pass1rank += p.rank / count;
                    }
                });
                break;

            case 1:
                this.rank = pass1rank;
                pass1rank = 0.0;
                break;

            case 2:
                links.keySet().forEach((p) ->
                {
                    double count = links.get(p);

                    if(count > 0.0)
                    {
                        pass1rank += p.rank / count;
                    }
                });
                break;

            case 3:
                this.rank = pass1rank;
                break;

            default:
                break;
        }
    }

    public void addLinkTo(Page page)
    {
        if(this == page)
        {
            return;
        }

        if(!links.containsKey(page))
        {
            links.put(page, 0);
        }

        links.put(page, links.get(page) + 1);
    }

    @Override
    public String toString()
    {
        return new StringBuilder()
                .append(NUMBER)
                .append(":\t")
                .append(rank)
                .toString();
    }

    @Override
    public int compareTo(Page that)
    {
        if(this.rank < that.rank)
        {
            return -1;
        }
        else
        {
            if(this.rank > that.rank)
            {
                return 1;
            }
            else
            {
                if(this.NUMBER < that.NUMBER)
                {
                    return -1;
                }
                else
                {
                    if(this.NUMBER > that.NUMBER)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            }
        }
    }
}
