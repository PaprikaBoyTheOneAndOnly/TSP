package com.bbw.ch;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;


public class TravelingSalesmanTest {
    private TravelingSalesman testee;

    @Before
    public void setUp() {
        this.testee = new TravelingSalesman();
    }

    @Test
    public void testPermutation() {
        List<List<String>> ways = new ArrayList<>();
        Set<String> set = new HashSet<>(Arrays.asList("A", "B", "C"));

        testee.permutations(set, new Stack<>(), set.size(), ways);

        assertThat(ways, containsInAnyOrder(
                Arrays.asList("A", "C", "B"),
                Arrays.asList("A", "B", "C"),
                Arrays.asList("B", "A", "C")
        ));
    }

    @Test
    public void testSmallestWay() {
        double smallesWay = testee.getSmallestWay(Arrays.asList(
                new Node(1,1, "A"),
                new Node(1,7, "B"),
                new Node(5,7, "C"),
                new Node(5,1, "E")
        ));
        assertThat(smallesWay, is(20.));
        assertThat(testee.getPointsSmallestWay(), IsIterableContainingInOrder.contains("A", "B", "C", "E", "A"));
    }

    @Test
    public void testSmallestWay_withASmallAmountOfNodes() {
        double smallestWay = testee.getSmallestWay(Arrays.asList(
                new Node(1,1, "A"),
                new Node(1,7, "B"),
                new Node(5,1, "E")
        ));
        assertThat(Math.round(smallestWay *1000.)/1000., is(17.211));
    }
}