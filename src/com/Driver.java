package com;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Driver {

    public static void main(String[] args) {
        // Diego: It takes about 3-4 minutes to run on my computer.
        double l1 = System.nanoTime() / 1000.0 / 1000.0 / 1000.0;
        // Create the pie graph for part 1
        String fileNamePart1 = "20150801.as2types.txt";
        FileReader reader = new FileReader(fileNamePart1);
        reader.MakePieChartFile("part1.csv");
        System.out.println("Done with part 1");
        reader.clear();
        // Read 20170901.as-rel2.txt and routeviews-rv2-20171105-1200.pfx2as for part 2
        String topologyFile = "20170901.as-rel2.txt";
        reader.readTopologyFile(topologyFile);
        // Write the degree of each AS
        reader.writeNodeDegree("part2.csv");
        // Create the pie graph for part 2
        reader.AddIPInformation("routeviews-rv2-20171105-1200.pfx2as");
        // Make histogram based on IP information
        reader.MakeHistogram("part3.csv");
        // Make pie chart to classify AS
        reader.MakeTypePieChart("part4.csv");
        System.out.println("Done with part 2");

        // Find max clique for part 3
        reader.MakeCliqueTable("part5.csv");
        System.out.println("Done with part 3");

        reader.CreateCustomerCone("part6.csv", "part7.csv");
        System.out.println("Done with extra credit");

        double l2 = System.nanoTime() / 1000.0 / 1000.0 / 1000.0;
        System.out.println("Took " + (l2 - l1) + " seconds");
    }



}
