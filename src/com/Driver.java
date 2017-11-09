package com;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args) {
        // Create the pie graph for part 1
        String fileNamePart1 = "20150801.as2types.txt";
        FileReader reader = new FileReader(fileNamePart1);
        reader.MakePieChartFile("part1.csv");
        
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
        
        // Find max clique for part 3
        reader.MakeCliqueTable("part5.csv");

    }



}
