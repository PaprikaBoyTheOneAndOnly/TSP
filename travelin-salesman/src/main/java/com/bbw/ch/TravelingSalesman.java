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
    private List<String> pointsSmallestWay;

    public TravelingSalesman() {
        this.pointsSmallestWay = new ArrayList<>();
    }

    /**
     * searches the smallest way of given nodes
     * @param nodeList all existing nodes
     * @return the smallest way
     */
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
            for (int i = 0; i < nodeList.size(); i++) {
                if(i + 1 == nodeList.size()) {
                    way += nodeList.get(i).calcWay(nodeList.get(0));
                } else {
                    way += nodeList.get(i).calcWay(nodeList.get(i + 1));
                }
            }
            this.pointsSmallestWay = nodeList.stream().map(Node::getName).collect(Collectors.toList());
            return way;
        }
    }

    /**
     * calculates the way depend on the way-points
     * @param ways which are already defined
     * @param wayPoints current way to check
     * @param smallestWay current smallest way
     * @param nodes all existing nodes
     * @return the current way
     */
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
                this.pointsSmallestWay = wayPoints;
                smallestWay = calculateWay(ways, ways.get(index), thisWay, nodes);
            } else {
                double way = calculateWay(ways, ways.get(index), smallestWay, nodes);
                if(smallestWay > way) {
                    this.pointsSmallestWay = wayPoints;
                }
                smallestWay = Math.min(smallestWay, way);
            }
        } else {
            return Math.min(smallestWay, thisWay);
        }

        return smallestWay;
    }

    /**
     * Creates all possible combination of ways.
     * Ways are ignored if a way in reversed order is already defined.
     * @param nodes set of all Points
     * @param permutation current permutation
     * @param size total size of nodes
     * @param ways list which saves all generated ways
     */
    public void permutations(Set<String> nodes, Stack<String> permutation, int size, List<List<String>> ways) {
        if (permutation.size() == size && !isAlreadyDefined(permutation.toArray(new String[0]), ways)) {
            ways.add(new ArrayList<>(permutation));
        }
        String[] availableNodes = nodes.toArray(new String[0]);
        for (String node : availableNodes) {
            permutation.push(node);
            nodes.remove(node);
            permutations(nodes, permutation, size, ways);
            nodes.add(permutation.pop());
        }
    }

    /**
     * checks if a way is already defined
     * ignores if a way in revered order is already defined
     * @param way which has to be checked
     * @param ways already defined ways
     * @return if way is already defined
     */
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

    /**
     * @return the points of the last calculated smallest way.
     */
    public List<String> getPointsSmallestWay() {
        return pointsSmallestWay;
    }
}
