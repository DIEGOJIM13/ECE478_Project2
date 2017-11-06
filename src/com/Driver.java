package com;

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
//      reader.getASList().forEach(a -> System.out.println(a.getASIdentifier() + " "));

    }

}
