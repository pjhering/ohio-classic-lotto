package org.petehering.lotto.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

class Play
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
    
    final Date date;
    final int n1;
    final int n2;
    final int n3;
    final int n4;
    final int n5;
    final int n6;
    final int kicker;
    final double jackpot;
    final int payout;
    
    Play(Date date, int n1, int n2, int n3, int n4, int n5, int n6, int kicker, double jackpot, int payout)
    {
        this.date = Objects.requireNonNull(date);
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;
        this.n5 = n5;
        this.n6 = n6;
        this.kicker = kicker;
        this.jackpot = jackpot;
        this.payout = payout;
    }
    
    @Override
    public String toString()
    {
        return sdf.format(date) +
                ", " + n1 +
                ", " + n2 +
                ", " + n3 +
                ", " + n4 +
                ", " + n5 +
                ", " + n6 +
                ", " + kicker +
                ", " + String.format("$%,-14.2f", jackpot) +
                ", " + String.format("$%,-14.2f", 0.0 + payout);
    }
}
