package com.ngsce.keisuke.nzdrivett_x;

import java.io.File;

public class Setting {
    //TS(設定)ファイルの読み込み

    String LoadSX1;
    String LoadSX2;
    String LoadSX3;
    //読み込み
    public void Set(File TS){
        Load Tab = new Load();
        Resolve resS = new Resolve();
        int indexS = 2;
        String LoadSX = Tab.loadTT(TS, indexS);
        resS.Resolve3(LoadSX);
        LoadSX1 = resS.Res1();
        LoadSX2 = resS.Res2();
        LoadSX3 = resS.Res3();
        return;
    }
    //表示言語数
    public int LangN(){
        return Integer.parseInt(LoadSX1);
    }
    //時刻1の手動位置調整用の数値
    public float Space1(){
        if (LoadSX2.equals("0")){
            return 146f;
        }else{
            return Integer.parseInt(LoadSX2);
        }
    }
    //時刻2の手動位置調整用の数値
    public float Space2(){
        if (LoadSX3.equals("0")){
            return 146f;
        }else{
            return Integer.parseInt(LoadSX3);
        }
    }

}
