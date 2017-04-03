package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.bobomee.android.common.adapter.CommonRcvAdapter;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.recyclerviewhelper.selectclick.click.ItemClick;
import com.bobomee.android.recyclerviewhelper.selectclick.click.ItemClickSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class TagList extends AppCompatActivity {

  private RecyclerView recycler;
  private TagList mTagList;

  public static final String RESULT_TAG = "RESULT_TAG";
  private TagAdapter mTagAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_recycler);
    mTagList = this;
    initView();
  }

  private void initView() {

    List<Tag> tags = provideData();

    recycler = (RecyclerView) findViewById(R.id.recycler);
    recycler.setLayoutManager(new LinearLayoutManager(mTagList));
    mTagAdapter = new TagAdapter(tags);
    recycler.setAdapter(mTagAdapter);

    ItemClickSupport.from(recycler)
        .add()
        .addOnItemClickListener(new ItemClick.OnItemClickListener() {
          @Override public void onItemClick(RecyclerView parent, View view, int position, long id) {
            List<Tag> data = mTagAdapter.getData();
            Tag tag = data.get(position);
            setResult(tag);
          }
        });
  }

  private List<Tag> provideData() {
    List<Tag> result = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      Tag tag = new Tag("tag --> " + i, "tagid-->" + i);
      tag.setTagUrl("http://www.baidu.com/");
      result.add(tag);
    }
    return result;
  }

  private class TagAdapter extends CommonRcvAdapter<Tag> {

    public TagAdapter(@Nullable List<Tag> data) {
      super(data);
    }

    @NonNull @Override public AdapterItem<Tag> createItem(int type) {
      return new TagItem();
    }
  }

  private class TagItem implements AdapterItem<Tag> {
    private TextView tagName;
    private TextView atgUrl;

    @Override public int getLayoutResId() {
      return R.layout.tag_item;
    }

    @Override public void bindViews(View root) {
      initView(root);
    }

    @Override public void setViews(Tag tag) {

    }

    @Override public void handleData(Tag tag, int position) {
      tagName.setText(tag.getTagLable());
      atgUrl.setText(tag.getTagUrl());
    }

    private void initView(View view) {
      tagName = (TextView) view.findViewById(R.id.tag_name);
      atgUrl = (TextView) view.findViewById(R.id.atg_url);
    }
  }

  public static Intent getIntent(Activity activity) {
    return new Intent(activity, TagList.class);
  }

  private void setResult(Tag tag) {
    Intent intent = getIntent();
    intent.putExtra(RESULT_TAG, tag);
    setResult(RESULT_OK, intent);
    finish();
  }
}


