package file_service;

import java.io.*;

public class FileService {

    /*
    // using for version 2
    public static final String COMMA_DELIMITER =",";
    public static final String SAVE_FILE = "files/ARRAY_SAVE.TXT";
     */

    public static final String INPUT_PATH = "files/INPUT.TXT";
    public static final String BUBBLE_SORT_OUTPUT ="files/OUTPUT1.TXT";
    public static final String INSERTION_SORT_OUTPUT ="files/OUTPUT2.TXT";
    public static final String SELECTION_SORT_OUTPUT ="files/OUTPUT3.TXT";
    public static final String LINEAR_SEARCHING_OUTPUT ="files/OUTPUT4.TXT";


    // doc du lieu tu file
    public static String readFile(String fileName, boolean isOneLine) {
        // bien isOneLine kiem tra doc 1 dong hay doc het
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // bien luu du lieu
            StringBuilder str = new StringBuilder();
            String line;
            int lineCount =0;
            while((line = reader.readLine())!=null){
                str.append(line);
                lineCount++;
                if (isOneLine){
                    break;
                }
                str.append("%n");
            }
            //Neu khong co du lieu thong bao cho nguoi dung
            if(lineCount==0) {
                System.out.println("The file is empty");
                throw new IOException();
            }
            return str.toString();
        } catch (IOException e) {
            System.out.println("Error when reading file or file does not exist");
        } catch (NumberFormatException e) {
            System.out.println("Datas are invalid!!!");
        }
        // Neu loi tra ve null
        return null;
    }

    // write du lieu vao file
    public static void writeFile(String filenPath, String content) {
        tryCreateSaveFile(filenPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filenPath))) {
            // trong chuoi ta su dung "%n" de xuong dong nen tach dong tai "%n"
            String[] lines = content.split("%n");
            for(String line: lines){
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // xoa du lieu file -  su dung de ghi de mang moi doi voi mang cu
    public static void clearDatas(String fileName) {
        tryCreateSaveFile(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // tao file neu file khong ton tai (truong hop nguoi dung lo xoa cac output.txt hoac
    // khi su dung chuc nang 1, tao file de luu mang)
    public static void tryCreateSaveFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // kiem tra xem file co ton tai khong (trong truong hop nguoi dung nhap sai duong dan)
    public static boolean isFileExisted(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static boolean isFileEmpty(String fileName) {
        if(isFileExisted(fileName)){
            try(BufferedReader buffer = new BufferedReader(new FileReader(fileName))){
                if(buffer.readLine() == null) {
                    System.out.println("The file is empty");
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
