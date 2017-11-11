package com;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;

public class AS
{
    private String ASIdentifier;
    private String Source;
    private String Type;
    private ArrayList<String> ip;
    private ArrayList<Integer> networkLength;
    private ArrayList<AS> peers;
    private ArrayList<AS> customers;
    private ArrayList<AS> providers;
    private BigInteger networkSize;
    private ArrayList<AS> inCustomerCone;

    public AS(String ASIdentifier){
        this.ASIdentifier = ASIdentifier;
        this.Source = null;
        this.Type = null;
        this.peers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.providers = new ArrayList<>();
        this.ip = new ArrayList<>();
        this.networkLength = new ArrayList<>();
        this.inCustomerCone = new ArrayList<>();
        networkSize = BigInteger.valueOf(0);
//        PrintAS();
    }

    public AS(String ASIdentifier, String Source, String Type){
        this.ASIdentifier = ASIdentifier;
        this.Source = Source;
        this.Type = Type;
        this.peers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.providers = new ArrayList<>();
        this.ip = new ArrayList<>();
        this.networkLength = new ArrayList<>();
        this.inCustomerCone = new ArrayList<>();
        networkSize = BigInteger.valueOf(0);
//        PrintAS();
    }

    public void addToPeers(AS as){
        this.peers.add(as);
    }

    public void addToCustomers(AS as){
        this.customers.add(as);
    }

    public void addToProviders(AS as){
        this.providers.add(as);
    }

    public void PrintAS(){
        System.out.println("AS with:");
        if (this.ASIdentifier != null){
            System.out.println("\tIdentifier:\t\t" + this.ASIdentifier);
        }
        if (this.Source != null){
            System.out.println("\tSource:\t\t\t" + this.Source);
        }
        if (this.Type != null){
            System.out.println("\tType:\t\t\t" + this.Type);
        }
        if (this.peers.size() > 0){
            System.out.println("\tPeers:\t\t\t" + peers.size());
        }
        if (this.customers.size() > 0){
            System.out.println("\tCustomers:\t\t" + customers.size());
        }
        if (this.providers.size() > 0){
            System.out.println("\tProviders:\t\t" + providers.size());
        }
        if (this.peers.size() > 0 || this.customers.size() > 0 || this.providers.size() > 0){
            System.out.println("\tDegree:\t\t\t" + (providers.size() + customers.size() + peers.size()));
        }
        if (this.ip.size() != 0){
            System.out.println("\tIP:\t\t\t\t" + this.ip);
        }
        if (this.networkLength.size() != 0){
            System.out.println("\tNetwork Length:\t" + this.networkLength);
        }
        if (this.networkSize.intValue() != 0){
            System.out.println("\tNetwork Size:\t" + this.networkSize);
        }
    }

    public String getASIdentifier() {
        return ASIdentifier;
    }

    public void setASIdentifier(String ASIdentifier) {
        this.ASIdentifier = ASIdentifier;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public ArrayList<AS> getPeers() {
        return peers;
    }

    public void setPeers(ArrayList<AS> peers) {
        this.peers = peers;
    }

    public ArrayList<AS> getCustomers() {
        return customers;
    }

    public ArrayList<AS> getProviders() {
        return providers;
    }

    public void setCustomers(ArrayList<AS> customers) {
        this.customers = customers;
    }

    public ArrayList<String> getIp() {
        return ip;
    }

    public void setIp(ArrayList<String> ip) {
        this.ip = ip;
    }

    public ArrayList<Integer> getNetworkLength() {
        return networkLength;
    }

    public void setNetworkLength(ArrayList<Integer> networkLength) {
        this.networkLength = networkLength;
    }

    public void addIP(String ip) {
        this.ip.add(ip);
    }

    public void addNetworkLength(int networkLength){
        this.networkLength.add(networkLength);
    }

    public BigInteger getNetworkSize() {
        return networkSize;
    }

    public void setNetworkSize(BigInteger networkSize) {
        this.networkSize = networkSize;
    }

    public void addToNetworkSize(int i) {
        BigInteger a = BigInteger.valueOf(2).pow(32);
        this.networkSize = this.networkSize.add(BigInteger.valueOf(2).pow(32-i));
        if (this.networkSize.compareTo(BigInteger.valueOf(2).pow(32)) > 0){
            System.out.println(this.networkSize);

        }
    }

    public static Comparator<AS> ASIdentifierCompare = new Comparator<AS>() {

        public int compare(AS as1, AS as2) {
            return Integer.compare(Integer.parseInt(as1.ASIdentifier), Integer.parseInt(as2.getASIdentifier()));
        }
    };

    public static Comparator<AS> DegreeCompare = new Comparator<AS>() {

        public int compare(AS as1, AS as2) {
            return Integer.compare(as2.peers.size() + as2.customers.size() + as2.providers.size(),
                    as1.peers.size() + as1.customers.size() + as1.providers.size());
        }
    };

    public boolean ConnectedTo(AS next) {
        if (this.peers.contains(next)){
            return true;
        }
        else if (this.customers.contains(next)){
            return true;
        }
        else if (this.providers.contains(next)){
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<AS> getInCustomerCone() {
        return inCustomerCone;
    }

    public void setInCustomerCone(ArrayList<AS> inCustomerCone) {
        this.inCustomerCone = inCustomerCone;
    }
}
