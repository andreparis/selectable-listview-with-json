package com.example.andre.blue_gears;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andre on 6/2/2016.
 */
public class GetItens extends AsyncTask<Void, Void, ArrayList<ItemListView>> {

    private static String url = "http://bpixel.com.br/teste/itens.json";

    private MainActivity activity;
    private AdapterListView adapter;
    private ProgressDialog pDialog;
    private JSONArray itens = null;
    private Context context;
    // JSON Node names
    static final String TAG_DATA = "data";
    static final String TAG_ITENS = "itens";
    static final String TAG_FOTO = "foto";
    static final String TAG_NOME = "nome";
    static final String TAG_DESCRICAO = "descricao";


    public GetItens(MainActivity a, AdapterListView adapter) {
        this.activity = a;
        this.adapter = adapter;
        this.context = a;
        //this.itensList = new ArrayList<ItemListView>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading list...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected ArrayList<ItemListView> doInBackground(Void... arg0) {
        DatabaseHelper db = new DatabaseHelper(activity);
        ArrayList<ItemListView> itensList = new ArrayList<ItemListView>();
        ItemListView item;
        ServiceHandler sh = new ServiceHandler();
        String jsonStr = sh.makeHttpRequest(url, "GET", null);

        Log.d("Response: ", "> " + jsonStr);
        itensList.addAll(db.getAllItens());
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                itens = jsonObj.getJSONArray(TAG_ITENS);
                for (int i = 0; i < itens.length(); i++) {
                    JSONObject c = itens.getJSONObject(i);
                    item = new ItemListView();
                    item.setImgWeb(c.getString(TAG_FOTO));
                    item.setName(c.getString(TAG_NOME));
                    item.setDescription(c.getString(TAG_DESCRICAO));
                    item.setDatetime(jsonObj.getString(TAG_DATA));
                    if (Utils.nonRepItem(itensList, item))
                        itensList.add(item);
                }
                Utils.sort(itensList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        Log.d("itensList: ", ":"+itensList);
        Log.d("itens: ", ":"+itens);
        return itensList;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemListView> result) {
        super.onPostExecute(result);
        adapter.addItemsList(result);
        adapter.notifyDataSetChanged();
        activity.setList(result);
        if (pDialog.isShowing())
            pDialog.dismiss();

    }

}
