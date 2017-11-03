package com;

public class Driver {

    public static void main(String[] args) {
        // Create the pie graph for part 1
        String fileNamePart1 = "20150801.as2types.txt";
        FileReader reader = new FileReader(fileNamePart1);
        reader.MakePieChartFile("part1.txt");

        reader.clear();
        // Read other file
        String topologyFile = "20170901.as-rel2.txt";
        reader.readTopologyFile(topologyFile);

    }

}
