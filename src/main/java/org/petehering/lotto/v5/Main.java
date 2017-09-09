package org.petehering.lotto.v5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeSet;

public class Main
{
    private static File root;
    private static LocalDate query;
    
    public static void main(String[] args) throws Exception
    {
        parse(args);
        
        CsvDrawParser csv = new CsvDrawParser(root);
        TreeSet<Draw> draws = csv.parse();
        saveBackupFile (draws);
        
        main (draws);
    }
    
    public static void main (TreeSet<Draw> draws)
    {
        Page[] pages = new Page[50];
        for (int i = 0; i < pages.length; i++)
        {
            pages[i] = new Page (i);
        }
        
        for (Draw d : draws)
        {
            int[] nums = d.getNumbers();
            
            for (int i = 0; i < nums.length; i++)
            {
                Page p1 = pages[nums[i]];
                
                for (int j = 0; j < nums.length; j++)
                {
                    Page p2 = pages[nums[j]];
                    
                    p1.addLinkTo(p2);
                }
            }
        }
        
        for (int i = 0; i < 4; i++)
        {
            for (Page p : pages)
            {
                p.pass(i);
            }
        }
        
        Arrays.sort(pages);
        
        for (Page p : pages)
        {
            out.println (p);
        }
    }
    
    private static void parse(String[] args)
    {
        for(String arg : args)
        {
            if(arg.startsWith("r="))
            {
                root = new File(arg.substring(2));
            }
            else if(arg.startsWith("q="))
            {
                query = LocalDate.parse(arg.substring(2));
            }
        }
        
        if(root == null)
        {
            root = new File(".");
        }
        
        if(query == null)
        {
            query = LocalDate.now();
        }
    }

    private static void saveBackupFile(TreeSet<Draw> draws)
    {
            Date today = new Date ();
            SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy");
            File backup = new File("backup-" + fmt.format(today) + ".csv");
            
        try (PrintStream out = new PrintStream (new FileOutputStream (backup)))
        {
            draws.forEach(d ->
            {
                d.printTo(out);
            });
            
            out.flush ();
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
}
