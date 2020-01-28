package com.example.junkdealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoldingCellListAdapter extends ArrayAdapter<Waste> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private Context mContext;
    private String city;
    private ArrayList<String> userIds;
    private List<Waste> wastes;
    public FoldingCellListAdapter(Context context, List<Waste> objects, String city, ArrayList<String> userIds) {
        super(context, 0, objects);
        mContext=context;
        this.city=city;
        this.userIds=userIds;
        wastes=objects;
    }

    private static class ViewHolder {
        TextView nameT,addT,pickedT;
        ImageView background;
        TextView newspaperB,paperB,tinsB,plasticB,cansB,pickedB;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent){
        final Waste waste=getItem(position);
        FoldingCell cell=(FoldingCell)convertView;
        final ViewHolder viewHolder;
        if(cell==null){
            viewHolder=new ViewHolder();
            LayoutInflater vi=LayoutInflater.from(mContext);
            cell=(FoldingCell)vi.inflate(R.layout.cell,parent,false);

            viewHolder.nameT=cell.findViewById(R.id.nameT);
            viewHolder.addT=cell.findViewById(R.id.addressT);
            viewHolder.pickedT=cell.findViewById(R.id.pickedT);
//            viewHolder.nameB=cell.findViewById(R.id.name);
            viewHolder.newspaperB=cell.findViewById(R.id.newspaper);
            viewHolder.paperB=cell.findViewById(R.id.paper_used);
            viewHolder.tinsB=cell.findViewById(R.id.tins);
            viewHolder.plasticB=cell.findViewById(R.id.plastic);
            viewHolder.cansB=cell.findViewById(R.id.cans);
            viewHolder.pickedB=cell.findViewById(R.id.pick_junk);
            cell.setTag(viewHolder);
        }else{
            if(unfoldedIndexes.contains(position))
                cell.unfold(true);
            else
                cell.fold(true);

            viewHolder=(ViewHolder)cell.getTag();
        }
        if(null==waste)
            return cell;

        //binding
        viewHolder.nameT.setText(waste.userId);
        viewHolder.addT.setText(waste.address);
        if(waste.picked==true)
            viewHolder.pickedT.setText("Picked");
        else
            viewHolder.pickedT.setText("Not Picked");
//        viewHolder.nameB.setText(waste.userId);
        viewHolder.newspaperB.setText("Newspaper: "+waste.newspaper);
        viewHolder.paperB.setText("Paper used: "+waste.paper);
        viewHolder.tinsB.setText("Tins "+waste.tins);
        viewHolder.plasticB.setText("Plastic: "+waste.plastic);
        viewHolder.cansB.setText("Cans: "+waste.cans);
        if(waste.picked==true){
            viewHolder.pickedB.setText("Picked");
        }else {
            viewHolder.pickedB.setText("Pick Junk");
            //a lot work
            viewHolder.pickedB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext,"Clicked at pos"+position,Toast.LENGTH_SHORT).show();
                    if(updatePickStatus(userIds.get(position),wastes.get(position))==true) {
                        viewHolder.pickedB.setText("Picked");
                        viewHolder.pickedT.setText("Picked");
                    }
                    else
                        Toast.makeText(mContext,"Failed to pick junk\nPlease try again later",Toast.LENGTH_SHORT).show();
                }
            });
        }


        return cell;
    }
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    private boolean updatePickStatus(final String userId, final Waste waste1){
        final DatabaseReference wasteRef=FirebaseDatabase.getInstance().getReference("garbage").child(city).child(userId);
        final boolean[] returnValue = {false};
        waste1.picked=true;
        wasteRef.setValue(waste1);
        updateCustomer(waste1.key);
        returnValue[0] =true;
        return true;
//        wasteRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                returnValue[0]=false;
//            }
//        });
//        return returnValue[0];
    }

    private void updateCustomer(String userId){
        final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("customers").child(userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Customer customer = dataSnapshot.getValue(Customer.class);
                    customer.picked=true;
                    myRef.setValue(customer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext,"Coud not update junk status",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
