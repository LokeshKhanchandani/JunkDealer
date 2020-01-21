package com.example.junkdealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class Junk extends AppCompatActivity {
    private String city;
    private DatabaseReference myRef;
    private ArrayList<Waste> wasteList;
    private ArrayList<String> userIds;
    private LinearLayout not_found_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junk);
        city=getIntent().getExtras().getString("City");
        if(city==null){
            Toast.makeText(Junk.this,"Something went wrong!\nTry again",Toast.LENGTH_SHORT).show();
            finish();
        }
        not_found_layout=findViewById(R.id.not_found);
        wasteList=new ArrayList<>();
        userIds=new ArrayList<>();
        myRef= FirebaseDatabase.getInstance().getReference("garbage").child(city);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Waste waste=data.getValue(Waste.class);
                        wasteList.add(waste);
                        String user=data.getKey();
                        userIds.add(user);
                    }
                    not_found_layout.setVisibility(View.INVISIBLE);
                    setonAdapter(wasteList,userIds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setonAdapter(ArrayList<Waste> wasteList,ArrayList<String> userIds){
        ListView theListView = findViewById(R.id.list);
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, wasteList,city,userIds);
        theListView.setAdapter(adapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
        //        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.contact:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:junkoscrap@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Query from "+city+" Junk Dealer");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
//                Toast.makeText(Junk.this,"Contact us",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
//                Toast.makeText(Junk.this,"About us",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(Junk.this,About.class);
                startActivity(intent1);
                return true;
            case R.id.logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
