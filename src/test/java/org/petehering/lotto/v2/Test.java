package org.petehering.lotto.v2;

import java.util.Objects;

public abstract class Test
{
    private final String name;
    
    public Test(String name)
    {
        this.name = Objects.requireNonNull(name);
    }
    
    public abstract boolean test() throws Exception;

    public String getName()
    {
        return name;
    }
}
