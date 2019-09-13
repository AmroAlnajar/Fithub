package com.example.fithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<String> listdates = new ArrayList<>();
    public static ArrayList<Number> weightsinarraylist = new ArrayList<>();
    public static Number[] weights = new Number[weightsinarraylist.size()];
    WeightEntries entries;
    DatabaseReference chef;
    private TextView UserName;
    private TextView UserEmail;
    private PersonalInfo post;
    private FirebaseUser currentuser;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private ImageView userimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            Fragment newFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, newFragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        mAuth = FirebaseAuth.getInstance();

        currentuser = mAuth.getCurrentUser();
        if (currentuser == null) {
            System.out.println("no user is logged in!!!");
        } else {
            System.out.println("user is logged in!");
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);

        userimage = findViewById(R.id.imageView);


        ref = FirebaseDatabase.getInstance().getReference().child("Personal Info").child(currentuser.getUid());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue(PersonalInfo.class);

                if (post != null) {
                    UserName = findViewById(R.id.UserFame);
                    UserEmail = findViewById(R.id.UserEmail);

                    UserName.setText(post.getName());
                    UserEmail.setText(currentuser.getEmail());

                    try {
                        Picasso.with(getApplicationContext())
                                .load(post.getProfilepicurl())
                                .into(userimage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //userimage


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.out.println(mAuth.getCurrentUser().getUid());
            mAuth.signOut();
            mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    System.out.println("something happened");
                }
            });
            Intent Mainintent = new Intent(this, MainActivity.class);
            startActivity(Mainintent);
            finish();

            if (mAuth.getCurrentUser() != null) {
                System.out.println("if there is a user signed in, this will display");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();

        } else if (id == R.id.nav_mybody) {
            fragment = new MyBodyFragment();

            //this will handle My Body page
        } else if (id == R.id.nav_progress) {
            fragment = new Progressfragment();
            //this will handle MyProgress page

        } else if (id == R.id.nav_Target) {
            fragment = new Targetfragment();
            //This will handle Target page
        } else if (id == R.id.nav_Steps) {
            fragment = new MySteps();
            //This will handle MySteps page

        } else if (id == R.id.settings) {
            Intent intent = new Intent(this, DataRegistery.class);
            startActivity(intent);

        } else if (id == R.id.nav_AboutUs) {
            fragment = new AboutUs();
        } else if (id == R.id.userseettings) {
            Intent userseetings = new Intent(this, UserSettings.class);
            startActivity(userseetings);
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        chef = FirebaseDatabase.getInstance().getReference().child("Weight entries").child(currentuser.getUid());

        chef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                listdates.clear();
                weightsinarraylist.clear();


                // if(entries != null) {

                for (DataSnapshot locations : dataSnapshot.getChildren()) {
                    // String location = locations.getValue(WeightEntries.class).toString();
                    //  entries = dataSnapshot.getValue(WeightEntries.class);

                    if (locations != null) {


                        try {
                            System.out.println(locations.child("date").getValue().toString());
                            System.out.println(locations.child("weight").getValue());

                            listdates.add(locations.child("date").getValue().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            weightsinarraylist.add(NumberFormat.getInstance().parse(locations.child("weight").getValue().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }


                }

                weights = weightsinarraylist.toArray(weights);



                System.out.println("------------------------------------------------");
                System.out.println(listdates.size() + "" + weightsinarraylist.size());
                System.out.println("------------------------------------------------");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
