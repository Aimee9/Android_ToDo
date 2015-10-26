package aimee.morgan.com.todolist.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Delete;

import java.util.ArrayList;

import aimee.morgan.com.todolist.R;
import aimee.morgan.com.todolist.models.Category;
import aimee.morgan.com.todolist.models.Task;

public class CategoryActivity extends ListActivity {
    private Category mCategory;
    private ArrayList<String> mTasks;
    private Button mNewTaskButton;
    private EditText mNewTaskText;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String name = getIntent().getStringExtra("categoryName");
        mCategory = Category.find(name);

        mNewTaskButton = (Button)findViewById(R.id.newTaskButton);
        mNewTaskText = (EditText)findViewById(R.id.newTask);


        mTasks = new ArrayList<String>();
        for (Task task : mCategory.tasks() ) {
            mTasks.add(task.getDescription());
        }

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        setListAdapter(mAdapter);

        mNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
                mNewTaskText.getText().clear();
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String description = mAdapter.getItem(position);
        Task newTask = Task.find(description);
        newTask.delete();
        mAdapter.remove(description);
        mAdapter.notifyDataSetChanged();

    }

    private void addTask() {
        String description = mNewTaskText.getText().toString();
        Task newTask = new Task(description, mCategory);
        newTask.save();
        mTasks.add(description);
        mAdapter.notifyDataSetChanged();
    }



}
