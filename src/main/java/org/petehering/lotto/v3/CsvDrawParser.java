package org.petehering.lotto.v3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* constructor takes a directory as an argument and parse all
   files that look like "ClassicLotto_*.csv"
*/
public class CsvDrawParser
{

    private final File ROOT;
    private final FilenameFilter FILTER = new FilenameFilter()
    {
        private final Pattern GOOD = Pattern.compile("^ClassicLotto_.*csv$");
        
        @Override
        public boolean accept(File dir, String name)
        {
            Matcher m = GOOD.matcher(name);
            return m.matches();
        }
    };
    
    public CsvDrawParser(File dir) throws Exception
    {
        if(dir.isDirectory())
        {
            this.ROOT = dir;
        }
        else
        {
            throw new Exception("expected a directory");
        }
    }
    
    public TreeSet<Draw> parse() throws Exception
    {
        TreeSet<Draw> set = new TreeSet<>();
        File[] files = ROOT.listFiles(FILTER);
        
        for(File f : files)
        {
            set.addAll(parse(f));
        }
        return set;
    }

    private Collection<? extends Draw> parse(File f) throws Exception
    {
        TreeSet<Draw> set = new TreeSet<>();
        FileReader reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        String line = null;
        int num = 1;
        
        while((line = buffer.readLine()) != null)
        {
            try
            {
               set.add(parse(line));
            }
            catch(Exception ex)
            {
                System.out.printf("%s, line %d, %s%n", f.getName(), num, ex.getMessage());
            }
            finally
            {
                num += 1;
            }
        }
        
        return set;
    }

    private Draw parse(String line)
    {
        return null;//TODO
    }
}
