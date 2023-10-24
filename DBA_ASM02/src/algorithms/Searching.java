package algorithms;

import java.util.ArrayList;
import java.util.List;

public class Searching {
    public static int[] linearSearch(int[] arr, int value) {
        // tao list luu index cac gia tri thoa man
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i< arr.length; i++) {
            if(arr[i]> value) {
                list.add(i);
            }
        }
        if(list.size()>0) {
            return list.stream().mapToInt(Integer::intValue).toArray();
        }
        return null;
    }

    // can sap xep mang truoc khi search(ta co the sort trong phan binarySearch hoac o phan chuc nang trong mainProgram)
    public static int binarySearch(int[] arr, int l,int r, int value) {
        if (r>=l) {
            // tim phan tu giua
            int m = l+(r-l)/2;
            // kiem tra xem phan tu giua co = value khong
            if(arr[m]== value) {
                /* vi mang co the co cac phan tu trung lap nen neu ar[m-1] = value thi ta giam m
                de co duoc index nho nhat thoa man
                 */
                while(m>0 && arr[m-1]== value) {
                    m--;
                }
                return m;
            }
            // truong hop gia tri arr[m] khong = value
            if (value<arr[m]) {
                // tim phan ben phai neu value lon hon arr[m]
                return binarySearch(arr, l, m-1, value);
            } else {
                // tim phan ben trai neu value nho hon arr[m]
                return binarySearch(arr, m+1, r, value);
            }
        }
        // tra ve -1 neu khong tim thay index thoa man
        return -1;
    }

}
