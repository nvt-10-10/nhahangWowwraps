package com.example.demo.model.data;

import java.util.*;

public class Test {


    public static int countSubarraysWithSumAndSize(int[] arr, int targetSum, int size) {
        int count = 0;
        int currentSum = 0;

        for (int endIndex = 0; endIndex < arr.length; endIndex++) {
            currentSum += arr[endIndex];

            System.out.println(currentSum+"\t"+count);
            if (endIndex >= size - 1) {
                System.out.println(currentSum+"\t"+targetSum);
                if (currentSum == targetSum) {
                    count++;
                }
                currentSum -= arr[endIndex - size + 1];
            }
        }

        return count;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    if (i+i+i==27&&i+i*j==27&&j+k*i==56)
                        System.out.println("k/j*i="+(k/j*i));
                }
            }
        }

    }
}
