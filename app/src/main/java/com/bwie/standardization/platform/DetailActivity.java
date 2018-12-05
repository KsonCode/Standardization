package com.bwie.standardization.platform;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.standardization.platform.adapter.MyImageAdapter;
import com.bwie.standardization.platform.common.Constants;
import com.bwie.standardization.platform.entity.KnowledgeEntity;
import com.bwie.standardization.platform.photo.PhotoViewPager;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = DetailActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private List<Integer> Urls;
    private PhotoView mPhotoView;

    private String id,title,desc,type,connotation,extension,url;//单元id
    private TextView mTitleTv;
    private ImageView look;
    private TextView define,defineDesc,connotationTv,connotationDesc,extensionTv,extensionDesc;
    private ScrollView scrollView;
    private List<KnowledgeEntity> knowledgeEntities;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,DefineActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) knowledgeEntities);
                bundle.putString("title",title);
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("tag", "onNewINtent执行了");
        setIntent(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {

        back = findViewById(R.id.back);
        define = findViewById(R.id.define);
        connotationTv = findViewById(R.id.connotation);
        extensionTv = findViewById(R.id.extension);
        defineDesc = findViewById(R.id.defineDesc);
        connotationDesc = findViewById(R.id.connotationDesc);
        extensionDesc = findViewById(R.id.extensionDesc);
        mPhotoView = findViewById(R.id.photoView);
        scrollView = findViewById(R.id.layout);

//        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
//        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
        mTitleTv = findViewById(R.id.toptitle);
        look = findViewById(R.id.look);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });


    }

    private void initData() {


        Intent intent = getIntent();
        if (intent!=null){
            if (intent.getExtras()!=null){
                Bundle bundle = intent.getExtras();
                id = bundle.getString("id", "");
                title = bundle.getString("title","单元流程图");
                System.out.println("titititle:"+title);
                if (!TextUtils.isEmpty(id)) {
                    if (title.equalsIgnoreCase("Java关键字")){
                        url = Constants.IMAGES_PATH+"011.png";
                    }else{
                        url = Constants.IMAGES_PATH + id + ".png";
                    }
                    //Glide图片加载缓存
                    Picasso.with(this).load(url).into(mPhotoView);

                }
                mTitleTv.setText(title);
                desc = bundle.getString("define");
                type = bundle.getString("type");
                if ("0".equals(type)){
                    scrollView.setVisibility(View.GONE);
                }else{
                    scrollView.setVisibility(View.VISIBLE);
                }
                connotation = bundle.getString("connotation");
                extension = bundle.getString("extension");
                System.out.println("define"+desc);
                if (TextUtils.isEmpty(desc)){
                    defineDesc.setVisibility(View.GONE);
                }else{
                    defineDesc.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(connotation)){
                    connotationDesc.setVisibility(View.GONE);
                }else{
                    connotationDesc.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(extension)){
                    extensionDesc.setVisibility(View.GONE);
                }else{
                    extensionDesc.setVisibility(View.VISIBLE);
                }
                connotationTv.setText(connotation);
                extensionTv.setText(extension);
                define.setText(desc);

                knowledgeEntities = (List<KnowledgeEntity>) bundle.getSerializable("list");
                fillData();
            }
        }
//        HomeQuestionListModel.DataBeanX DataBean = ((HomeQuestionListModel.DataBeanX) intent.getSerializableExtra("questionlistdataBean"));
//        Urls = DataBean.getAttach().getImage().getOri();

//        adapter = new MyImageAdapter(Urls, this);
//        mViewPager.setAdapter(adapter);
//        mViewPager.setCurrentItem(currentPosition, false);
//        mTvImageCount.setText(currentPosition+1 + "/" + Urls.size());
//        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                currentPosition = position;
//                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
//            }
//        });

    }

    /**
     * 填充列表
     */
    private void fillData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
