package com.bbw.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class App {
    public static void main(String[] args) {
        TravelingSalesman ts = new TravelingSalesman();
        Set<String> s = new HashSet<>();
        s.add("2");
        s.add("3");
        s.add("4");
        s.add("5");
        Map<String, Node> nodes = new HashMap<>();
        nodes.put("2", new Node(2, 3, "2"));
        nodes.put("3", new Node(3, 3, "3"));
        nodes.put("4", new Node(1, 2, "4"));
        nodes.put("5", new Node(4, 1, "5"));

        System.out.println(nodes.keySet());
        System.out.println(s);
        List<List<String>> ways = new ArrayList<>();
        ts.permutations(s, new Stack<>(), s.size(), ways);
        nodes.put("1", new Node(1, 1, "1"));
        ways.forEach(way -> {
            way.add("1");
            way.add(0, "1");
        });

        System.out.println("Smallest way: " + ts.calculateWay(ways, ways.get(0), 0, nodes));
    }
}
