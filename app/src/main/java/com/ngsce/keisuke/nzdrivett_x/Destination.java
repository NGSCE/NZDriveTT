package com.ngsce.keisuke.nzdrivett_x;

import java.io.File;

public class Destination {
    //行き先の読み込み

    String LoadDX1;
    String LoadDX2;
    String LoadDX3;

    //読み込み
    public void Destination(File TD, int indexi){
        Load Tab = new Load();
        Resolve resL = new Resolve();
        int indexS;

        indexS = indexi;
        String LoadDX = "";
        LoadDX = Tab.loadTT(TD,indexS);
        resL.Resolve3(LoadDX);
        LoadDX1 = resL.Res1();
        LoadDX2 = resL.Res2();
        LoadDX3 = resL.Res3();
        return;
    }

    //方面1～3の行き先･行き先番号
    public String index1(){
        return LoadDX1;
    }
    public String index2(){
        return LoadDX2;
    }
    public String index3(){
        return LoadDX3;
    }
}
