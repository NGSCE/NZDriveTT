package com.ngsce.keisuke.nzdrivett_x;

import java.util.regex.Pattern;

public class Resolve {
    //CSVファイルの解釈

    String Str0;
    String Str1;
    String Str2;
    String Str3;
    //列数4のCSV
    public void Resolve4(String Str){
        String[] Strx = Str.split(Pattern.quote(","), -1);
        Str0 = Strx[0];
        Str1 = Strx[1];
        Str2 = Strx[2];
        Str3 = Strx[3];
        return;
    }
    //列数3のCSV
    public void Resolve3(String Str){
        String[] Strx = Str.split(Pattern.quote(","), -1);
        Str0 = Strx[0];
        Str1 = Strx[1];
        Str2 = Strx[2];
        return;
    }
    //列数2のCSV
    public void Resolve2(String Str){
        String[] Strx = Str.split(Pattern.quote(","), -1);
        Str0 = Strx[0];
        Str1 = Strx[1];
        return;
    }

    //固有数値
    public String DispTT() {
        return Str0 + ":" + Str1;
    }
    public int TTn() {
        return Integer.parseInt(Str0 + Str1);
    }

    //共通数値
    public String Res1(){
        return Str0;
    }
    public String Res2(){
        return Str1;
    }
    public String Res3(){
        return Str2;
    }
    public String Res4() {return Str3;}
}
