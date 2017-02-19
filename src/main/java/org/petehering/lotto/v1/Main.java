package org.petehering.lotto.v1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");

    public static void main(String[] args)
    {
        if (args.length > 0)
        {
            for (String path : args)
            {
                System.out.println("input: " + path);

                if (path.endsWith(".csv"))
                {
                    try
                    {
                        process(path);
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                } else
                {
                    System.out.println("skipping: " + path);
                }
                
                System.out.println();
            }
        } else
        {
            System.out.println("expecting Ohio Classic Lotto CSV file");
        }
    }
    
    private static void process(String path) throws Exception
    {
        List<Play> plays = new ArrayList<>();
        FileReader file = new FileReader(path);
        int count = 1;

        try (BufferedReader reader = new BufferedReader(file))
        {
            String line = reader.readLine();

            if (!line.startsWith("Play Date"))
            {
                plays.add(createPlay(count, line));
            }

            while ((line = reader.readLine()) != null)
            {
                count += 1;
                plays.add(createPlay(count, line));
            }
        }
        
        for(ReportLine line : generateReport(plays))
        {
            System.out.print(line);
        }
        
    }
    
    private static List<ReportLine> generateReport(List<Play> plays)
    {
        Map<Integer, Integer> hits = new HashMap<>();
        Map<Integer, Double> pays = new HashMap<>();
        
        for(Play p : plays)
        {
            addHits(hits, p);
            addPays(pays, p);
        }
        
        List<ReportLine> report = new ArrayList<>();
        
        for(Integer num : hits.keySet())
        {
            int n = num.intValue();
            
            int h = hits.containsKey(num)?
                    hits.get(num).intValue():
                    0;
            
            double p = pays.containsKey(num)?
                    pays.get(num).doubleValue():
                    0.0;
            
            ReportLine line = new ReportLine(n, h, p);
            report.add(line);
        }
        
        Collections.sort(report);
        
        return report;
    }
    
    private static void addHits(Map<Integer, Integer> hits, Play play)
    {
        addHit(hits, play.n1);
        addHit(hits, play.n2);
        addHit(hits, play.n3);
        addHit(hits, play.n4);
        addHit(hits, play.n5);
        addHit(hits, play.n6);
    }
    
    private static void addHit(Map<Integer, Integer> hits, int n)
    {
        if(!hits.containsKey(n))
        {
            hits.put(n, 1);
        } else
        {
            hits.put(n, hits.get(n) + 1);
        }
    }
    
    private static void addPays(Map<Integer, Double> pays, Play play)
    {
        addPay(pays, play.n1, play.payout);
        addPay(pays, play.n2, play.payout);
        addPay(pays, play.n3, play.payout);
        addPay(pays, play.n4, play.payout);
        addPay(pays, play.n5, play.payout);
        addPay(pays, play.n6, play.payout);
    }
    
    private static void addPay(Map<Integer, Double> pays, int n, double p)
    {
        if(!pays.containsKey(n))
        {
            pays.put(n, p / 6);
        } else
        {
            pays.put(n, pays.get(n) + (p / 6));
        }
    }
    
    private static Play createPlay(int count, String line) throws Exception
    {
        String[] field = line.split("\\s*,\\s*");

        if (field.length != 10)
        {
            throw new Exception("line " + count + ": improper format");
        } else
        {
            Date date = sdf.parse(field[0]);
            int n1 = Integer.parseInt(field[1]);
            int n2 = Integer.parseInt(field[2]);
            int n3 = Integer.parseInt(field[3]);
            int n4 = Integer.parseInt(field[4]);
            int n5 = Integer.parseInt(field[5]);
            int n6 = Integer.parseInt(field[6]);
            int kicker = Integer.parseInt(field[7]);
            double jackpot = Double.parseDouble(field[8].replaceAll("\\$", ""));
            int payout = Integer.parseInt(field[9]);

            return new Play(date, n1, n2, n3, n4, n5, n6, kicker, jackpot, payout);
        }
    }
}
