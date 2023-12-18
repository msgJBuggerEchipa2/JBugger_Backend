package com.msgsystems.jbugger.echipa2.backend.utils;

import java.util.*;

public class Graph<T> {
    private final HashMap<T, ArrayList<T>> graph;

    public Graph(){

        graph = new HashMap<>();

    }
    public void addNode(T node){
        if(!graph.containsKey(node)) {
            ArrayList<T> emptyList = new ArrayList<T>();
            graph.put(node, emptyList);
        }
    }
    public void addEdge(T from, T to){
        if(isNode(from)) {
            if(!isEdge(from, to)) {

                ArrayList<T> arr = graph.get(from);
                arr.add(to);

                graph.put(from, arr);

            }
        }
    }

    public void addNodeList(HashSet<T> nodes){
        for(T node: nodes
        ) {
            addNode(node);
        }
    }
    public boolean isEdge(T from, T to){
        if(graph.containsKey(from))
            if(graph.get(from).contains(to))
                return true;
        return false;
    }

    public boolean isNode(T node){
        return this.graph.containsKey(node);
    }
    public void printGraph(){
        System.out.println("OUTBOUND NODES");
        for (T elem: graph.keySet()
             ) {
            System.out.print(elem.toString() + ": ");
            for (T outbound: graph.get(elem)
                 ) {
                System.out.print(outbound.toString() + " | ");
            }
            System.out.print("\n");
        }
    }
}