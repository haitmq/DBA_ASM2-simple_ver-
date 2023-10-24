package test;

import algorithms.Sorting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

@RunWith(Parameterized.class)
public class SortingTest {

    public int[] arr;
    public int[] expectedSortedArr;

    int[] initialArr;

    public SortingTest(int[] arr, int[] expectedSortedArr) {
        this.arr = arr;
        this.expectedSortedArr = expectedSortedArr;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> conditions() {
        return Arrays.asList(new Object[][] {
                {new int[]{ 12, 2, 7, 9, 6, 3, 7, 5, 3}, new int[]{ 2, 3, 3, 5, 6, 7, 7, 9, 12}},
                {new int[]{1}, new int[]{1}},
                {new int[]{2,2,2,2,2}, new int[]{2,2,2,2,2}},
                {new int[]{4,5,6,7,8,9,10}, new int[]{4,5,6,7,8,9,10}},
                {new int[]{9, 5,5,5,5,5,5,5,5,5,5,5,6}, new int[]{ 5,5,5,5,5,5,5,5,5,5,5,6, 9}}
        });
    }

    @Before
    public void setUp() throws Exception {
        initialArr = Arrays.copyOf(arr, arr.length);
    }

    @Test
    public void bubleSort() {
        Sorting.bubleSort(initialArr);
        assertArrayEquals(expectedSortedArr, initialArr);
    }

    @Test
    public void selectionSort() {
        Sorting.selectionSort(initialArr);
        assertArrayEquals(expectedSortedArr, initialArr);
    }

    @Test
    public void insertSort() {
        Sorting.insertSort(initialArr);
        assertArrayEquals(expectedSortedArr, initialArr);
    }
}