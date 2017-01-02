package org.petehering.lotto.v2;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class AnalyzerTest extends Test{

    private final Analyzer analyzer;
    private final URL url;

    public AnalyzerTest(String name, String path, Analyzer analyzer)
    {
        super(name);
        this.analyzer = Objects.requireNonNull(analyzer);
        this.url = AnalyzerTest.class.getResource(path);
    }
    @Override
    public boolean test() throws Exception
    {
        List<Draw> draws = Draw.parse(url);
        Report r = analyzer.analyze(draws);
        r.print(System.out);
        return true;
    }
}
