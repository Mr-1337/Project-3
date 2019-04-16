/*
 * Created on Jan 27, 2005
 * File: RandomUtil.java
 * Project: CIS362 Improving Sort Algorithm
 * Author: danielsd 
 * 
 */
package com.brackeen.javagamebook.util;

import java.util.Random;

/**
 * @author danielsd
 *
 */
public class RandomUtil {

    public static Random rand = new Random(System.currentTimeMillis());
    
    public static void main(String[] args) {
    }
    public static int getRandomInt(){
        return rand.nextInt();
    }
    /**
     * 
     * @param n max value possible
     * @return a random int from 0 to n
     */
    public static int getRandomInt(int n){
        return rand.nextInt(n);
    }
    /**
     * 
     * @param low - lowest integer in potential range
     * @param high highest integer in potential range
     * @return
     */
    public static int getRandomInt(int low, int high){
        
        int randBetween = getRandomInt(high-low) + low;
        return randBetween;
    }
}
