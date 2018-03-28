package com.spartans.rentec;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Postsearch_Activity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postsearch_);
      listView=(ListView) findViewById(R.id.postsearchview);
        ArrayList<String> postarray=new ArrayList<>();
        postarray.addAll(Arrays.asList(getResources().getStringArray(R.array.array_post)));

        adapter=new ArrayAdapter<String>(Postsearch_Activity.this,android.R.layout.simple_list_item_1,postarray);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_second, menu);
        MenuItem item =menu.findItem(R.id.test_search_check2);
        item.expandActionView();
        final SearchView searchView=(SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> postarray= new ArrayList<>();
                postarray.addAll(Arrays.asList(getResources().getStringArray(R.array.array_post)));
                adapter= new ArrayAdapter<String>(Postsearch_Activity.this,android.R.layout.simple_list_item_1,postarray);
                listView.setAdapter(adapter);
                adapter.getFilter().filter(query, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (count==0){
                            Toast.makeText(getApplicationContext(),"Your search is invalid. Please Search Again.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                ArrayList<String> postarray= new ArrayList<>();
                postarray.addAll(Arrays.asList(getResources().getStringArray(R.array.array_post)));
                adapter= new ArrayAdapter<String>(Postsearch_Activity.this,android.R.layout.simple_list_item_1,postarray);
                listView.setAdapter(adapter);
                adapter.getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (count==0){
                            Toast.makeText(getApplicationContext(),"Your search is invalid. Please Search Again.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Userinfo.searchid=null;
                finish();
                startActivity(new Intent(getApplicationContext(),WallActivity.class));
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String district=listView.getItemAtPosition(position).toString();
//                Intent intent = new Intent(storesearchActivity.this, ChooseVegActivity.class);
//                intent.putExtra("Vege", vegetable);
//                startActivity(intent);
                Userinfo.searchid=district;
                finish();
                startActivity(new Intent(Postsearch_Activity.this,WallActivity.class));



            }



        });



        return true;
    }




    @Override
    public void onBackPressed() {
        Userinfo.searchid=null;
        finish();
        startActivity(new Intent(getApplicationContext(),WallActivity.class));
    }
}
