package com.airshen.test;

import com.airshen.SeatAllocator;
import org.junit.Assert;
import org.junit.Test;

public class TestSeatAllocator {

    @Test
    public void testFillSeats() {

        int[][] expectedPlane =
                {{19, 25, 1, 2, 26, 27, 3, 4, 5, 6, 28, 20},
                {21, 29, 7, 8, 30, 0, 9, 10, 11, 12, 0, 22},
                {0, 0, 0, 13, 0, 0, 14, 15, 16, 17, 0, 23},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 24}};

        int segmnts = 4;
        int[][] limit = {{2,3},{3,4},{3,2},{4,3}};  // In the given problem, rows and columns are interchanged.
        int passengers = 30;

        int[][] resultedPlane = new SeatAllocator().fillSeats(segmnts, limit, passengers);

        Assert.assertArrayEquals(expectedPlane, resultedPlane);
    }

    @Test
    public void testFillSeats1() {

        int[][] expectedPlane =
                {{18, 25, 1, 2, 26, 27, 3, 4, 28, 19},
                {20, 29, 5, 6, 30, 31, 7, 8, 32, 21},
                {22, 33, 9, 10, 34, 35, 11, 12, 36, 23},
                {0, 0, 0, 13, 37, 38, 14, 15, 39, 24},
                {0, 0, 0, 16, 40, 41, 17, 0, 0, 0}};

        int segmnts = 3;
        int[][] limit = {{3,3},{5,4},{4,3}};    // In the given problem, rows and columns are interchanged.
        int passengers = 100;

        int[][] resultedPlane = new SeatAllocator().fillSeats(segmnts, limit, passengers);

        Assert.assertArrayEquals(expectedPlane, resultedPlane);
    }
}
