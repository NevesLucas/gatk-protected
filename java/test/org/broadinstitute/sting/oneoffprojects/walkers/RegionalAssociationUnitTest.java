package org.broadinstitute.sting.oneoffprojects.walkers;

import org.broadinstitute.sting.oneoffprojects.walkers.association.statistics.casecontrol.UStatistic;
import org.broadinstitute.sting.oneoffprojects.walkers.association.statistics.casecontrol.ZStatistic;
import org.broadinstitute.sting.utils.MathUtils;
import org.broadinstitute.sting.BaseTest;
import org.broadinstitute.sting.oneoffprojects.walkers.association.AssociationTestRunner;
import org.broadinstitute.sting.oneoffprojects.walkers.association.statistics.casecontrol.TStatistic;
import org.broadinstitute.sting.utils.collections.Pair;
import org.broadinstitute.sting.utils.pileup.ReadBackedPileup;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.broadinstitute.sting.BaseTest;
import org.testng.Assert;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Ghost
 * Date: 3/5/11
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegionalAssociationUnitTest extends BaseTest {
    @BeforeClass
    public void init() { }

    @Test
    private void testTStatistics() {
        logger.warn("Testing T statistics");
        TTest test1 = new TTest();
        test1.setCaseData((Collection) Arrays.asList(1,1,2,3,4));
        test1.setControlData((Collection) Arrays.asList(10, 10, 20, 30, 40));
        Assert.assertEquals(AssociationTestRunner.testStudentT(test1).second,0.1702,1e-2);
        TTest test2 = new TTest();
        test2.setCaseData((Collection) Arrays.asList(5, 6, 5, 2, 3, 8, 7, 12, 10, 6, 4, 2, 8, 7, 3));
        test2.setControlData((Collection) Arrays.asList(1, 6, 7, 2, 3, 3, 4, 1, 2, 5, 7, 3, 10, 3, 3, 2, 3));
        Assert.assertEquals(AssociationTestRunner.testStudentT(test2).second, 0.5805, 1e-2);
        TTest test3 = new TTest();
        test3.setCaseData((Collection) Arrays.asList(94,25,68,4,27,51,9,10,91,61,61,37,39,44,36,27,86,33,3,38,5,6,28,93,30,56,81,8,40,44));
        test3.setControlData((Collection) Arrays.asList(6,64,96,85,20,74,93,18,31,20,88,38,80,50,33,81,35,8,2,69,49,6,26,74,79,63,63,96,45,18));
        Assert.assertEquals(AssociationTestRunner.testStudentT(test3).second,0.8229,1e-4);
        TTest test4 = new TTest();
        test4.setCaseData((Collection) Arrays.asList(14,8,8,17,8,12,10,10,13,9,13,9,9,12,12,11,16,12,13,16,10,13,11,16,13,16,11,13,9,16,16,14,9,14,17,10,15,15,9,15,17,15,17,12,10,13,11,14,8,14));
        test4.setControlData((Collection) Arrays.asList(7,1,4,2,3,7,8,5,5,4,10,6,4,9,2,9,9,3,3,10,1,8,9,5,3,7,2,7,10,9,4,9,2,10,10,3,2,3,4,4,5,10,9,4,3,5,6,10,5,10));
        Assert.assertEquals(AssociationTestRunner.testStudentT(test4).second,0.1006,1e-4);
        Assert.assertEquals(AssociationTestRunner.testStudentT(test4).first,1.657989,1e-6);
    }

    @Test
    private void testZStatistics() {
        logger.warn("Testing Z statistics");
        ZTest test1 = new ZTest();
        test1.setCaseData(new Pair<Number,Number>(100,500));
        test1.setControlData(new Pair<Number,Number>(55,300));
        Assert.assertEquals(AssociationTestRunner.testZ(test1).first,0.57742362050306,2e-6);
        Assert.assertEquals(AssociationTestRunner.testZ(test1).second,0.56367,2e-5);
        ZTest test2 = new ZTest();
        test1.setCaseData(new Pair<Number, Number>(1020, 1800));
        test1.setControlData(new Pair<Number, Number>(680, 1670));
        Assert.assertEquals(AssociationTestRunner.testZ(test1).first,9.3898178216531,2e-6);
        ZTest test3 = new ZTest();
        test3.setCaseData(new Pair<Number,Number>(20,60));
        test3.setControlData(new Pair<Number,Number>(30,80));
        Assert.assertEquals(AssociationTestRunner.testZ(test3).first,-0.50917511840392,2e-6);
        Assert.assertEquals(AssociationTestRunner.testZ(test3).second,0.610643593,2e-4);
    }

    @Test
    private void testUStatistic() {
        logger.warn("Testing U statistics");
        UTest test1 = new UTest();
        test1.setCaseData((Collection) Arrays.asList(2,4,5,6,8));
        test1.setControlData((Collection) Arrays.asList(1,3,7,9,10,11,12,13));
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test1).first,10,0);
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test1).second,0.092292,5e-2); // z-approximation, off by about 0.05
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test1).second,0.044444,1e-3); // recursive calculation
        UTest test2 = new UTest();
        test2.setCaseData((Collection) Arrays.asList(1,7,8,9,10,11,15,18));
        test2.setControlData((Collection) Arrays.asList(2,3,4,5,6,12,13,14,16,17));
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test2).first,37,0);
        UTest test3 = new UTest();
        test3.setCaseData((Collection)Arrays.asList(13,14,7,18,5,2,9,17,8,10,3,15,19,6,20,16,11,4,12,1));
        test3.setControlData((Collection) Arrays.asList(29,21,14,10,12,11,28,19,18,13,7,27,20,5,17,16,9,23,22,26));
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test3).first,93,0);
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test3).second,2*0.00302,1e-3);
        UTest test4 = new UTest();
        test4.setCaseData((Collection) Arrays.asList(1,2,4,5,6,9));
        test4.setControlData((Collection) Arrays.asList(3,8,11,12,13));
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test4).first,5,0);
        Assert.assertEquals(AssociationTestRunner.mannWhitneyUTest(test4).second,0.0303,1e-4);

    }


    private class TTest extends TStatistic {
        Map<Cohort,Collection<Number>> toTest = new HashMap<Cohort,Collection<Number>>(2);

        @Override
        public Map<Cohort,Collection<Number>> getCaseControl() {
            return toTest;
        }

        public void setCaseData(Collection<Number> data) {
            toTest.put(Cohort.CASE,data);
        }

        public void setControlData(Collection<Number> data) {
            toTest.put(Cohort.CONTROL,data);
        }

        public Collection<Number> map(ReadBackedPileup rbp) { return null; }
        public int getWindowSize() { return 1; }
        public int slideByValue() { return 1; }
        public boolean usePreviouslySeenReads() { return false; }
    }

    private class ZTest extends ZStatistic {
        Map<Cohort,Pair<Number,Number>> toTest = new HashMap<Cohort,Pair<Number,Number>>(2);

        @Override
        public Map<Cohort,Pair<Number,Number>> getCaseControl() {
            return toTest;
        }

        public void setCaseData(Pair<Number,Number> data) {
            toTest.put(Cohort.CASE,data);
        }

        public void setControlData(Pair<Number,Number> data) {
            toTest.put(Cohort.CONTROL,data);
        }

        public Pair<Number,Number> map(ReadBackedPileup p) { return null; }
        public int getWindowSize() { return 1; }
        public int slideByValue() { return 1; }
        public boolean usePreviouslySeenReads() { return true; }
    }

    private class UTest extends UStatistic {
        TTest test = new TTest();
        public boolean usePreviouslySeenReads() { return false; }
        public int getWindowSize() { return 1; }
        public int slideByValue() { return 1; }
        public Collection<Number> map(ReadBackedPileup p ){ return null; }

        @Override
        public Map<Cohort,Collection<Number>> getCaseControl() {
            return test.getCaseControl();
        }

        public void setCaseData(Collection<Number> data) { test.setCaseData(data);}
        public void setControlData(Collection<Number> data) { test.setControlData(data); }
    }
}