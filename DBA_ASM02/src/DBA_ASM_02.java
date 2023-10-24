import java.io.File;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import algorithms.Searching;
import algorithms.Sorting;
import file_service.FileService;
import utils.Utils;

public class DBA_ASM_02 {
    // file path: files/INPUT.TXT or INPUT.TXT

    private static final Scanner scanner = new Scanner(System.in);
    // Biến isFresh là để kiểm tra xem người dùng đã nhập 1 array nào chưa
    private static boolean isFresh = true;
    public static int[] CurrentArr;

    public static void main(String[] args) {
        mainProgram();
    }

    // Chương trình chính
    public static void mainProgram() {
        while(true) {
            menuTable();
            // Kiểm tra function người dùng chọn hợp lệ
            int functionChoose = validFunctionInput(8);
            // Nếu ban đầu chưa nhập mảng thì hiển thị thông báo (khong the lua chon cn 3->8)
            if(isFresh&&functionChoose>2) {
                System.out.println("Current array is unavailable. Please enter an array");
                continue;
            } else if(!isFresh) {
                // copy mang nguoi dung da nhap mang
                CurrentArr = Utils.changeStrToIntArr(FileService.readFile(FileService.INPUT_PATH, true), " ");
            }
            // kiem tra chuc nang tuong ung voi input
            switch (functionChoose) {
                case 0:
                    System.out.println("Thanks for using the program!!!");
                    return;
                case 1:
                    manualInput();
                    break;
                case 2:
                    fileInput();
                    break;
                case 3:
                    showCurrentArray();
                    break;
                case 4:
                    bubbleSort();
                    break;
                case 5:
                    selectionSort();
                    break;
                case 6:
                    insertionSort();
                    break;
                case 7:
                    linearSearch();
                    break;
                case 8:
                    binarySearch();
                    break;
            }
        }
    }

    // Menu chức năng
    public static void menuTable() {
        final String ROWLINE = "+--------+-------------+--------+%n";
        int ROWLINELength = ROWLINE.length();
        String placeHolderStr = "| %-"+ (ROWLINELength-6) +"s |%n";
        System.out.format(ROWLINE);
        System.out.format("| %"+ ((ROWLINELength-2)/2) +"s %"+((ROWLINELength-10)/2) +"s |%n", "MENU","");
        System.out.format(ROWLINE);
        System.out.format(placeHolderStr, "1. Manual input");
        System.out.format(placeHolderStr, "2. File input");
        System.out.format(placeHolderStr, "3. Show current array");
        System.out.format(placeHolderStr, "4. Bubble sort");
        System.out.format(placeHolderStr, "5. Selection sort");
        System.out.format(placeHolderStr, "6. Insertion sort");
        System.out.format(placeHolderStr, "7. Search > value");
        System.out.format(placeHolderStr, "8. Search = value");
        System.out.format(placeHolderStr, "0. Exit");
        System.out.format(ROWLINE);
    }

    // kiem tra chuc nang nguoi dung nhap co hop le khong
    public static int validFunctionInput(int funcNumbers) {
        while(true) {
//            nếu chức năng không hợp lệ thì yêu cầu nhập lại
            try {
                System.out.print("Select function: ");
                String input = scanner.nextLine();
                int intInput = Integer.parseInt(input);
                if((intInput>(funcNumbers)) || (intInput<0)) {
//                    nếu không nằm trong phạm vi chức năng thì quăng lỗi
                    throw new Exception();
                }
                return intInput;
            }
            catch(Exception e) {
                System.out.println("Selected function is not valid");
                System.out.println("Please enter again");
            }
        }
    }

    // Chức năng 1: Nhập mảng thủ công
    public static void manualInput() {
        /*Kiểm tra xem:
        nếu nhập lần đầu thì nhập mảng
        nếu không hỏi nguoời dùng có muốn xóa mảng hiện tại để nhập mảng mới không
         */
        if(enterNewArrCheck() || isFresh){
            while(true) {
                try {
                    System.out.print("Enter the array length: ");
                    // kiểm tra dữ liệu người dùng nhập: (độ dài mảng)
                    String intput = scanner.nextLine();
                    int len = Integer.parseInt(intput);
                    if(len<=0 || len>20) {
                        throw new NumberFormatException();
                    }
                    // Kiểm tra dữ liệu người dùng nhập: (giá trị các phần tử của mảng)
                    boolean isValidinput = false; //flag
                    while(!isValidinput) {
                        int[] arr = new int[len];
                        System.out.println("Enter elements (ex: 9 3 5 for an array has length 3):");
                        for(int i =0; i< len;i++) {
                            try{
                                arr[i] = scanner.nextInt();
                                isValidinput = true;
                            } catch (InputMismatchException e) {
                                // bắt lỗi input người dùng và yêu cầu nhập lại
                                System.out.println("Value of elements must be a Integer.");
                                System.out.println("Please enter again.");
                                isValidinput = false;
                                break;
                            }
                        }
                        scanner.nextLine();
                        // Nếu dữ liệu hợp lệ, xóa mảng trước đó, lưu mảng nguời dùng nhập vào file input.txt
                        if(isValidinput){
                            FileService.clearDatas(FileService.INPUT_PATH);
                            FileService.writeFile(FileService.INPUT_PATH, Utils.formatArraySave(arr));
                            System.out.printf(Utils.arrayFormatedString(arr));
                            isFresh = false;
                            return;
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("The array length must be a number between 1 and 20");
                    System.out.println("Please enter again.");
                }
            }
        }

    }
    // Chức năng 2: nhập mảng thông qua đường dẫn
    public static void fileInput() {
        System.out.println("NOTE: The file must be in 'files' folder of the Project.");
        // Ở đây không cần kiểm tra xem đã nhập mảng lần nào chưa vi file input cũng chính là file save mảng gốc
        while(true) {
            System.out.print("Enter file path: ");
            String path = Utils.handleFilePathInput(scanner.nextLine());
            //Kiểm tra xem file có tồn tại hay hợp lệ hay không
            if(FileService.isFileExisted(path)){
                String datas = FileService.readFile(path, true);
                int[] arr = Utils.changeStrToIntArr(datas.trim(), " ");
                if(arr != null) {
                    System.out.printf(Utils.arrayFormatedString(arr));
                    isFresh = false;
                    return;
                } else {
                    // trong trương hợp người dùng lỡ xóa hoặc nội dung dữ liệu không hợp lệ
                    System.out.println("Datas are invalid");
                    System.out.println("Please enter again");
                }
            } else {
                System.out.println("File not found or the path is invalid.");
                System.out.println("please enter again");
            }
        }
    }
    //chuc nang 3: hien thi mang nguoi dung nhap
    public static void showCurrentArray(){
        System.out.printf(Utils.arrayFormatedString(CurrentArr));
    }

    // chuc nang 4: bubble sort
    public static void bubbleSort() {
        // da copy mang trong mainProgram
        String consoleOutput =  algorithms.Sorting.bubleSort(CurrentArr);
        // in ra console
        System.out.printf(consoleOutput);
        // luu ket qua mang da sap xep
        FileService.writeFile(FileService.BUBBLE_SORT_OUTPUT,Utils.formatArraySave(CurrentArr));
    }

    // chuc nang 5: selection sort
    public static void selectionSort(){
        // tuong tu nhu bubbleSort()
        String consoleOutput =  algorithms.Sorting.selectionSort(CurrentArr);
        System.out.printf(consoleOutput);
        FileService.writeFile(FileService.SELECTION_SORT_OUTPUT,Utils.formatArraySave(CurrentArr));
    }

    // chuc nang 6: insertion sort
    public static void insertionSort(){
        // tuong tu nhu bubbleSort()
        String consoleOutput =  algorithms.Sorting.insertSort(CurrentArr);
        System.out.printf(consoleOutput);
        FileService.writeFile(FileService.INSERTION_SORT_OUTPUT,Utils.formatArraySave(CurrentArr));

    }

    // chuc nang 7: tim kiem tuan tu
    public static void linearSearch() {
        while(true) {
            // kiem tra tinh hop le cua input
            System.out.print("Enter searched input value: ");
            String input = scanner.nextLine();
            try{
                int value = Integer.parseInt(input);
                // lay ket qua tim kiem
                int[] result = Searching.linearSearch(CurrentArr, value);
                String saveOutput = "-1";
                if(result!=null) {
                    System.out.print("The Larger position: ");
                    for(int i =0; i< result.length; i++) {
                        System.out.print(result[i]+" ");
                    }
                    System.out.println();
                    saveOutput =Utils.formatArraySave(result);
                } else {
                    System.out.println("The value does not exist!");
                }
                // luu ket qua vao file txt
                FileService.writeFile(FileService.LINEAR_SEARCHING_OUTPUT,saveOutput);
                return;
            } catch (NumberFormatException e) {
                // yeu cau nhap lai neu khong hop le
                System.out.println("The value must be a number");
            }
        }
    }

    // chuc nang 8: tim kiem nhi phan
    public static void binarySearch() {
        while(true) {
            // kiem tra tinh hop le cua input
            System.out.print("Enter searched input value: ");
            String input = scanner.nextLine();
            try{
                int value = Integer.parseInt(input);
//                int[] arr = Arrays.copyOf(CurrentArr, CurrentArr.length);
//                Sorting.quickSort(arr, 0, arr.length-1);
                // Sắp xếp lại mảng trước khi tìm kiếm
                Sorting.quickSort(CurrentArr, 0, CurrentArr.length-1);
                int result = Searching.binarySearch(CurrentArr, 0, CurrentArr.length-1, value);
                // hien thi ket qua ra man hinh
                String consoleOutput = (result!=-1)?"The right position(sorted array): " + result:"The value does not exist!";
                System.out.println(consoleOutput);
                return;
            } catch (NumberFormatException e) {
                System.out.println("The value must be a number");
            }
        }
    }

    public static boolean enterNewArrCheck() {
        if(isFresh == false) {
            System.out.println("New array will override the current array.");
            System.out.println("Are you sure want to enter a new array?(Y/N)");
            while(true) {
                String createNew = scanner.nextLine();
                if(createNew.toLowerCase().equals("y") || createNew.toLowerCase().equals("yes")) {
                    return true;
                } else if (createNew.toLowerCase().equals("n") || createNew.toLowerCase().equals("no")) {
                    break;
                } else {
                    System.out.println("Please enter again.");
                }
            }
        }
        return false;
    }

















    //---------------------------------------------------------------------------------------------------

    //version 2

    /*
    public static void mainProgram() {
        FileService.tryCreateSaveFile(FileService.SAVE_FILE);
        // Khi khởi động chương trình xóa dữ liệu ở file ARRAY_SAVE.TXT
        FileService.clearDatas(FileService.SAVE_FILE);
        while(true) {
            menuTable();
            // Kiểm tra function người dùng chọn hợp lệ
            int functionChoose = validFunctionInput(8);
            // Nếu ban đầu chưa nhập mảng thì hiển thị thông báo
            if(isFresh&&functionChoose>2) {
                System.out.println("Current array is unavailable. Please enter an array");
                continue;
            } else if(!isFresh) {
                CurrentArr =
                        Utils.changeStrToIntArr(
                                FileService.readFile(
                                        FileService.SAVE_FILE, true
                                ), " "
                        );
            }
            switch (functionChoose) {
                case 0:
                    System.out.println("Thanks for using the program!!!");
                    return;
                case 1:
                    manualInput();
                    break;
                case 2:
                    fileInput();
                    break;
                case 3:
                    showCurrentArray();
                    break;
                case 4:
                    bubbleSort();
                    break;
                case 5:
                    selectionSort();
                    break;
                case 6:
                    insertionSort();
                    break;
                case 7:
                    linearSearch();
                    break;
                case 8:
                    binarySearch();
                    break;
            }
        }
    }

    // Chức năng 1: Nhập mảng thủ công
    public static void manualInput() {
        //Kiểm tra xem đã tồn tại mảng trong file save chưa
        //nếu chưa hoặc nhập lần đầu thì nhập mảng
        //nếu không hỏi nguoời dùng có muốn xóa mảng hiện tại để nhập mảng mới không
        if(enterNewArrCheck() || isFresh){
            while(true) {
                try {
                    System.out.print("Enter the array length: ");
                    // kiểm tra dữ liệu người dùng nhập: (độ dài mảng)
                    String intput = scanner.nextLine();
                    int len = Integer.parseInt(intput);
                    if(len<=0 || len>20) {
                        throw new NumberFormatException();
                    }
                    // Kiểm tra dữ liệu người dùng nhập: (giá trị các phần tử của mảng)
                    boolean isValidinput = false; //flag
                    while(!isValidinput) {
                        int[] arr = new int[len];
                        System.out.println("Enter elements (ex: 9 3 5 for an array has length 3):");
                        for(int i =0; i< len;i++) {
                            try{
                                arr[i] = scanner.nextInt();
                                isValidinput = true;
                            } catch (InputMismatchException e) {
                                // bắt lỗi input người dùng và yêu cầu nhập lại
                                System.out.println("Value of elements must be a Integer.");
                                System.out.println("Please enter again.");
                                isValidinput = false;
                                break;
                            }
                        }
                        scanner.nextLine();
                        // Nếu dữ liệu hợp lệ, xóa mảng trước đó, lưu mảng nguời dùng nhập vào file save
                        if(isValidinput){
                            FileService.clearDatas(FileService.SAVE_FILE);
                            FileService.writeFile(FileService.SAVE_FILE, Utils.formatArraySave(arr));
                            System.out.printf(Utils.arrayFormatedString(arr));
                            isFresh = false;
                            return;
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("The array length must be a number between 1 and 20");
                    System.out.println("Please enter again.");
                }
            }
        }
    }
    // Chức năng 2: nhập mảng thông qua đường dẫn
    public static void fileInput() {
        System.out.println("NOTE: The file must be in 'files' folder of the Project.");
        // Kiểm tra tương tự chức năng 1
        if(enterNewArrCheck() || isFresh) {
            while(true) {
                System.out.print("Enter file path: ");
                String path = Utils.handleFilePathInput(scanner.nextLine());
                //Kiểm tra xem file có tồn tại hay không
                if(FileService.isFileExisted(path)){
                    String datas = FileService.readFile(path, true);
                    int[] arr = Utils.changeStrToIntArr(datas.trim(), " ");
                    if(arr != null) {
                        FileService.clearDatas(FileService.SAVE_FILE);
                        FileService.writeFile(FileService.SAVE_FILE, Utils.formatArraySave(arr));
                        System.out.printf(Utils.arrayFormatedString(arr));
                        isFresh = false;
                        return;
                    } else {
                        System.out.println("Datas are invalid");
                        System.out.println("Please enter again");
                    }
                } else {
                    System.out.println("File not found. Please enter again");
                    System.out.println("please enter again");
                }
            }
        }
    }

    public static void showCurrentArray(){
        System.out.printf(Utils.arrayFormatedString(CurrentArr));
    }

    public static void bubbleSort() {
        algorithms.Sorting.bubleSort(CurrentArr);
        System.out.printf(FileService.readFile(FileService.BUBBLE_SORT_OUTPUT, false));
    }

    public static void selectionSort(){
        algorithms.Sorting.selectionSort(CurrentArr);
        System.out.printf(FileService.readFile(FileService.SELECTION_SORT_OUTPUT, false));
    }

    public static void insertionSort(){
        algorithms.Sorting.insertSort(CurrentArr);
        System.out.printf(FileService.readFile(FileService.INSERTION_SORT_OUTPUT, false));
    }

    public static void linearSearch() {
        while(true) {
            System.out.print("Enter searched input value: ");
            String input = scanner.nextLine();
            try{
                int value = Integer.parseInt(input);
                int[] result = Searching.linearSearch(CurrentArr, value);
                if(result!=null) {
                    System.out.print("The Larger position: ");
                    for(int i =0; i< result.length; i++) {
                        System.out.print(result[i]+" ");
                    }
                    System.out.println();
                    int[][] rArr = new int[result.length][2];
                    for(int i =0; i< result.length; i++) {
                        rArr[i][0]= result[i];
                        rArr[i][1]= CurrentArr[result[i]];
                    }
                    System.out.printf(Utils.arrayFormatedString2(rArr));
                    FileService.writeFile(FileService.LINEAR_SEARCHING_OUTPUT, Utils.formatArraySave(result));

                } else {
                    System.out.println("The value does not exist!");
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("The value must be a number");
            }
        }
    }

    public static void binarySearch() {
        while(true) {
            System.out.print("Enter searched input value: ");
            String input = scanner.nextLine();
            try{
                int value = Integer.parseInt(input);
                int[] arr = Arrays.copyOf(CurrentArr, CurrentArr.length);
                Sorting.quickSort(arr, 0, arr.length-1);
                int result = Searching.binarySearch(arr, 0, arr.length-1, value);
                if(result!=-1) {
                    System.out.println("The right position: " + result);
                } else {
                    System.out.println("The value does not exist!");
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("The value must be a number");
            }
        }
    }

    */


}







