package test;

import algorithms.Searching;
import algorithms.Sorting;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class SearchingTest {
    public static int[] arr;
    public static int[] sortedArr;
    private int value;
    private int binarySeachExpected;
    private int[] linearSeachExpected;

    public SearchingTest(int value, int binarySeachExpected, int[] linearSeachExpected) {
        this.value = value;
        this.binarySeachExpected = binarySeachExpected;
        this.linearSeachExpected = linearSeachExpected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> conditions() {
        return Arrays.asList(new Object[][] {
                {5, 3, new int[]{0, 2, 3, 4, 6}},
                {7, 5, new int[]{0, 3}},
                {30, -1, null},
                {-2, -1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}},
                {12, 8, null}
        });
    }

    @BeforeClass
    public static void setUp() throws Exception {
        arr =new int[]{ 12, 2, 7, 9, 6, 3, 7, 5, 3};
        //{ 2, 3, 3, 5, 6, 7, 7, 9, 12};
        sortedArr = Arrays.copyOf(arr, arr.length);
        Sorting.quickSort(sortedArr, 0, arr.length -1);
    }


    @org.junit.Test
    public void linearSearch() {
        assertArrayEquals(linearSeachExpected, Searching.linearSearch(arr, value));
    }
    @org.junit.Test
    public void binarySearch() {
        assertEquals(binarySeachExpected, Searching.binarySearch(sortedArr, 0, arr.length-1, value));
    }
}