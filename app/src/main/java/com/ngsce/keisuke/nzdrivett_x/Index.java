package com.ngsce.keisuke.nzdrivett_x;

import java.io.File;

public class Index {
    //見出しの読み込み

    String LoadLX1 = "";
    String LoadLX2 = "";
    String LoadLX3 = "";
    String LoadLX4 = "";

    //読み込み
    public void index(File TL, int indexi){
        Load Tab = new Load();
        Resolve resL = new Resolve();
        int indexS;
        indexS = indexi - 1;
        String LoadLX = "";

        LoadLX = Tab.loadTT(TL, indexS);
        resL.Resolve4(LoadLX);
        LoadLX1 = resL.Res1();
        LoadLX2 = resL.Res2();
        LoadLX3 = resL.Res3();
        LoadLX4 = resL.Res4();
        return;
    }
    //見出し(方面･次発･次々発･最終)
    public String index1(){
        return LoadLX1;
    }
    public String index2(){
        return LoadLX2;
    }
    public String index3(){
        return LoadLX3;
    }
    public String index4() {return LoadLX4;}
}
