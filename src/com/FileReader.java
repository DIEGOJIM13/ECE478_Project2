package com;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    private List<AS> ASList;

    public FileReader(String fileName) {
        this.ASList = Collections.emptyList();
        try {
            Stream<String> stream = Files.lines(Paths.get(fileName));
            this.ASList = stream
                    .filter(l -> l.charAt(0) != '#')
                    .filter(l -> l.split("\\|").length == 3)
                    .map(l -> new AS(l.split("\\|")[0], l.split("\\|")[1], l.split("\\|")[2]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Exception Happened");
            e.printStackTrace();
        }
    }

    public void MakePieChartFile(String fileName) {
        Set<String> temp = new HashSet<>();
        this.ASList.forEach(as -> temp.add(as.getType()));

        Map<String, Integer> data = new HashMap<>();
        temp.forEach(type -> data.put(type, 0));
        this.ASList.forEach(as -> data.put(as.getType(), data.get(as.getType()) + 1));
        List<String> outputLine = data.entrySet().stream().map(set -> set.getKey() + "," + set.getValue().toString() + "\n").collect(Collectors.toList());
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
            outputLine.forEach(printWriter::print);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void clear() {
        this.ASList = Collections.emptyList();
    }

    public List<AS> getASList() {
        return ASList;
    }

    public void setASList(List<AS> ASList) {
        this.ASList = ASList;
    }


    public void readTopologyFile(String topologyFile) {
        this.ASList = Collections.emptyList();
        try {
            final Stream<String> stream = Files.lines(Paths.get(topologyFile));
            // Make sure they have more than 3 parts to it and then make a list of String arrays.
            final List<String[]> split = stream
                    .filter(l -> l.charAt(0) != '#')
                    .filter(l -> l.split("\\|").length >= 3)
                    .map(l -> l.split("\\|"))
                    .collect(Collectors.toList());

            final Set<Integer> allASSet = new HashSet<>();
            // Add all possible AS identifiers to a set.
            for (String[] line : split){
                allASSet.add(Integer.parseInt(line[0]));
                allASSet.add(Integer.parseInt(line[1]));
            }
            // Convert that set to a list of AS and sort
            final List<String> allASList = (List<String>) new ArrayList(allASSet).stream()
                    .map(as -> as.toString())
                    .collect(Collectors.toList());
            Collections.sort(allASList);
            this.ASList = allASList.stream()
                    .map(as -> new AS(as))
                    .collect(Collectors.toList());
            Map<String, AS> asMap = new HashMap<>();

            // Make a map that relates the identifier ot the object
            for (int i = 0; i < allASList.size(); i++){
                asMap.put(allASList.get(i), this.ASList.get(i));
            }
            for (String[] line : split){
                if (Objects.equals(line[2], "0")){
                    // p2p link: <peer-AS>|<peer-AS>| 0 |<source>
                    // Make both peers of each other
                    asMap.get(line[0]).addToPeers(asMap.get(line[1]));
                    asMap.get(line[1]).addToPeers(asMap.get(line[0]));
                }
                else if (Objects.equals(line[2], "-1")) {
                    // p2c link: <provider-AS>|<customer-AS>| -1 |<source>
                    // Add the second to customers of first and first as provider of second.
                    asMap.get(line[0]).addToCustomers(asMap.get(line[1]));
                    asMap.get(line[1]).addToProviders(asMap.get(line[0]));
                }
                else {
                    System.out.println("Other not found: " + line[2]);
                }
            }
            Collections.sort(this.ASList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNodeDegree(String fileName) {
        List<String> outputLine = this.ASList.stream()
                .filter(as -> (as.getCustomers().size() + as.getPeers().size()) > 0)
                .map(as -> as.getASIdentifier() + "," + (as.getCustomers().size() + as.getPeers().size()))
                .collect(Collectors.toList());
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
            outputLine.forEach(printWriter::println);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
