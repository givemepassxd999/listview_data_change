package com.example.givemepass.listviewdatachangedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private ListView mListView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        initData();
        initView();
        execDataChange();
    }

    private void initData(){
        MyData.clear();
        for(int i = 0; i < 100; i++){
            MyData.addItem("a" + i);
        }
    }

    private void initView(){
        mListView = (ListView) findViewById(R.id.list_view);
        adapter = new MyAdapter(MyData.getData());
        mListView.setAdapter(adapter);
    }

    private void execDataChange(){
        ExecutorService thread1 = Executors.newSingleThreadExecutor();
        thread1.submit(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100000; i++){
                    MyData.addItem("b" + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setData(MyData.getData());
                            adapter.notifyDataSetChanged();
                            mListView.setSelection(MyData.getData().size() - 1);
                        }
                    });
                }
            }
        });
        ExecutorService thread2 = Executors.newSingleThreadExecutor();
        thread2.submit(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100000; i++){
                    MyData.addItem("c" + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setData(MyData.getData());
                            adapter.notifyDataSetChanged();
                            mListView.setSelection(MyData.getData().size() - 1);
                        }
                    });
                }
            }
        });
    }

    public class MyAdapter extends BaseAdapter {
        private List<String> list;
        public MyAdapter(List<String> data) {
            list = data;
        }
        public void setData(List<String> data){
            list = data;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder holder;
            if(view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.view_item, parent, false);
                holder = new Holder();
                holder.textView = (TextView) view.findViewById(R.id.item_text);
                view.setTag(holder);
            } else{
                holder = (Holder) view.getTag();
            }
            holder.textView.setText(list.get(position));
            return view;
        }
        class Holder{
            TextView textView;
        }
    }
}
