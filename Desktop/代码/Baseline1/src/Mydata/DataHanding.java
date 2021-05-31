package Mydata;

import java.io.*;

/**
 * Mydata
 * wuyue
 * 2021/5/24
 **/
public class DataHanding {
    public static void main(String[] args) throws Exception {
        int i = 0, j = 0,num = 0;

        BufferedReader br = new BufferedReader(new FileReader("new_data.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("test2.txt"));

        String contentLine;
        while ((contentLine = br.readLine()) != null){
            String[] str = contentLine.split(" ");
            i = Integer.valueOf(str[0]);
            num = Integer.valueOf(str[11]);
            for (int k = 0; k < num; k++){
                j = Integer.valueOf(str[12 + k]);
                bw.write(i + " " + j);
                bw.newLine();
            }
        }

        bw.close();
        br.close();
    }
}
