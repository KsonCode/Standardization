package com.bwie.standardization.platform;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.standardization.platform.adapter.CoursesAdapter;
import com.bwie.standardization.platform.api.ApiService;
import com.bwie.standardization.platform.common.Constants;
import com.bwie.standardization.platform.entity.BaseResponseEnitty;
import com.bwie.standardization.platform.entity.KnowledgeEntity;
import com.bwie.standardization.platform.entity.UnitEntity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mSearchEt;
    private ImageView mSearchBtn;
    private TextView mTitleTv;
    private ListView mLv;
    private CoursesAdapter coursesAdapter;
    private List<UnitEntity> unitEntities;
    private boolean isKeywords;
    private ImageView back;

    Handler handler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {

        unitEntities = new ArrayList<>();

        downloadData();


    }

    private String readSaveFile(String path) {
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream(path);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            Log.d("msg", "readSaveFile: \n" + sb.toString());
            inputStream.close();

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 下载json数据
     */
    private void downloadData() {

        File file = new File(Constants.SDCARD_FILE_PATH);
        if (file.exists()){
            fillData(readSaveFile(Constants.SDCARD_FILE_PATH));
            return;
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.downloadFile().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody ResponseBody) throws IOException {
                        if (ResponseBody!=null){
                            fillData(ResponseBody.string());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });
//        String result = getFromAssets("content.json");
//
//        BaseResponseEnitty baseResponseEnitty = new Gson().fromJson(result, BaseResponseEnitty.class);
//        fillData(baseResponseEnitty);

    }

    /**
     * 列表
     */
    private void fillData(String responseBody) {
        try {
            String result = responseBody;

            save(result);
            BaseResponseEnitty baseResponseEnitty = new Gson().fromJson(result,BaseResponseEnitty.class);

            if (baseResponseEnitty!=null){

                unitEntities = baseResponseEnitty.data;
                if (unitEntities != null) {

                    coursesAdapter = new CoursesAdapter(this, unitEntities);
                    mLv.setAdapter(coursesAdapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    private void save(String result) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(Constants.SDCARD_PATH);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(Constants.SDCARD_FILE_PATH);
            System.out.println("filepath:"+file.getAbsolutePath());
            FileOutputStream fileOutputStream = null;
            if (!file.exists()){
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(result.getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {

                }
            }
        }
    }

    private void initView() {
        back = findViewById(R.id.back);
        back.setVisibility(View.GONE);
        mSearchBtn = findViewById(R.id.search_btn);
        mSearchEt = findViewById(R.id.searchEt);
        mSearchBtn.setOnClickListener(this);
        mLv = findViewById(R.id.lv);
        mTitleTv = findViewById(R.id.toptitle);
        mTitleTv.setText("首页");
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (coursesAdapter != null) {

                    UnitEntity unitEntity = (UnitEntity) coursesAdapter.getItem(position);
                    if (unitEntity != null) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", unitEntity.id);
                        bundle.putString("type","0");
                        bundle.putString("title", unitEntity.title);
                        bundle.putSerializable("list", (Serializable) unitEntity.list);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });

    }


    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                if (mSearchEt.getText().toString().length()==0){
                    Toast.makeText(this, "关键词不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                toDetail();
                break;
        }
    }

    /**
     * 跳转到单元流程图详情页
     */
    private void toDetail() {
        isKeywords = false;

        String keywords = mSearchEt.getText().toString();

        for (UnitEntity unitEntity : unitEntities) {

           List<KnowledgeEntity> knowledgeEntities = unitEntity.list;
            for (KnowledgeEntity knowledgeEntity : knowledgeEntities) {
                if (keywords.equalsIgnoreCase(knowledgeEntity.title)){
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this,DetailActivity.class);
                    bundle.putString("id",unitEntity.id);
                    bundle.putString("title",knowledgeEntity.title);
                    bundle.putString("define",knowledgeEntity.definition);
                    bundle.putString("connotation",knowledgeEntity.connotation);
                    bundle.putString("extension",knowledgeEntity.extension);
                    bundle.putString("type","1");//搜索
                    System.out.println("id:"+unitEntity.id);
                    System.out.println("title:"+knowledgeEntity.title);
                    bundle.putSerializable("list", (Serializable) unitEntity.list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    isKeywords = true;

                }
            }

        }
        if (!isKeywords){
            Toast.makeText(this, "请输入正确概念词", Toast.LENGTH_SHORT).show();
        }


    }
}
