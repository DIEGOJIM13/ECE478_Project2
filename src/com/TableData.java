package com;

import java.math.BigInteger;
import java.util.Comparator;

public class TableData {
    private int asNumber;
    private String asName;
    private int asDegree;
    private BigInteger numASes, numIPPrefix, numIPs;
    private double percentASes, percentIPPrefix, percentIPs;

    public TableData(int asNumber, String asName, int asDegree, BigInteger numASes, BigInteger numIPPrefix, BigInteger numIPs, double percentASes, double percentIPPrefix, double percentIPs) {
        this.asNumber = asNumber;
        this.asName = asName;
        this.asDegree = asDegree;
        this.numASes = numASes;
        this.numIPPrefix = numIPPrefix;
        this.numIPs = numIPs;
        this.percentASes = percentASes;
        this.percentIPPrefix = percentIPPrefix;
        this.percentIPs = percentIPs;
    }

    public int getAsNumber() {
        return asNumber;
    }

    public String getAsName() {
        return asName;
    }

    public int getAsDegree() {
        return asDegree;
    }

    public BigInteger getNumASes() {
        return numASes;
    }

    public BigInteger getNumIPPrefix() {
        return numIPPrefix;
    }

    public BigInteger getNumIPs() {
        return numIPs;
    }

    public double getPercentASes() {
        return percentASes;
    }

    public double getPercentIPPrefix() {
        return percentIPPrefix;
    }

    public double getPercentIPs() {
        return percentIPs;
    }

    public void PrintTableData(){
        System.out.println(String.format("ASNumber: %d\tASType:%s\tASDegree:%d\tnumASes:%s\tnumIPPrefix:%s\tnumIPs:%s\tpercentageASes:%f\tpercentageIPPrefix:%f\tpercentageIPs:%f", asNumber, asName, asDegree, numASes, numIPPrefix, numIPs, percentASes, percentIPPrefix, percentIPs));
    }

    public String StringTableData(){
        return String.format("%d,%s,%d,%s,%s,%s,%f,%f,%f", asNumber, asName, asDegree, numASes, numIPPrefix, numIPs, percentASes, percentIPPrefix, percentIPs);
    }

    public static String StringHeaderInfo(){
        return String.format("ASNumber,ASType,ASDegree,numASes,numIPPrefix,numIPs,percentageASes,percentageIPPrefix,percentageIPs");
    }

    public static Comparator<TableData> CompareNumASes = new Comparator<TableData>() {
        public int compare(TableData td1, TableData td2) {
            return td2.getNumASes().compareTo(td1.getNumASes());
        }
    };

    public static Comparator<TableData> ComparePercentageIPs = new Comparator<TableData>() {
        public int compare(TableData td1, TableData td2) {
            return Double.compare(td2.getPercentIPs(), td1.getPercentIPs());
        }
    };
}
