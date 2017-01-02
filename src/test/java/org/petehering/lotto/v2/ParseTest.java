package org.petehering.lotto.v2;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ParseTest extends Test{

    private final String path;
    
    public ParseTest(String name, String path)
    {
        super(name);
        this.path = Objects.requireNonNull(path);
    }
    
    @Override
    public boolean test() throws Exception
    {
        URL url = getClass().getResource(path);
        List<Draw> draws = Draw.parse(url);
        draws.forEach(d -> System.out.println(d));
        return true;
    }
}
