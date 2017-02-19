package org.petehering.lotto.v3;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node>
{

    public static enum Order
    {
        HIT_COUNT_ASC,
        HIT_COUNT_DESC,
        PAYOUT_ASC,
        PAYOUT_DESC,
        VALUE_ASC,
        VALUE_DESC
    }
    
    private final Order order;
    
    public NodeComparator(Order order)
    {
        this.order = (order == null)? Order.HIT_COUNT_ASC: order;
    }
    
    @Override
    public int compare(Node n1, Node n2)
    {
        switch(order)
        {
            case HIT_COUNT_ASC:
                return n1.getHitCount() - n2.getHitCount();
                
            case HIT_COUNT_DESC:
                return n2.getHitCount() - n1.getHitCount();
                
            case PAYOUT_ASC:
                return (int)(n1.getPayout() - n2.getPayout());
                
            case PAYOUT_DESC:
                return (int)(n2.getPayout() - n1.getPayout());
                
            case VALUE_ASC:
                return n1.getValue() - n2.getValue();
                
            case VALUE_DESC:
                return n2.getValue() - n1.getValue();
                
            default:
                return n1.getHitCount() - n2.getHitCount();
        }
    }
}
