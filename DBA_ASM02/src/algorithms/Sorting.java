package algorithms;

import file_service.FileService;
import utils.Utils;

import java.util.Random;

public class Sorting {
    public static Random rn = new Random();


    public static void swap(int[] arr, int k, int l) {
        int temp = arr[k];
        arr[k] = arr[l];
        arr[l] = temp;
    }

    // Buble Sort
    public static String bubleSort(int[] arr) {
        StringBuilder output = new StringBuilder();
        output.append("Original array:%n");
        output.append(Utils.arrayFormatedString(arr));
        int step = 1;
        boolean swaped = true;
        for (int i =0; i< arr.length; i++) {
            swaped = false;
            for(int j =0; j< arr.length-i-1; j++) {
                if(arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                    swaped = true;
                }
            }
            output.append(Utils.showStep(arr, step));
            step++;
            if (swaped == false) {
                break;
            }

        }
//        FileService.writeFile(FileService.BUBBLE_SORT_OUTPUT, output.toString());
        return output.toString();

    }

    // Selection Sort
    public static String selectionSort(int[] arr) {
        StringBuilder output = new StringBuilder();
        output.append("Original array:%n");
        output.append(Utils.arrayFormatedString(arr));
        int step = 1;
        for(int i = 0; i < arr.length-1; i++) {
            int mId = i;
            for(int j = i+1;j< arr.length; j++) {
                if(arr[j]<arr[mId]) {
                    mId =j;
                }
            }
            swap(arr, mId, i);
            output.append(Utils.showStep(arr, step));
            step++;
        }
//        FileService.writeFile(FileService.SELECTION_SORT_OUTPUT, output.toString());
        return output.toString();
    }

    // Insert Sort
    public static String insertSort(int[] arr) {
        StringBuilder output = new StringBuilder();
        output.append("Original array:%n");
        output.append(Utils.arrayFormatedString(arr));
        int step = 1;
        for(int i  =1; i< arr.length; i++) {
            int j = i;
            while(j>0) {
                if(arr[j]< arr[j-1]) {
                    swap(arr, j, j-1);
                    j--;
                } else {
                    break;
                }
            }

//            showStep(arr, step);
            output.append(Utils.showStep(arr, step));
            step++;
        }
//        FileService.writeFile(FileService.INSERTION_SORT_OUTPUT, output.toString());
        return output.toString();
    }

    // Merge Sort

    public static void mergeSort(int[] arr, int l, int r) {
        if(l< r) {
            int m  = (l+r)/2;
            mergeSort(arr, l, m);
            mergeSort(arr,m+1, r);
            merge(arr, l, m, r);
        }
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int[] arr1 = new int[m-l+1];
        int[] arr2 = new int[r-m];
        System.arraycopy(arr, l, arr1, 0, arr1.length);
        System.arraycopy(arr, m+1, arr2, 0, arr2.length);
        int i =0, j=0, k =l;
        while (i< arr1.length && j< arr2.length) {
            if(arr1[i]< arr2[j]) {
                arr[k] = arr1[i];
                i++;
            } else {
                arr[k] = arr2[j];
                j++;
            }
            k++;
        }
        while (i< arr1.length) {
            arr[k] = arr1[i];
            k++;
            i++;
        }
        while (j< arr2.length) {
            arr[k] = arr2[j];
            k++;
            j++;
        }
    }

    // Quick Sort
    public static void quickSort(int[] arr, int l, int r) {
        if(l< r) {
            int pInd = rn.nextInt(r-l+1) +l;
            int[] p = partition(arr, l, pInd, r);
            quickSort(arr, l, p[0]-1);
            quickSort(arr, p[1]+1, r);
        }
    }
    protected static int[] partition( int[] arr, int l, int pId, int r) {
        // chuyen phan tu pivot len dau
        swap(arr, pId, l);
        // chia mang lam 3 phan p1 = {x< pivot}, p2 = {x = pivot}, p3 = { x> pivot}
        // cho lap tu i = l+1 -> r neu ar[i} < pivot thi chuyen ve ben trai, neu lon hon thi chuyen ve ben phai
        int m1 = l, m2 = r, k = l+1;
        // m1, m2 lan luot la index cua phan tu dau vaf cuoi cua p2
        while(k<=m2) {
            if(arr[k]< arr[m1]) {
                swap(arr, k, m1);
                k++;
                m1++;
            } else if(arr[k]> arr[m1]) {
                swap(arr, k, m2);
                m2--;
            } else {
                k++;
            }
        }
        int[] partitions = {m1, m2};
        return partitions;
    }








    //-------------------------------------------------------------------------------------------------------
    //using for version 2
    /*
    // Buble Sort
    public static void bubleSort(int[] arr) {
        StringBuilder output = new StringBuilder();
        output.append("Original array:%n");
        output.append(Utils.arrayFormatedString(arr));
        int step = 1;
        boolean swaped = true;
        for (int i =0; i< arr.length; i++) {
            swaped = false;
            for(int j =0; j< arr.length-i-1; j++) {
                if(arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                    swaped = true;
                }
            }
            output.append(Utils.showStep(arr, step));
            step++;
            if (swaped == false) {
                break;
            }

        }
        FileService.writeFile(FileService.BUBBLE_SORT_OUTPUT, output.toString());
    }

    // Selection Sort
    public static void selectionSort(int[] arr) {
        StringBuilder output = new StringBuilder();
        output.append("Original array:%n");
        output.append(Utils.arrayFormatedString(arr));
        int step = 1;
        for(int i = 0; i < arr.length-1; i++) {
            int mId = i;
            for(int j = i+1;j< arr.length; j++) {
                if(arr[j]<arr[mId]) {
                    mId =j;
                }
            }
            swap(arr, mId, i);
            output.append(Utils.showStep(arr, step));
            step++;
        }
        FileService.writeFile(FileService.SELECTION_SORT_OUTPUT, output.toString());
    }

    // Insert Sort
    public static void insertSort(int[] arr) {
        StringBuilder output = new StringBuilder();
        output.append("Original array:%n");
        output.append(Utils.arrayFormatedString(arr));
        int step = 1;
        for(int i  =1; i< arr.length; i++) {
            int j = i;
            while(j>0) {
                if(arr[j]< arr[j-1]) {
                    swap(arr, j, j-1);
                    j--;
                } else {
                    break;
                }
            }
            output.append(Utils.showStep(arr, step));
            step++;
        }
        FileService.writeFile(FileService.INSERTION_SORT_OUTPUT, output.toString());
    }

     */



}
