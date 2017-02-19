package org.petehering.lotto.v1;

public class ReportLine implements Comparable<ReportLine>
{

    public final int NUMBER;
    public final int HITS;
    public final double PAYOUTS;
    public final double AVERAGE_PAYOUT;

    public ReportLine(int n, int h, double p)
    {
        this.NUMBER = n;
        this.HITS = h;
        this.PAYOUTS = p;
        this.AVERAGE_PAYOUT = p / h;
    }

    @Override
    public String toString()
    {
        return String.format("%5d, %5d, %15.3f, %15.3f\n", NUMBER, HITS, PAYOUTS, AVERAGE_PAYOUT);
    }

    @Override
    public int compareTo(ReportLine that)
    {
        if(this.HITS < that.HITS)
        {
            return 1;
        } else
        {
            if(this.HITS > that.HITS)
            {
                return -1;
            } else
            {
                if(this.NUMBER < that.NUMBER)
                {
                    return -1;
                } else
                {
                    if(this.NUMBER > that.NUMBER)
                    {
                        return 1;
                    } else
                    {
                        return 0;
                    }
                }
            }
        }
    }
}
