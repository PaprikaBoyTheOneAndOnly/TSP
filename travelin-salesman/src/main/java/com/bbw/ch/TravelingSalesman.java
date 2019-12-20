package com.bbw.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class TravelingSalesman {
    private List<String> points;

    public TravelingSalesman() {
        this.points = new ArrayList<>();
    }

    public double getSmallestWay(List<Node> nodeList) {
        if (nodeList.size() > 3) {
            Map<String, Node> nodes = new HashMap<>();

            for (int i = 1; i < nodeList.size(); i++) {
                nodes.put(nodeList.get(i).getName(), nodeList.get(i));
            }

            Set<String> set = new HashSet<>(nodes.keySet());
            List<List<String>> ways = new ArrayList<>();

            this.permutations(set, new Stack<>(), set.size(), ways);
            Node startNode = nodeList.get(0);
            nodes.put(startNode.getName(), startNode);
            ways.forEach(way -> {
                way.add(startNode.getName());
                way.add(0, startNode.getName());
            });

            return calculateWay(ways, ways.get(0), 0, nodes);
        } else {
            double way = 0;
            for (int i = 0; i < nodeList.size() - 1; i++) {
                way += nodeList.get(i).calcWay(nodeList.get(i + 1));
            }
            this.points = nodeList.stream().map(Node::getName).collect(Collectors.toList());
            return way;
        }
    }

    public double calculateWay(List<List<String>> ways, List<String> wayPoints, double smallestWay, Map<String, Node> nodes) {
        double thisWay = 0;

        for (int i = 0; i < wayPoints.size(); i++) {
            if (i != wayPoints.size() - 1) {
                String node = wayPoints.get(i);
                String neighbour = wayPoints.get(i + 1);

                thisWay += nodes.get(node).calcWay(nodes.get(neighbour));
            }
        }

        int index = ways.indexOf(wayPoints) + 1;
        if (index < ways.size()) {
            if (smallestWay == 0) {
                this.points = wayPoints;
                smallestWay = calculateWay(ways, ways.get(index), thisWay, nodes);
            } else {
                double way = calculateWay(ways, ways.get(index), smallestWay, nodes);
                if(smallestWay > way) {
                    this.points = wayPoints;
                }
                smallestWay = Math.min(smallestWay, way);
            }
        } else {
            return Math.min(smallestWay, thisWay);
        }

        return smallestWay;
    }

    public void permutations(Set<String> nodes, Stack<String> permutation, int size, List<List<String>> ways) {
        if (permutation.size() == size && !isAlreadyDefined(permutation.toArray(new String[0]), ways)) {
            ways.add(new ArrayList<>(permutation));
        }
        String[] availableNodes = nodes.toArray(new String[0]);
        for (String node : availableNodes) {
            /* add current item */
            permutation.push(node);

            /* remove item from available item set */
            nodes.remove(node);

            /* pass it on for next permutation */
            permutations(nodes, permutation, size, ways);

            /* pop and put the removed item back */
            nodes.add(permutation.pop());
        }
    }

    private boolean isAlreadyDefined(String[] way, List<List<String>> ways) {
        for (List<String> w : ways) {
            boolean same = true;
            for (int i = 0; i < w.size(); i++) {
                if (!w.get(i).equals(way[w.size() - 1 - i])) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return true;
            }
        }
        return false;
    }

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }
}
