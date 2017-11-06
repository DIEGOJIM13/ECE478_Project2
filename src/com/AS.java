package com;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AS implements Comparable<AS>
{
    private String ASIdentifier;
    private String Source;
    private String Type;
    private ArrayList<AS> peers;
    private ArrayList<AS> customers;
    private ArrayList<AS> providers;

    public AS(String ASIdentifier){
        this.ASIdentifier = ASIdentifier;
        this.Source = null;
        this.Type = null;
        this.peers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.providers = new ArrayList<>();
//        PrintAS();
    }

    public AS(String ASIdentifier, String Source, String Type){
        this.ASIdentifier = ASIdentifier;
        this.Source = Source;
        this.Type = Type;
        this.peers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.providers = new ArrayList<>();
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
            System.out.println("\tIdentifier:\t" + this.ASIdentifier);
        }
        if (this.Source != null){
            System.out.println("\tSource:\t\t" + this.Source);
        }
        if (this.Type != null){
            System.out.println("\tType:\t\t" + this.Type);
        }
        if (this.peers.size() > 0){
            System.out.println("\tPeers:\t\t" + peers.size());
        }
        if (this.customers.size() > 0){
            System.out.println("\tCustomers:\t" + customers.size());
        }
        if (this.providers.size() > 0){
            System.out.println("\tProviders:\t" + providers.size());
        }
        if (this.peers.size() > 0 || this.customers.size() > 0 || this.providers.size() > 0){
            System.out.println("\tDegree:\t\t" + (providers.size() + customers.size() + peers.size()));
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

    public void setCustomers(ArrayList<AS> customers) {
        this.customers = customers;
    }

    @Override
    public int compareTo(AS o) {
        return Integer.compare(Integer.parseInt(this.ASIdentifier), Integer.parseInt(o.getASIdentifier()));
    }
}
