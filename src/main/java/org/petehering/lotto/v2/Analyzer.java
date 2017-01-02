package org.petehering.lotto.v2;

import java.util.List;
import java.util.Objects;

public abstract class Analyzer
{

    private final String name;

    public Analyzer(String name)
    {
        this.name = Objects.requireNonNull(name);
    }

    public String getName()
    {
        return name;
    }

    public abstract Report analyze(List<Draw> draws) throws Exception;
}
