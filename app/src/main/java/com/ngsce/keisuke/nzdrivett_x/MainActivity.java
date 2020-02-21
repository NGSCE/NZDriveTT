package com.ngsce.keisuke.nzdrivett_x;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.util.TypedValue.COMPLEX_UNIT_PX;


public class MainActivity extends AppCompatActivity {


    //文字表示
    float textSizeMultiple = 1;
    float textSize = 1;

    //時刻表読み込み(行番号)
    int LineCount1P = 2;
    int LineCount1S = 2;
    int LineCount2P = 2;
    int LineCount2S = 2;
    int LineCount3P = 2;
    int LineCount3S = 2;

    //時刻表読み込み(便情報<内部処理用･2桁>)
    String TT1Pi = "";
    String TT1Si = "";
    String TT2Pi = "";
    String TT2Si = "";
    String TT3Pi = "";
    String TT3Si = "";

    //情報番号
    String info1 = "";
    String info2 = "";
    String info3 = "";

    //行き先表示
    String desText1 = "";
    String desText2 = "";
    String desText3 = "";
    String des = "";
    String pri = "";
    String sec = "";

    //日付･時刻同期
    String date = "0";
    String dateDis = "";
    String time = "";
    String timeDis = "";
    int mainTime;
    int timeP = 0;
    String todayDiagram = "A";
    String dateTB = "";
    String dateTBa = "";
    String dateTBb = "";

    //初期化値(1で初期化済)
    int Go = 0;

    //情報表示
    int infoLineCount = 2;

    //行き先番号
    String des1N;
    String des2N;
    String des3N;

    //行き先･見出しの読み込み
    int indexi = 3;
    int LangN = 2;

    //内部ストレージの絶対パスの読み出し↓
    File sdcard = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Android6.0以降の場合のみパーミッション取得
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }

        //画面方向固定･画面常時オン･全画面
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        File TS = new File(sdcard,"TT/TS.csv");
        Setting set = new Setting();
        set.Set(TS);
        LangN = set.LangN();
        float Space1 = set.Space1();
        float Space2 = set.Space2();

        //フォントの指定(M+)
        final Typeface M = Typeface.createFromAsset(getAssets(), "font/mplus-2c-regular.ttf");
        final Typeface Mb = Typeface.createFromAsset(getAssets(), "font/mplus-2c-bold.ttf");

        //文字サイズ
        Size m = new Size();
        textSizeMultiple = m.displaySize();
        textSize = 146*textSizeMultiple;



        //時刻1･時刻2の位置調整
        TextView Spacing1 = (TextView) findViewById(R.id.SP1);
        Spacing1.setTypeface(M);
        Spacing1.setTextSize(COMPLEX_UNIT_PX,Space1*textSizeMultiple);
        Spacing1.setText("Spacing time 1");
        TextView Spacing2 = (TextView) findViewById(R.id.SP2);
        Spacing2.setTypeface(M);
        Spacing2.setTextSize(COMPLEX_UNIT_PX,Space2*textSizeMultiple);
        Spacing2.setText("Spacing for the 2nd.");

        //時刻･日付同期開始
        final Handler times = new Handler();
        final Runnable rtimes = new Runnable() {
            @Override
            public void run() {

                Date now = new Date(System.currentTimeMillis());

                TextView timeNow = (TextView) findViewById(R.id.currentTime);
                timeNow.setTypeface(Mb);
                timeNow.setTextSize(COMPLEX_UNIT_PX,textSize);
                int dateLineCount;

                //初期化1(起動時･日付変更時に実行)
                if (timeP == 0) {
                    DateFormat formatDate = new SimpleDateFormat("dd");
                    DateFormat formatDateDis = new SimpleDateFormat("yyyy"+"年"+"MM"+"月"+"dd"+"日");
                    DateFormat formatDateTBa = new SimpleDateFormat("yyyy");
                    DateFormat formatDateTBb = new SimpleDateFormat("MM");
                    dateDis = formatDateDis.format(now);
                    date = formatDate.format(now);
                    dateTBa = formatDateTBa.format(now);
                    dateTBb = formatDateTBb.format(now);
                    dateTB = "TT/" + dateTBa + "/TB" + dateTBb + ".csv";
                    Go = 0;

                    LineCount1P = 2;
                    LineCount1S = 2;
                    LineCount2P = 2;
                    LineCount2S = 2;
                    LineCount3P = 2;
                    LineCount3S = 2;
                    TT1Pi = "";
                    TT1Si = "";
                    TT2Pi = "";
                    TT2Si = "";
                    TT3Pi = "";
                    TT3Si = "";
                }

                //初期化2(ダイヤの読み込み)
                if (Go == 0) {
                    dateLineCount = 2;
                    for (int id = 1; id< 33; id++) {
                        File TB = new File(sdcard,dateTB);
                        Load today = new Load();
                        Resolve resT = new Resolve();
                        String dateDiagram = today.loadTT(TB,dateLineCount);
                        resT.Resolve2(dateDiagram);
                        String readDate = resT.Res1();
                        todayDiagram = resT.Res2();

                        if (readDate.equals(date)){
                            Go = 1;
                        }else{
                            dateLineCount++;
                        }
                    }
                }

                DateFormat formatTime = new SimpleDateFormat("HHmm");
                DateFormat formatTimeDis = new SimpleDateFormat("HH" + ":" + "mm");
                timeDis = formatTimeDis.format(now);
                time = formatTime.format(now);
                timeP = Integer.parseInt(time);
                mainTime = timeP + 1;

                times.postDelayed(this, 10000);
                timeNow.setText(timeDis);
            }
        };
        times.post(rtimes);
        //時刻･日付同期終了

        //行き先番号の読み込み
        File TD = new File(sdcard, "TT/TD.csv");
        Destination init = new Destination();
        init.Destination(TD,2);
        des1N = init.index1();
        des2N = init.index2();
        des3N = init.index3();


        //行き先･見出しの表示開始
        Handler destination = new Handler();
        Runnable rDestination = new Runnable() {
            @Override
            public void run() {

                //アニメーション
                AlphaAnimation desFadeOut = new AlphaAnimation(1, 0);
                desFadeOut.setDuration(500);

                AlphaAnimation desFadeIn = new AlphaAnimation(0, 1);
                desFadeIn.setDuration(500);

                //見出しの読み込み
                File TL = new File(sdcard,"TT/TL.csv");
                Index index = new Index();

                index.index(TL,indexi);
                String LX1 = index.index1();
                String LX2 = index.index2();
                String LX3 = index.index3();
                String LX4 = index.index4();

                //行き先の読み込み
                File TD = new File(sdcard, "TT/TD.csv");
                Destination dest = new Destination();

                dest.Destination(TD,indexi);
                String DX1 = dest.index1();
                String DX2 = dest.index2();
                String DX3 = dest.index3();

                if(LangN + 2> indexi) {
                    indexi++;
                }else{
                    indexi = 3;
                }

                des = LX1;
                pri = LX2;
                sec = LX3;
                desText1 = DX1;
                desText2 = DX2;
                desText3 = DX3;

                //行き先番号が0の場合の処理
                if(des1N.equals("0")){
                    desText1 = "";
                }
                if(des2N.equals("0")){
                    desText2 = "";
                }
                if(des3N.equals("0")){
                    desText3 = "";
                }

                //最終便の処理
                if(TT1Si.equals("00")){
                    TextView last = (TextView) findViewById(R.id.destination1S);
                    last.setTypeface(M);
                    last.setTextSize(COMPLEX_UNIT_PX,textSize);
                    last.startAnimation(desFadeOut);
                    last.setText(LX4);
                    last.startAnimation(desFadeIn);
                }
                if(TT2Si.equals("00")){
                    TextView last = (TextView) findViewById(R.id.destination2S);
                    last.setTypeface(M);
                    last.setTextSize(COMPLEX_UNIT_PX,textSize);
                    last.startAnimation(desFadeOut);
                    last.setText(LX4);
                    last.startAnimation(desFadeIn);
                }
                if(TT3Si.equals("00")){
                    TextView last = (TextView) findViewById(R.id.destination3S);
                    last.setTypeface(M);
                    last.setTextSize(COMPLEX_UNIT_PX,textSize);
                    last.startAnimation(desFadeOut);
                    last.setText(LX4);
                    last.startAnimation(desFadeIn);
                }

                //文字表示
                TextView destination = (TextView) findViewById(R.id.Houmen);
                TextView primary = (TextView) findViewById(R.id.Jihatsu);
                TextView secondary = (TextView) findViewById(R.id.Jijihatsu);
                TextView destination1 = (TextView) findViewById(R.id.Des1);
                TextView destination2 = (TextView) findViewById(R.id.Des2);
                TextView destination3 = (TextView) findViewById(R.id.Des3);

                destination.setTypeface(Mb);
                primary.setTypeface(Mb);
                secondary.setTypeface(Mb);
                destination1.setTypeface(M);
                destination2.setTypeface(M);
                destination3.setTypeface(M);

                destination.setTextSize(COMPLEX_UNIT_PX,textSize/2);
                primary.setTextSize(COMPLEX_UNIT_PX,textSize/2);
                secondary.setTextSize(COMPLEX_UNIT_PX,textSize/2);
                destination1.setTextSize(COMPLEX_UNIT_PX,textSize);
                destination2.setTextSize(COMPLEX_UNIT_PX,textSize);
                destination3.setTextSize(COMPLEX_UNIT_PX,textSize);

                destination.startAnimation(desFadeOut);
                primary.startAnimation(desFadeOut);
                secondary.startAnimation(desFadeOut);
                destination1.startAnimation(desFadeOut);
                destination2.startAnimation(desFadeOut);
                destination3.startAnimation(desFadeOut);

                destination.setText(des);
                primary.setText(pri);
                secondary.setText(sec);
                destination1.setText(desText1);
                destination2.setText(desText2);
                destination3.setText(desText3);

                destination.startAnimation(desFadeIn);
                primary.startAnimation(desFadeIn);
                secondary.startAnimation(desFadeIn);
                destination1.startAnimation(desFadeIn);
                destination2.startAnimation(desFadeIn);
                destination3.startAnimation(desFadeIn);

                destination.postDelayed(this, 10000);
            }
        };
        destination.post(rDestination);
        //行き先の表示終了


        //行き先1時刻表示開始
                final Handler destination1 = new Handler();
                final Runnable rdestination1 = new Runnable() {
                    @Override
                    public void run() {

                        TextView First = (TextView) findViewById(R.id.destination1P);
                        First.setTextSize(COMPLEX_UNIT_PX,textSize);
                        First.setTypeface(Mb);

                        TextView Second = (TextView) findViewById(R.id.destination1S);
                        Second.setTextSize(COMPLEX_UNIT_PX,textSize);
                        Second.setTypeface(M);

                        //便があるか･行き先が有効か･初期化済か確認
                        //行き先1読込
                        if (!todayDiagram.equals("0") && !des1N.equals("0") && Go == 1) {
                            String desTT = "TT/" + todayDiagram + des1N + ".csv";
                            File TT = new File(sdcard,desTT);

                            Load desP = new Load();
                            String LoadP = desP.loadTT(TT,LineCount1P);

                            Resolve resP = new Resolve();
                            resP.Resolve3(LoadP);
                            String DispTTP = resP.DispTT();
                            int TTn = resP.TTn();
                            TT1Pi = resP.Res3();

                            First.setText(DispTTP);
                            First.setTextColor(Color.WHITE);

                            LineCount1S = LineCount1P + 1;

                            Load desS = new Load();
                            String LoadS = desS.loadTT(TT,LineCount1S);
                            Resolve resS = new Resolve();
                            resS.Resolve3(LoadS);
                            String DispTTS = resS.DispTT();
                            TT1Si = resS.Res3();

                            //最終便の確認と時刻同期
                            if (TTn < mainTime && !TT1Si.equals("00")) {
                                LineCount1P++;
                            }
                            if (!TT1Si.equals("00")) {
                                Second.setText(DispTTS);
                                Second.setTextColor(Color.WHITE);
                            }
                        }else{
                            //読み込み条件を満たさない場合
                            First.setText("00:00");
                            Second.setText("");
                            First.setTextColor(Color.BLACK);
                        }

                        info1 = des1N + TT1Pi;
                        destination1.postDelayed(this, 5000);
                        }


                };
                destination1.post(rdestination1);

        //行き先1時刻表示終了


        //行き先2時刻表示開始
        final Handler destination2 = new Handler();
        final Runnable rdestination2 = new Runnable() {
            @Override
            public void run() {
                TextView First = (TextView) findViewById(R.id.destination2P);
                First.setTextSize(COMPLEX_UNIT_PX,textSize);
                First.setTypeface(Mb);

                TextView Second = (TextView) findViewById(R.id.destination2S);
                Second.setTextSize(COMPLEX_UNIT_PX,textSize);
                Second.setTypeface(M);

                //読み込み条件(便があるか･行き先が有効か･初期化済か確認)
                //行き先2読込
                if (!todayDiagram.equals("0") && !des2N.equals("0") && Go == 1) {
                    String desTT = "TT/" + todayDiagram + des2N + ".csv";
                    File TT = new File(sdcard, desTT);

                    Load desP = new Load();
                    String LoadP = desP.loadTT(TT, LineCount2P);

                    Resolve resP = new Resolve();
                    resP.Resolve3(LoadP);
                    String DispTTP = resP.DispTT();
                    int TTn = resP.TTn();
                    TT2Pi = resP.Res3();

                    First.setText(DispTTP);
                    First.setTextColor(Color.WHITE);

                    LineCount2S = LineCount2P + 1;

                    Load desS = new Load();
                    String LoadS = desS.loadTT(TT, LineCount2S);
                    Resolve resS = new Resolve();
                    resS.Resolve3(LoadS);
                    String DispTTS = resS.DispTT();
                    TT2Si = resS.Res3();

                    //最終便の確認と時刻同期
                    if (TTn < mainTime && !TT2Si.equals("00")) {
                        LineCount2P++;
                    }
                    if (!TT2Si.equals("00")) {
                        Second.setText(DispTTS);
                        Second.setTextColor(Color.WHITE);
                    }
                } else {
                    //読み込み条件を満たさない場合
                    First.setText("00:00");
                    Second.setText("");
                    First.setTextColor(Color.BLACK);
                }

                info2 = des2N + TT2Pi;
                destination2.postDelayed(this, 5000);
            }

        };
        destination2.post(rdestination2);
        //行き先2時刻表示終了

        //行き先3時刻表示開始
        final Handler destination3 = new Handler();
        final Runnable rdestination3 = new Runnable() {
            @Override
            public void run() {
                TextView First = (TextView) findViewById(R.id.destination3P);
                First.setTextSize(COMPLEX_UNIT_PX,textSize);
                First.setTypeface(Mb);

                TextView Second = (TextView) findViewById(R.id.destination3S);
                Second.setTextSize(COMPLEX_UNIT_PX,textSize);
                Second.setTypeface(M);

                //便があるか･行き先が有効か･初期化済か確認
                //行き先3読込
                if (!todayDiagram.equals("0") && !des3N.equals("0") && Go == 1) {
                    String desTT = "TT/" + todayDiagram + des3N + ".csv";
                    File TT = new File(sdcard, desTT);

                    Load desP = new Load();
                    String LoadP = desP.loadTT(TT, LineCount3P);

                    Resolve resP = new Resolve();
                    resP.Resolve3(LoadP);
                    String DispTTP = resP.DispTT();
                    int TTn = resP.TTn();
                    TT3Pi = resP.Res3();

                    First.setText(DispTTP);
                    First.setTextColor(Color.WHITE);

                    LineCount3S = LineCount3P + 1;

                    Load desS = new Load();
                    String LoadS = desS.loadTT(TT, LineCount3S);
                    Resolve resS = new Resolve();
                    resS.Resolve3(LoadS);
                    String DispTTS = resS.DispTT();
                    TT3Si = resS.Res3();

                    //最終便の確認と時刻同期
                    if (TTn < mainTime && !TT3Si.equals("00")) {
                        LineCount3P++;
                    }
                    if (!TT3Si.equals("00")) {
                        Second.setText(DispTTS);
                        Second.setTextColor(Color.WHITE);
                    }
                } else {
                    //読み込み条件を満たさない場合
                    First.setText("00:00");
                    Second.setText("");
                    First.setTextColor(Color.BLACK);
                }

                info3 = des3N + TT3Pi;
                destination3.postDelayed(this, 5000);
            }
        };
        destination3.post(rdestination3);
        //行き先3時刻表示終了

        //情報表示開始
        final Handler information = new Handler();
        final Runnable rinformation = new Runnable() {
            @Override
            public void run() {

                TextView info = (TextView) findViewById(R.id.information);
                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 1920*textSizeMultiple, Animation.RELATIVE_TO_SELF, -1.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                translateAnimation.setFillAfter(true);

                int infoGo = 0;
                int infoTime = 0;
                String infoText = "";

                File TT = new File(sdcard, "TT/i.csv");

                while (infoGo == 0){
                    Load desP = new Load();
                    String Loadi = desP.loadTT(TT,infoLineCount);

                    Resolve resi = new Resolve();
                    resi.Resolve2(Loadi);
                    infoText = resi.Res1();
                    String infoN = resi.Res2();

                    if (infoN.equals("000") || infoN.equals(info1) || infoN.equals(info2) || infoN.equals(info3)){
                        infoGo = 1;
                    }
                    if (infoN.equals("999")){
                        infoLineCount = 1;
                        infoGo = 1;
                        String infoTextTemp = dateDis + "　" + todayDiagram + "ダイヤ";
                        if (todayDiagram.equals("0")){
                            infoTextTemp = dateDis + "　" + infoText;
                        }
                        infoText = infoTextTemp;
                    }
                    infoLineCount++;
                }
                int infoLength = infoText.length();
                infoTime = infoLength * 1000;

                info.setTextSize(COMPLEX_UNIT_PX,textSize);
                info.setTypeface(M);
                info.setText(infoText);
                info.startAnimation(translateAnimation);

                translateAnimation.setDuration(infoTime + 7000);
                information.postDelayed(this, infoTime + 2000);
            }
        };
        information.post(rinformation);
        //情報表示終了
    }
}
