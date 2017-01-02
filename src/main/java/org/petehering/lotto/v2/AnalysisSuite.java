package org.petehering.lotto.v2;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AnalysisSuite
{

    private final List<Draw> draws;
    private final List<Analyzer> analysers;

    private AnalysisSuite(URL url) throws IOException, ParseException
    {
        draws = Draw.parse(url);
        this.analysers = new ArrayList<>();
    }

    public static AnalysisSuite build(String path) throws IOException, ParseException
    {
        URL url = AnalysisSuite.class.getResource(path);
        return new AnalysisSuite(url);
    }

    public AnalysisSuite add(Analyzer analyser)
    {
        this.analysers.add(analyser);
        return this;
    }

    public void run(PrintStream out)
    {
        analysers.forEach(a ->
        {
            try
            {
                System.out.printf("begin %s%n", a.getName());
                
                Report r = a.analyze(draws);
                r.print(out);
                
                System.out.printf("end %s%n%n", a.getName());
            }
            catch(Exception ex)
            {
                System.out.printf("%s threw %s - %s%n%n",
                        a.getName(),
                        ex.getClass().getName(),
                        ex.getMessage());
            }
        });
    }
}
