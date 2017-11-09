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
        // Read 20170901.as-rel2.txt for part 2
        String topologyFile = "20170901.as-rel2.txt";
        reader.readTopologyFile(topologyFile);
        reader.writeNodeDegree("part2.csv");

        // Create the pie graph for part 2
        reader.AddIPInformation("routeviews-rv2-20171105-1200.pfx2as");
        reader.MakeHistogram("part3.csv");
        reader.MakeTypePieChart("part4.csv");
        
        

    }

}
