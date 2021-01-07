package com.test;

import java.util.Scanner;

public class TestMain1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // inputs and initial calculations part
        System.out.print("Enter number of segments:");
        int segmnts = scanner.nextInt();
        scanner.nextLine();

        int[][] limit = new int[segmnts][2];
        int lastRow = 0, totalColumn = 0; int numOfSeats = 0;

        for(int seg = 0; seg < segmnts; seg++) {
            System.out.printf("Enter segment %d row and column (r,c):", seg + 1);
            String[] rowAndCol = scanner.nextLine().split(",");
            limit[seg][0] = Integer.parseInt(rowAndCol[1]);
            limit[seg][1] = Integer.parseInt(rowAndCol[0]);
            // In the given problem, rows and columns are interchanged.

            lastRow = Math.max(lastRow, limit[seg][0]);
            totalColumn += limit[seg][1];
            numOfSeats += limit[seg][0] * limit[seg][1];
        }

        int[][] plane = new int[lastRow][totalColumn];

        System.out.print("Enter number of Passengers:");
        int passengers = scanner.nextInt();
        scanner.nextLine();

        // seat filling part
        int seatType = 1;   // initially aisle
        int passenger = 1;
        while (passenger <= passengers && passenger <= numOfSeats) {
            for(int row = 0; row < lastRow && passenger <= passengers; row++) {
                int segStart = 0;
                for(int seg = 0; seg < segmnts && passenger <= passengers; seg++) {

                    if(seg > 0) {
                        segStart += limit[seg-1][1];  // index where the segment starts
                    }

                    if(row < limit[seg][0]) {

                        int segEnd = segStart + limit[seg][1] - 1;  // index where the segment ends
                        switch (seatType) {
                            case SeatType.aisle:
                                if(seg == 0) {  // first segment has aisle on last column
                                    plane[row][limit[seg][1] - 1] = passenger++;
                                } else if(seg == segmnts-1) {   // last segment has aisle on first column
                                    plane[row][segStart] = passenger++;
                                } else {    // other segments have aisles on first and last columns
                                    plane[row][segStart] = passenger++;
                                    if(passenger <= passengers) {
                                        plane[row][segEnd] = passenger++;
                                    }
                                }
                                break;

                            case SeatType.window:
                                if(seg == 0) {  // first segment has window on first column
                                    plane[row][0] = passenger++;
                                } else if(seg == segmnts-1) {   // last segment has window on last column
                                    plane[row][segEnd] = passenger++;
                                }
                                break;

                            case SeatType.middle:   // middle seats start from segStart+1 and ends at segEnd-1
                                for(int col = segStart + 1; col < segEnd && passenger <= passengers; col++) {
                                    plane[row][col] = passenger++;
                                }
                        }
                    }
                }
            }
            seatType++;
        }

        // printing part
        System.out.println("\nW - Window seats");
        System.out.println("M - Middle seats");
        System.out.println("A - Aisle seats");
        System.out.println("_ - Empty seats");
        System.out.println("\nThe Plane looks like:\n");

        for(int row = -1; row < lastRow; row++) {
            int segmnt = 0, segStart = 0;
            for(int col = 0; col < totalColumn; col++) {
                if(col == 0 || col == segStart + limit[segmnt][1]) {
                    if(row == -1) {
                        System.out.print("  ");
                    } else {
                        System.out.print("| ");
                    }
                    if(col > 0) {
                        segStart += limit[segmnt][1];
                        segmnt++;
                    }
                }
                if(row == -1) { // seat title row
                    int segEnd = segStart + limit[segmnt][1] - 1;
                    if(col == 0 || col == totalColumn-1) {
                        System.out.print(" W ");
                    } else if(col == segStart || col == segEnd) {
                        System.out.print(" A ");
                    } else {
                        System.out.print(" M ");
                    }
                    continue;
                }
                if(row < limit[segmnt][0]) {
                    if(plane[row][col] > 0) {
                        System.out.print(plane[row][col] < 10 ? " "+plane[row][col]+" " :  plane[row][col]+" ");
                    } else {
                        System.out.print("__ ");  // empty seat.
                    }
                } else {
                    System.out.print("   ");  // There is no seat.
                }
            }
            if(row == -1) {
                System.out.println();
            } else {
                System.out.println("|");
            }
        }
        if(passengers > numOfSeats) {
            System.out.printf("Sorry! There are no seats available for %d passengers.", passengers - numOfSeats);
        }
        scanner.close();
    }
}

class SeatType {
    static final int aisle = 1;
    static final int window = 2;
    static final int middle = 3;
}
