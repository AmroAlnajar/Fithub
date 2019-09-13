package com.example.fithub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
class CustomAdapter implements ListAdapter {
    private ArrayList<WeightEntries> arrayList;
    private Context context;
    private FirebaseUser currentuser;
    private FirebaseAuth mAuth;
    private DatabaseReference chef;


    public CustomAdapter(Context context, ArrayList<WeightEntries> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();



        final WeightEntries weightEntries = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!weightEntries.pic.equals(MyBodyDialog.nopic))
                    {
                        Intent zoomthepic = new Intent(context, ShowZoomedPicture.class);
                        zoomthepic.putExtra("piclink", weightEntries.pic);
                        context.startActivity(zoomthepic);
                    }
                }
            });
            final TextView link = convertView.findViewById(R.id.WeightEntry);
            final TextView tittle = convertView.findViewById(R.id.DateEntry);
            ImageView imag = convertView.findViewById(R.id.ImageEntry);
            tittle.setText(weightEntries.date);
            link.setText(weightEntries.weight);
            Picasso.with(context)
                    .load(weightEntries.pic)
                    .into(imag);


            ImageView deletebutton = convertView.findViewById(R.id.deletebutton);

            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Deleting..");
                    alertDialogBuilder.setMessage("are you sure you want to delete this entry?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            System.out.println("hello?");
                            chef = FirebaseDatabase.getInstance().getReference().child("Weight entries").child(currentuser.getUid()).child(weightEntries.weight);
                            chef.setValue(null);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialogBuilder.create();
                    alertDialogBuilder.show();

                    System.out.println("hello world?" + "");


                }
            });
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}