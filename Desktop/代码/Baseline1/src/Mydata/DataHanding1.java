package Mydata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Mydata
 * wuyue
 * 2021/5/24
 **/
public class DataHanding1 {
    public static void main(String[] args) throws Exception {
        int i = 0, j = 0,num = 0;
        ArrayList arr = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader("v_dui_xiang.txt"));
        BufferedReader br1 = new BufferedReader(new FileReader("e_dx_dx.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("test3.txt"));

        String contentLine;
        while ((contentLine = br.readLine()) != null){
            arr.add(contentLine);
        }

        String contentLine1;
        br1.readLine();
        while ((contentLine1 = br1.readLine()) != null){
            String[] str = contentLine1.split(" ");
            i = arr.indexOf(str[0]) + 1;
            j = arr.indexOf(str[1]) + 1;
            bw.write(i + " " + j);
            bw.newLine();
        }

        bw.close();
        br.close();
    }
}
