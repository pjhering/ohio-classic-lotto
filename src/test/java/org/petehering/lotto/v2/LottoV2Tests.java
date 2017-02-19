package org.petehering.lotto.v2;

public class LottoV2Tests
{

    public static void main(String[] args)
    {
        String goodFile1 = "/ClassicLotto_2017_1_1.csv";
        String goodFile2 = "/ClassicLotto_2017_2_8.csv";
        String badDate = "/bad_date_ClassicLotto_2017_1_1.csv";
        String badInt = "/bad_int_ClassicLotto_2017_1_1.csv";
        String badDbl = "/bad_dbl_ClassicLotto_2017_1_1.csv";
        
        TestSuite.build("org.petehering.lotto.v2")
                .add(new ParseTest("bad date format", badDate))
                .add(new ParseTest("bad int format", badInt))
                .add(new ParseTest("bad double format", badDbl))
                .add(new ParseTest("good cvs file", goodFile2))
                .add(new AnalyzerTest("column count", goodFile2, new ColumnCount()))
                .add(new AnalyzerTest("count hits with", goodFile2, new CountHitsWith()))
                .add(new AnalyzerTest("hit frequency", goodFile2, new HitFrequency()))
                .add(new AnalyzerTest("total payout", goodFile2, new TotalPayout()))
                .run();
    }
}
