package org.petehering.lotto.v2;

public class LottoV2Tests
{

    public static void main(String[] args)
    {
        String goodFile = "/ClassicLotto_2017_1_1.csv";
        String badDate = "/bad_date_ClassicLotto_2017_1_1.csv";
        String badInt = "/bad_int_ClassicLotto_2017_1_1.csv";
        String badDbl = "/bad_dbl_ClassicLotto_2017_1_1.csv";
        
        TestSuite.build("org.petehering.lotto.v2")
                .add(new ParseTest("bad date format", badDate))
                .add(new ParseTest("bad int format", badInt))
                .add(new ParseTest("bad double format", badDbl))
                .add(new ParseTest("good cvs file", goodFile))
                .add(new AnalyzerTest("column count", goodFile, new ColumnCount()))
                .add(new AnalyzerTest("count hits with", goodFile, new CountHitsWith()))
                .add(new AnalyzerTest("hit frequency", goodFile, new HitFrequency()))
                .add(new AnalyzerTest("total payout", goodFile, new TotalPayout()))
                .run();
    }
}
