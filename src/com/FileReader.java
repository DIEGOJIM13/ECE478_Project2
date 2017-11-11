package com;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    
    public void MakeTypePieChart(String fileName) {
    	int numEnterprise = 0;
    	int numContent = 0;
    	int numTransit = 0;
    	
    	for (AS as : this.ASList) {
    		if(as.getCustomers().size() == 0 && as.getPeers().size() > 0) {
    		    as.setType("Content");
    			numContent++;
    		}
    		if(as.getCustomers().size() > 0) {
    			numTransit++;
                as.setType("Transit");
            }
    		if((as.getProviders().size()) < 2 && as.getCustomers().size() == 0 && as.getPeers().size() == 0) {
    			numEnterprise++;
                as.setType("Enterprise");
            }
    	}
    	
    	try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
            printWriter.println("Content," + numContent);
            printWriter.println("Enterprise," + numEnterprise);
            printWriter.println("Transit," + numTransit);
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
            Collections.sort(this.ASList, AS.ASIdentifierCompare);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNodeDegree(String fileName) {
        List<String> outputLine = this.ASList.stream()
                .filter(as -> (as.getCustomers().size() + as.getPeers().size() + as.getProviders().size()) > 0)
                .map(as -> as.getASIdentifier() + "," + (as.getCustomers().size() + as.getPeers().size() + as.getProviders().size()))
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

    public void AddIPInformation(String fileName) {
        try {
            final List<String> inputData = Files.lines(Paths.get(fileName)).collect(Collectors.toList());

            // Make a map that relates the identifier ot the object
            Map<String, AS> asMap = new HashMap<>();
            this.ASList.forEach(as -> asMap.put(as.getASIdentifier(), as));

            for (String line : inputData){
                String[] split = line.split("\\s+");
                if (asMap.containsKey(split[2]) && !split[2].contains("_")){
                    // System.out.println(split[2]);
                    asMap.get(split[2]).addIP(split[0]);
                    asMap.get(split[2]).addToNetworkSize(Integer.parseInt(split[1]));
                    asMap.get(split[2]).addNetworkLength(Integer.parseInt(split[1]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void MakeHistogram(String fileName) {
        final List<BigInteger> bigIntegers = this.getASList().stream()
                .map(as -> as.getNetworkSize())
                .collect(Collectors.toList());
        Collections.sort(bigIntegers);
        final List<String> outputLine = bigIntegers.stream()
                .map(BigInteger::toString)
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

    public void MakeCliqueTable(String fileName) {
        this.ASList.sort(AS.DegreeCompare);
        // Find clique
        ArrayList<AS> clique = new ArrayList<>();
        clique.add(this.ASList.get(0));
        this.ASList.remove(0);
        while (true){
            final AS next = this.ASList.get(0);
            final List<AS> notIncluded = clique.stream().filter(as -> !as.ConnectedTo(next)).collect(Collectors.toList());
            if (notIncluded.size() != 0){
                break;
            }
            else{
                clique.add(next);
                this.ASList.remove(0);
            }
        }
        final List<String> outputLine = clique.stream()
                .map(as -> as.getASIdentifier() + ","
                        + (as.getPeers().size() + as.getCustomers().size() + as.getProviders().size()))
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

    public void CreateCustomerCone(String fileName1, String fileName2) {
        for (AS as : this.ASList) {
            final Set<AS> asSet = customerAS(as);
            ArrayList<AS> asArrayList = new ArrayList<>(asSet);
            as.setInCustomerCone(asArrayList);
            asArrayList.sort(AS.ASIdentifierCompare);
        }
        ArrayList<TableData> tableData = new ArrayList<>();
        BigInteger totalASes = BigInteger.valueOf(this.ASList.size());
        BigInteger totalIPPrefix = BigInteger.ZERO;
        for (AS as : this.ASList){
            totalIPPrefix = totalIPPrefix.add(BigInteger.valueOf(as.getIp().size()));
        }
        BigInteger totalIPs = BigInteger.valueOf(2).pow(32);
        for (AS as : this.ASList) {
            int asNumber = Integer.parseInt(as.getASIdentifier());
            String asName = as.getType();
            int asDegree = as.getCustomers().size() + as.getPeers().size() + as.getProviders().size();
            BigInteger numASes = BigInteger.valueOf(as.getInCustomerCone().size());
            BigInteger numIPPrefix = BigInteger.ZERO;
            for (AS as1 : as.getInCustomerCone()){
                numIPPrefix = numIPPrefix.add(BigInteger.valueOf(as1.getIp().size()));
            }
            BigInteger numIPs = BigInteger.ZERO;
            for (AS as1 : as.getInCustomerCone()){
                numIPs = numIPs.add(as1.getNetworkSize());
            }
            double percentASes = 100 * numASes.doubleValue() / totalASes.doubleValue();
            double percentIPPrefix = 100 * numIPPrefix.doubleValue() / totalIPPrefix.doubleValue();
            double percentIPs = 100 * numIPs.doubleValue() / totalIPs.doubleValue();
            tableData.add(new TableData(asNumber, asName, asDegree, numASes, numIPPrefix, numIPs, percentASes, percentIPPrefix, percentIPs));
        }

        tableData.sort(TableData.CompareNumASes);
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName1));
            printWriter.println(TableData.StringHeaderInfo());
            for (int i = 0; i < 15; i++){
                printWriter.println(tableData.get(i).StringTableData());
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }

        tableData.sort(TableData.ComparePercentageIPs);
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName2));
            printWriter.println(TableData.StringHeaderInfo());
            for (int i = 0; i < 15; i++){
                printWriter.println(tableData.get(i).StringTableData());
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public Set<AS> customerAS(AS as){
        Set<AS> asSet = new HashSet<>();
        asSet.add(as);
        if (as.getCustomers().size() == 0){
            asSet.add(as);
            return asSet;
        }
        else{
            for (AS individualAS : as.getCustomers()){
                Set<AS> as1 = customerAS(individualAS);
                asSet.addAll(as1);
            }
            return asSet;
        }
    }

}
