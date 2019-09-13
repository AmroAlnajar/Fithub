package com.example.fithub;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyBodyFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseUser currentuser;
    private WeightEntries entries;
    private ArrayList<String> DateEntry = new ArrayList<>();
    private ArrayList<String> WeightEntry = new ArrayList<>();
    private int[] ImageEntry = new int[]{R.drawable.exampleprofile};
    private ArrayList<String> pics = new ArrayList<>();


    ArrayList<WeightEntries> arrayList = new ArrayList<WeightEntries>();
    ListView list;

    public MyBodyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_body, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        list = view.findViewById(R.id.list);

     //   arrayList.add(new WeightEntries("JAVA", "https://www.tutorialspoint.com/java/", "https://www.tutorialspoint.com/java/images/java-mini-logo.jpg"));
     //   arrayList.add(new WeightEntries("Python", "https://www.tutorialspoint.com/python/", "https://www.tutorialspoint.com/python/images/python-mini.jpg"));
     //   arrayList.add(new WeightEntries("Javascript", "https://www.tutorialspoint.com/javascript/", "https://www.tutorialspoint.com/javascript/images/javascript-mini-logo.jpg"));
     //   arrayList.add(new WeightEntries("Cprogramming", "https://www.tutorialspoint.com/cprogramming/", "https://www.tutorialspoint.com/cprogramming/images/c-mini-logo.jpg"));
     //   arrayList.add(new WeightEntries("Cplusplus", "https://www.tutorialspoint.com/cplusplus/", "https://www.tutorialspoint.com/cplusplus/images/cpp-mini-logo.jpg"));




        final List<HashMap<String, String>> MyList = new ArrayList<>();

      /*  String[] from = {"ImageEntry", "DateEntry", "WeightEntry"};
        int[] to = {R.id.ImageEntry, R.id.DateEntry, R.id.WeightEntry};

        final SimpleAdapter Adapter = new SimpleAdapter(getActivity(), MyList, R.layout.list_item, from, to);

        final ListView MyBodyListView = view.findViewById(R.id.list_view); */

        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();

        if(currentuser == null)
        {
            System.out.println("no user is logged in!!!");
        }
        else
        {
            System.out.println("user is logged in!");
        }

        ref = FirebaseDatabase.getInstance().getReference().child("Weight entries").child(currentuser.getUid());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();

                for(DataSnapshot locations : dataSnapshot.getChildren())
                {
                    if(locations!=null)
                    {
                        try {
                            arrayList.add(new WeightEntries(locations.child("date").getValue().toString(),locations.child("weight").getValue().toString(),locations.child("pic").getValue().toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                try {
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), arrayList);
                    list.setAdapter(customAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       /* ref.addChildEventListener(new ChildEventListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                entries = dataSnapshot.getValue(WeightEntries.class);

               if(entries != null)
               {
                   arrayList.add(new WeightEntries(entries.getDate(), entries.getWeight(), entries.getPic()));
               }

                CustomAdapter customAdapter = new CustomAdapter(getActivity(), arrayList);
                list.setAdapter(customAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}}); */

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("you're inside the list now maybe?");
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.floatingaddbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyBodyDialog myBodyDialog = new MyBodyDialog();
                myBodyDialog.show(getActivity().getSupportFragmentManager(),"example myBodyDialog" );
            }
        });

    }

    }