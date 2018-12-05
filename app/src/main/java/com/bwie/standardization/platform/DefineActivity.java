package com.bwie.standardization.platform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.standardization.platform.adapter.CoursesAdapter;
import com.bwie.standardization.platform.adapter.KownleageAdapter;
import com.bwie.standardization.platform.entity.KnowledgeEntity;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.List;

public class DefineActivity extends AppCompatActivity {
    private ListView lv;
    private List<KnowledgeEntity> knowledgeEntities;
    private KownleageAdapter kownleageAdapter;
    private  String title;
    private TextView titleTv;
    private String uid;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define);
        initView();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            title = bundle.getString("title");
            uid = bundle.getString("id");
            knowledgeEntities = (List<KnowledgeEntity>) bundle.getSerializable("list");
            if (knowledgeEntities != null) {

                kownleageAdapter = new KownleageAdapter(this, knowledgeEntities);
                lv.setAdapter(kownleageAdapter);
            }
            if (!TextUtils.isEmpty(title)){
                titleTv.setText(title);
            }
        }

    }


    private void initView() {
        back = findViewById(R.id.back);
        lv = findViewById(R.id.lv);
        titleTv = findViewById(R.id.toptitle);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KnowledgeEntity knowledgeEntity = (KnowledgeEntity) kownleageAdapter.getItem(position);
                if (knowledgeEntity!=null){

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(DefineActivity.this,DetailActivity.class);
                    bundle.putString("id",uid);
                    bundle.putString("title",knowledgeEntity.title);
                    bundle.putString("define",knowledgeEntity.definition);
                    bundle.putString("connotation",knowledgeEntity.connotation);
                    bundle.putString("extension",knowledgeEntity.extension);
                    bundle.putString("type","1");//搜索
                    bundle.putSerializable("list", (Serializable) knowledgeEntities);
                    System.out.println("ddddd:"+knowledgeEntity.definition);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefineActivity.this.finish();
            }
        });

    }
}
