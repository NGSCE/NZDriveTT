package com.ngsce.keisuke.nzdrivett_x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Load {
    //CSVファイルの読み込み

    public String loadTT(File TT, int LineCount) {
        String load = "";
        try {

            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(TT), "UTF-8"));

                String str;
                for (int i = 0; i < LineCount; i++) {
                    str = br.readLine();
                    load = str;
                }
            } finally {
                if (TT != null) br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return load;
    }
}
