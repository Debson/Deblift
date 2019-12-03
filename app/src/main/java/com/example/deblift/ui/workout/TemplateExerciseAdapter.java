package com.example.deblift.ui.workout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deblift.R;

import java.util.ArrayList;

public class TemplateExerciseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<TemplateSetAdapter> templateSetAdapterList = new ArrayList<>();
    private TemplateSetAdapter templateSetAdapter;
    private Button addSetButton;
    private BaseAdapter adapter;

    private RecyclerView setRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<TemplateSetRecycleAdapter> templateSetRecycleAdapterSet = new ArrayList<>();


    private int itemCount = 300;

    public TemplateExerciseAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));

        for(int i = 0; i < itemCount; i++)
            templateSetRecycleAdapterSet.add(new TemplateSetRecycleAdapter());

        adapter = this;
    }


    @Override
    public int getCount() {
        return itemCount;
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
    public View getView(int position, View root, ViewGroup parent) {
        root = inflater.inflate(R.layout.activity_workout_template_exercise_listview, null);

        setRecyclerView = root.findViewById(R.id.template_exercise_set_list);
        setRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        setRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = templateSetRecycleAdapterSet.get(position);

        //setupListView(setRecyclerView);
        setRecyclerView.setAdapter(mAdapter);


        /*int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, setRecyclerView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            //Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
        ViewGroup.LayoutParams params = setRecyclerView.getLayoutParams();
        params.height = totalHeight + (setRecyclerView.getDividerHeight() * (mAdapter.getCount() - 1));
        setRecyclerView.setLayoutParams(params);
        setRecyclerView.requestLayout();*/


        addSetButton = root.findViewById(R.id.add_set_button);
        addSetButton.setTag(position);
        setupAddSetButton(addSetButton);


        return root;
    }

    private void setupListView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Exercise item clicked", Long.toString(position));

            }
        });
    }


    private void setupAddSetButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                templateSetAdapterList.get((Integer)v.getTag()).addItem((Integer)v.getTag());
                adapter.notifyDataSetChanged();

                Log.d("Add set: ", " button pressed " + v.getTag());
            }
        });
    }

    public void addItem() {
        itemCount++;
        templateSetAdapterList.add(new TemplateSetAdapter(context));

        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        itemCount--;

        notifyDataSetChanged();
    }
}
