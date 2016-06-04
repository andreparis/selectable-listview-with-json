package com.example.andre.blue_gears;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private AdapterListView adapter;
    private DatabaseHelper db;
    private ArrayList<ItemListView> itens;
    private ArrayList<ItemListView> itemsToDownlaod;
    private boolean downlaoded = false;
    private ArrayList<Integer> positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(MainActivity.this);
        list=(ListView)findViewById(R.id.list);
        itens = new ArrayList<ItemListView>();
        itemsToDownlaod = new ArrayList<ItemListView>();
        positions = new ArrayList<Integer>();
        adapter = new AdapterListView(this, R.layout.item_list, itens);
        list.setLongClickable(true);
        list.setAdapter(adapter);
        new GetItens(this, adapter).execute();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = list.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                if (checked)
                    positions.add(position);
                adapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.download:
                        downlaoded = true;
                        SparseBooleanArray selected = adapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                ItemListView selecteditem = adapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                itemsToDownlaod.add(selecteditem);
                                //adapter.remove(selecteditem);
                            }
                        }
                        if (itemsToDownlaod.size() > 0)
                            db.addItens(itemsToDownlaod);
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                adapter.removeSelection(downlaoded, positions);
                itemsToDownlaod = new ArrayList<ItemListView>();
                positions = new ArrayList<Integer>();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }

    public void setList(ArrayList<ItemListView> list) {
        this.itens = list;
    }
}
