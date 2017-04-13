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

/**
 * Created by SouthernBox on 2016/10/25 0025.
 * 主页面
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<Entity> mList = new ArrayList<>();
    private IndexBar mIndexBar;
    private View vFlow;
    private TextView tvFlowIndex;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initIndexBar();
        initData();
        initFlowIndex();
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化快速索引栏
     */
    private void initIndexBar() {
        mIndexBar = (IndexBar) findViewById(R.id.indexbar);
        TextView tvToast = (TextView) findViewById(R.id.tv_toast);
        mIndexBar.setSelectedIndexTextView(tvToast);
        mIndexBar.setOnIndexChangedListener(new IndexBar.OnIndexChangedListener() {
            @Override
            public void onIndexChanged(String index) {
                for (int i = 0; i < mList.size(); i++) {
                    String firstWord = mList.get(i).getFirstWord();
                    if (index.equals(firstWord)) {
                        // 滚动列表到指定的位置
                        layoutManager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    /**
     * 加载数据
     */
    @SuppressWarnings("unchecked")
    private void initData() {
        Map<String, Object> map = convertSortList(getData());
        mList.clear();
        mList.addAll((List<Entity>) map.get("sortList"));
        Object[] keys = (Object[]) map.get("keys");
        String[] letters = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            letters[i] = keys[i].toString();
        }
        mIndexBar.setIndexs(letters);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化顶部悬浮标签
     */
    private void initFlowIndex() {
        vFlow = findViewById(R.id.ll_index);
        tvFlowIndex = (TextView) findViewById(R.id.tv_index);
        mRecyclerView.addOnScrollListener(new mScrollListener());
        //设置首项的索引字母
        if (mList.size() > 0) {
            tvFlowIndex.setText(mList.get(0).getFirstWord());
            vFlow.setVisibility(View.VISIBLE);
        }
    }

    private class mScrollListener extends RecyclerView.OnScrollListener {

        private int mFlowHeight;
        private int mCurrentPosition = -1;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            mFlowHeight = vFlow.getMeasuredHeight();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            View view = layoutManager.findViewByPosition(firstVisibleItemPosition + 1);

            if (view != null) {
                if (view.getTop() <= mFlowHeight && isItem(firstVisibleItemPosition + 1)) {
                    vFlow.setY(view.getTop() - mFlowHeight);
                } else {
                    vFlow.setY(0);
                }
            }

            if (mCurrentPosition != firstVisibleItemPosition) {
                mCurrentPosition = firstVisibleItemPosition;
                tvFlowIndex.setText(mList.get(mCurrentPosition).getFirstWord());
            }
        }

        /**
         * @param position 对应项的下标
         * @return 是否为标签项
         */
        private boolean isItem(int position) {
            return mAdapter.getItemViewType(position) == MainAdapter.VIEW_INDEX;
        }
    }

    /**
     * 按首字母将数据排序
     *
     * @param list 需要排序的数组
     * @return 返回按首字母排序的集合（集合中插入标签项），及所有出现的首字母数组
     */
    public Map<String, Object> convertSortList(List<Entity> list) {
        HashMap<String, List<Entity>> map = new HashMap<>();
        for (Entity item : list) {
            String firstWord;
            if (TextUtils.isEmpty(item.getFirstWord())) {
                firstWord = "#";
            } else {
                firstWord = item.getFirstWord().toUpperCase();
            }
            if (map.containsKey(firstWord)) {
                map.get(firstWord).add(item);
            } else {
                List<Entity> mList = new ArrayList<>();
                mList.add(item);
                map.put(firstWord, mList);
            }
        }

        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        List<Entity> sortList = new ArrayList<>();

        for (Object key : keys) {
            Entity t = getIndexItem(key.toString());
            sortList.add(t);
            sortList.addAll(map.get(key.toString()));
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("sortList", sortList);
        resultMap.put("keys", keys);
        return resultMap;
    }

    private Entity getIndexItem(String firstWord) {
        Entity entity = new Entity();
        entity.setFirstWord(firstWord);
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
        list.add(new Entity("隆多", "L"));
        list.add(new Entity("诺维斯基", "N"));
        list.add(new Entity("格里芬", "G"));
        list.add(new Entity("波什", "B"));
        list.add(new Entity("伊戈达拉", "Y"));

        return list;
    }
}
