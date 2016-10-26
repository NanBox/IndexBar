package com.southernbox.indexbar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.southernbox.indexbar.R;
import com.southernbox.indexbar.adapter.MainAdapter;
import com.southernbox.indexbar.entity.Entity;
import com.southernbox.indexbar.widget.IndexBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<Entity> mList = new ArrayList<>();
    private IndexBar mIndexBar;
    private TextView tvToast;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initIndexBar();
        initData();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initIndexBar() {
        mIndexBar = (IndexBar) findViewById(R.id.indexbar);
        tvToast = (TextView) findViewById(R.id.tv_toast);
        mIndexBar.setInitialLetterTextView(tvToast);
        tvToast.setVisibility(View.GONE);
        mIndexBar.setOnLetterChangedListener(new IndexBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String index) {
                for (int i = 0; i < mList.size(); i++) {
                    String firstword = mList.get(i).getFirstword();
                    if (index.equals(firstword)) {
                        // 滚动列表到指定的位置
                        layoutManager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        Map<String, Object> map = convertSortList(getData());
        mList.clear();
        mList.addAll((List<Entity>) map.get("data"));
        Object[] keys = (Object[]) map.get("key");
        String[] letters = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            letters[i] = keys[i].toString();
        }
        mIndexBar.setLetters(letters);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 按首字母将数据排序
     *
     * @param list
     * @return
     */
    public Map<String, Object> convertSortList(List<Entity> list) {
        HashMap<String, List<Entity>> map = new HashMap<>();
        for (Entity item : list) {
            String firstWord;
            if (TextUtils.isEmpty(item.getFirstword())) {
                firstWord = "#";
            } else {
                firstWord = item.getFirstword().toUpperCase();
            }
            if (map.containsKey(firstWord)) {
                map.get(firstWord).add(item);
            } else {
                List<Entity> mlist = new ArrayList<>();
                mlist.add(item);
                map.put(firstWord, mlist);
            }
        }

        Object[] key = map.keySet().toArray();
        Arrays.sort(key);
        //将“#”置于最后
        if (key.length > 0 && "#".equals(key[0])) {
            for (int i = 0; i < key.length - 1; i++) {
                key[i] = key[i + 1];
            }
            key[key.length - 1] = "#";
        }
        List<Entity> sortList = new ArrayList<>();
        for (int i = 0; i < key.length; i++) {
            Entity t = getIndexItem(key[i].toString());
            sortList.add(t);
            sortList.addAll(map.get(key[i]));
        }

        HashMap<String, Object> resultMap = new HashMap();
        resultMap.put("data", sortList);
        resultMap.put("key", key);
        return resultMap;
    }

    private Entity getIndexItem(String firstword) {
        Entity entity = new Entity();
        entity.setFirstword(firstword);
        entity.setIndex(true);
        return entity;
    }

    private List<Entity> getData() {
        List<Entity> list = new ArrayList<>();
        list.add(new Entity("加内特", "J"));
        list.add(new Entity("韦德", "W"));
        list.add(new Entity("詹姆斯", "Z"));
        list.add(new Entity("安东尼", "A"));
        list.add(new Entity("科比", "K"));
        list.add(new Entity("乔丹", "Q"));
        list.add(new Entity("奥尼尔", "A"));
        list.add(new Entity("麦格雷迪", "M"));
        list.add(new Entity("艾弗森", "A"));
        list.add(new Entity("哈达威", "H"));
        list.add(new Entity("纳什", "N"));
        list.add(new Entity("弗朗西斯", "F"));
        list.add(new Entity("姚明", "Y"));
        list.add(new Entity("库里", "K"));
        list.add(new Entity("邓肯", "D"));
        list.add(new Entity("吉诺比利", "J"));
        list.add(new Entity("帕克", "P"));
        list.add(new Entity("杜兰特", "D"));
        list.add(new Entity("韦伯", "W"));
        list.add(new Entity("威斯布鲁克", "W"));
        list.add(new Entity("霍华德", "H"));
        list.add(new Entity("保罗", "B"));
        list.add(new Entity("罗斯", "L"));
        list.add(new Entity("加索尔", "J"));
        list.add(new Entity("欧文", "O"));
        list.add(new Entity("威廉姆斯", "W"));
        list.add(new Entity("隆多", "L"));
        list.add(new Entity("诺维斯基", "N"));
        list.add(new Entity("格里芬", "G"));
        list.add(new Entity("波什", "B"));
        list.add(new Entity("伊戈达拉", "Y"));

        return list;
    }
}
