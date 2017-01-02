package org.petehering.lotto.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Draw
{

    private static final NumberFormat cfmt = NumberFormat.getCurrencyInstance();
    private static final DateFormat dfmt = DateFormat.getDateInstance(DateFormat.SHORT);
    private final Date date;
    private final int[] numbers;
    private final int kicker;
    private final double jackpot;
    private final double payout;

    public Draw(Date date, int[] numbers, int kicker, double jackpot, double payout)
    {
        this.date = Objects.requireNonNull(date);
        this.numbers = Objects.requireNonNull(numbers);
        this.kicker = kicker;
        this.jackpot = jackpot;
        this.payout = payout;
    }

    public static List<Draw> parse(BufferedReader reader) throws ParseException, IOException
    {
        List<Draw> list = new ArrayList<>();
        String line;

        while((line = reader.readLine()) != null)
        {
            if(!line.startsWith("Play Date"))
            {
                list.add(parse(line));
            }
        }

        return list;
    }

    public static List<Draw> parse(Reader reader) throws ParseException, IOException
    {
        return parse(new BufferedReader(reader));
    }

    public static List<Draw> parse(InputStream stream) throws ParseException, IOException
    {
        return parse(new InputStreamReader(stream));
    }

    public static List<Draw> parse(URL url) throws IOException, ParseException
    {
        return parse(url.openStream());
    }

    public static Draw parse(String csvText) throws ParseException
    {
        return parse(csvText.split("\\s*,\\s*"));
    }

    public static Draw parse(String[] fields) throws ParseException
    {
        Date date = dfmt.parse(fields[0]);
        int[] numbers = new int[]
        {
            Integer.parseInt(fields[1]),
            Integer.parseInt(fields[2]),
            Integer.parseInt(fields[3]),
            Integer.parseInt(fields[4]),
            Integer.parseInt(fields[5]),
            Integer.parseInt(fields[6])
        };
        int kicker = Integer.parseInt(fields[7]);
        double jackpot = Double.parseDouble(fields[8].replace("$", ""));
        double payout = Double.parseDouble(fields[9]);

        return new Draw(date, numbers, kicker, jackpot, payout);
    }

    public Date getDate()
    {
        return date;
    }

    public int[] getNumbers()
    {
        return numbers;
    }

    public int getKicker()
    {
        return kicker;
    }

    public double getJackpot()
    {
        return jackpot;
    }

    public double getPayout()
    {
        return payout;
    }
    
    @Override
    public String toString()
    {
        return new StringBuilder()
                .append(dfmt.format(date)).append(", ")
                .append(numbers[0]).append(", ")
                .append(numbers[1]).append(", ")
                .append(numbers[2]).append(", ")
                .append(numbers[3]).append(", ")
                .append(numbers[4]).append(", ")
                .append(numbers[5]).append(", ")
                .append(cfmt.format(jackpot)).append(", ")
                .append(cfmt.format(payout))
                .toString();
    }
}
