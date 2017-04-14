package org.petehering.lotto.v3;

import java.io.File;
import java.io.FileWriter;
import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import static org.petehering.lotto.v3.NodeComparator.Order.HIT_COUNT_DESC;
import static org.petehering.lotto.v3.NodeComparator.Order.VALUE_DESC;

public class Main
{

    private static final TreeSet<Draw> DRAWS = new TreeSet<>();
    private static final TreeMap<Integer, Node> NODES = new TreeMap<>();
    private static final List<Node> LIST = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        for(String arg : args)
        {
            out.println(arg);
            parseDraws(arg);
        }
        saveBackupFile();
        buildNodes();
        analyzeNodes();
        reportFindings();
    }

    private static void parseDraws(String arg) throws Exception
    {
        try
        {
            CsvDrawParser p = new CsvDrawParser(new File(arg));
            TreeSet<Draw> set = p.parse();
            DRAWS.addAll(set);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void buildNodes()
    {
        for(Draw d : DRAWS)
        {
            double pay = d.getPayout() / 6;
            int[] nums = d.getNumbers();

            for(int i1 = 0; i1 < nums.length; i1++)
            {
                if(!NODES.containsKey(nums[i1]))
                {
                    NODES.put(nums[i1], new Node(nums[i1]));
                }

                Node n1 = NODES.get(nums[i1]);
                n1.addToHitCount(1);
                n1.addToPayout(pay);

                for(int i2 = 0; i2 < nums.length; i2++)
                {
                    if(!NODES.containsKey(nums[i2]))
                    {
                        NODES.put(nums[i2], new Node(nums[i2]));
                    }

                    Node n2 = NODES.get(nums[i2]);

                    n1.addToHitsWith(n2.getNumber());
                    n2.addToHitsWith(n1.getNumber());
                }
            }
        }
    }

    private static void analyzeNodes()
    {
        NODES.forEach((k, v) ->
        {
            Node n = NODES.get(k);
            
            for(int i = 1; i < 50; i++)
            {
                int hw = v.getHitWith(i);
                v.addToValue(NODES.get(i).getHitCount() * hw);
            }
        });
        
        LIST.addAll(NODES.values());
    }

    private static void reportFindings()
    {
        out.println("\nhit count ascending:");
        NodeComparator.Order HIT_COUNT_DEC;
        Collections.sort(LIST, new NodeComparator(HIT_COUNT_DESC));
        for(int i = 0; i < 6; i++)
        {
            out.print(LIST.get(i).getNumber());
            out.print(" ");
        }
        out.println();
        
        out.println("\nvalue ascending:");
        Collections.sort(LIST, new NodeComparator(VALUE_DESC));
        for(int i = 0; i < 6; i++)
        {
            out.print(LIST.get(i).getNumber());
            out.print(" ");
        }
        out.println();
    }

    private static void saveBackupFile() throws Exception
    {
        String date = new SimpleDateFormat("MM_dd_yyyy").format(new Date());
        File file = new File("ohio-classic-lotto-backup_" + date + ".csv");
        FileWriter writer = new FileWriter(file);
        
        for(Draw d : DRAWS)
        {
            writer.write(d.toString());
            writer.write("\n");
        }
        
        writer.flush();
        writer.close();
    }
}
