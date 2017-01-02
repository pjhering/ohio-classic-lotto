package org.petehering.lotto.v2;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
{

    public static void main(String[] args)
    {
        Class c = Main.class;
        
        for(String arg : args)
        {
            try
            {
                AnalysisSuite
                        .build(arg)
                        .add(new HitFrequency())
                        .add(new ColumnCount())
                        .add(new TotalPayout())
                        .add(new CountHitsWith())
                        .run(System.out);
            }
            catch(IOException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(ParseException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
