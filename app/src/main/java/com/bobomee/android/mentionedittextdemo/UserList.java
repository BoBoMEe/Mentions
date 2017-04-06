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
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class UserList extends AppCompatActivity {

  private RecyclerView recycler;
  private UserList mUserList;

  public static final String RESULT_USER = "RESULT_USER";
  private UserAdapter mUserAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_recycler);
    mUserList = this;
    initView();
  }

  private void initView() {

    List<User> users = provideData();

    recycler = (RecyclerView) findViewById(R.id.recycler);

    recycler.setLayoutManager(new LinearLayoutManager(mUserList));
    mUserAdapter = new UserAdapter(users);
    recycler.setAdapter(mUserAdapter);

    ItemClickSupport.from(recycler)
        .add()
        .addOnItemClickListener(new ItemClick.OnItemClickListener() {
          @Override public void onItemClick(RecyclerView parent, View view, int position, long id) {
            List<User> data = mUserAdapter.getData();
            User user = data.get(position);
            setResult(user);
          }
        });
  }

  private List<User> provideData() {
    List<User> result = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      User user = new User("id" + i, "name" + i);
      user.setUserSex(i % 2 == 0 ? "男" : "女");
      result.add(user);
    }

    return result;
  }

  private class UserAdapter extends CommonRcvAdapter<User> {

    public UserAdapter(@Nullable List<User> data) {
      super(data);
    }

    @NonNull @Override public AdapterItem<User> createItem(int type) {
      return new UserItem();
    }
  }

  private class UserItem implements AdapterItem<User> {
    private TextView userName;
    private TextView userSex;

    @Override public int getLayoutResId() {
      return R.layout.user_item;
    }

    @Override public void bindViews(View root) {
      initView(root);
    }

    @Override public void setViews(User user) {

    }

    @Override public void handleData(User user, int position) {
      userName.setText(user.getUserName());
      userSex.setText(user.getUserSex());
    }

    private void initView(View view) {
      userName = (TextView) view.findViewById(R.id.user_name);
      userSex = (TextView) view.findViewById(R.id.user_sex);
    }
  }

  public static Intent getIntent(Activity activity) {
    return new Intent(activity, UserList.class);
  }

  private void setResult(User user) {
    Intent intent = getIntent();
    intent.putExtra(RESULT_USER, user);
    setResult(RESULT_OK, intent);
    finish();
  }
}
