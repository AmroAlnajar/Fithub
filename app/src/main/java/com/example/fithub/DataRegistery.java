package com.example.fithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataRegistery extends AppCompatActivity {

    DatabaseReference databaseinfo;
    FirebaseAuth mauth;
    ToggleButton malebutton;
    ToggleButton femalebutton;

    ToggleButton halfbutton;
    ToggleButton onebutton;
    ToggleButton twobutton;
    ToggleButton threebutton;
    ToggleButton fourbutton;

    EditText NameField;
    EditText MonthField;
    EditText DayField;
    EditText YearField;
    EditText WeightField;
    EditText HeightField;
    EditText ProteinsField;
    EditText CarbsField;
    EditText FatField;

    public static String Id;
    public static String Weight;
    public static String Height;
    public static String Gender;
    public static String Name;
    public static String Birthday;
    public static String Protein;
    public static String Carbs;
    public static String Fat;
    public static String Weightloss;
    public static String activitylevel;

    Spinner mySpinner;

    String profilepicurl = "hello";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataregistery);

        malebutton = (ToggleButton) findViewById(R.id.MaleToggle);
        femalebutton = (ToggleButton) findViewById(R.id.FemaleToggle);
        halfbutton = (ToggleButton) findViewById(R.id.halfbutton);
        onebutton = (ToggleButton) findViewById(R.id.onebutton);
        twobutton = (ToggleButton) findViewById(R.id.twobutton);
        threebutton = (ToggleButton) findViewById(R.id.threebutton);
        fourbutton = (ToggleButton) findViewById(R.id.fourbutton);
        mySpinner = findViewById(R.id.activitychooser);

        NameField = (EditText) findViewById(R.id.FullName);
        WeightField = (EditText) findViewById(R.id.Weight);
        HeightField = (EditText) findViewById(R.id.Height);
        ProteinsField = (EditText) findViewById(R.id.Protein);
        CarbsField = (EditText) findViewById(R.id.Carbs);
        FatField = (EditText) findViewById(R.id.Fat);
        MonthField = (EditText) findViewById(R.id.Month);
        DayField = (EditText) findViewById(R.id.Day);
        YearField = (EditText) findViewById(R.id.Year);

        final ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(DataRegistery.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.activitychooser));

        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinneradapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                System.out.println(mySpinner.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        mauth = FirebaseAuth.getInstance();

        FirebaseUser currentuser = mauth.getCurrentUser();


        if(currentuser != null)
        {
            Id = currentuser.getUid();

            System.out.println(Id);
        }
        else
        {
            Intent newintent = new Intent(this, MainActivity.class);
            startActivity(newintent);
        }


        databaseinfo = FirebaseDatabase.getInstance().getReference("Personal Info").child(currentuser.getUid());

        databaseinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PersonalInfo post = dataSnapshot.getValue(PersonalInfo.class);

                if(post != null)
                {
                    String[] bdaysplitted;

                    String birthday = post.getBirthday();
                    bdaysplitted = birthday.split("/", 3);

                    NameField.setText(post.getName());
                    WeightField.setText(post.getWeight());
                    HeightField.setText(post.getHeight());
                    ProteinsField.setText(post.getProtein());
                    CarbsField.setText(post.getCarbs());
                    FatField.setText(post.getFat());

                    MonthField.setText(bdaysplitted[0]);
                    DayField.setText(bdaysplitted[1]);
                    YearField.setText(bdaysplitted[2]);

                   if(post.getGender().equals("Male"))
                   {
                       malebutton.setChecked(true);
                       malebutton.isChecked();
                       femalebutton.setChecked(false);
                       Gender = post.getGender();
                   }
                   else if(post.getGender().equals("Female"))
                   {
                       femalebutton.setChecked(true);
                       femalebutton.isChecked();
                       malebutton.setChecked(false);
                       Gender = post.getGender();
                   }



                   if(post.getWeightloss().equals("130"))
                   {
                       halfbutton.setChecked(true);
                       onebutton.setChecked(false);
                       twobutton.setChecked(false);
                       threebutton.setChecked(false);
                       fourbutton.setChecked(false);
                       Weightloss = "130";

                   } else if (post.getWeightloss().equals("260"))
                   {
                       halfbutton.setChecked(false);
                       onebutton.setChecked(true);
                       twobutton.setChecked(false);
                       threebutton.setChecked(false);
                       fourbutton.setChecked(false);
                       Weightloss = "260";


                   } else if(post.getWeightloss().equals("513"))
                   {
                       halfbutton.setChecked(false);
                       onebutton.setChecked(false);
                       twobutton.setChecked(true);
                       threebutton.setChecked(false);
                       fourbutton.setChecked(false);
                       Weightloss = "513";


                   } else if(post.getWeightloss().equals("770"))
                   {
                       halfbutton.setChecked(false);
                       onebutton.setChecked(false);
                       twobutton.setChecked(false);
                       threebutton.setChecked(true);
                       fourbutton.setChecked(false);
                       Weightloss = "770";


                   } else if(post.getWeightloss().equals("1026"))
                   {
                       halfbutton.setChecked(false);
                       onebutton.setChecked(false);
                       twobutton.setChecked(false);
                       threebutton.setChecked(false);
                       fourbutton.setChecked(true);
                       Weightloss = "1026";


                   }


                   if(post.getActivitylevel().equals("1.3"))
                   {
                       mySpinner.setSelection(1);

                   } else if(post.getActivitylevel().equals("1.4"))
                   {
                       mySpinner.setSelection(2);

                   } else if(post.getActivitylevel().equals("1.5"))
                   {
                       mySpinner.setSelection(3);

                   }else if(post.getActivitylevel().equals("1.6"))
                   {
                       mySpinner.setSelection(4);

                   }



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void sendinfo(View view)
    {

        if(!malebutton.isChecked() && !femalebutton.isChecked())
        {
            Toast.makeText(DataRegistery.this, "please choose your gender", Toast.LENGTH_LONG).show();
            return;
        }

        if(!halfbutton.isChecked() && !onebutton.isChecked() && !twobutton.isChecked() && !threebutton.isChecked() && !fourbutton.isChecked())
        {
            Toast.makeText(DataRegistery.this, "please choose your weight loss goal", Toast.LENGTH_LONG).show();
            return;

        }

        if( mySpinner.getSelectedItem().toString().trim().equals("No exercise"))
        {
            activitylevel="1.3";

        } else if( mySpinner.getSelectedItem().toString().trim().equals("Light exercise"))
        {
            activitylevel = "1.4";

        } else if( mySpinner.getSelectedItem().toString().trim().equals("Heavy exercise"))
        {
            activitylevel = "1.5";

        } else if( mySpinner.getSelectedItem().toString().trim().equals("physical work, medium exercise"))
        {
            activitylevel = "1.6";

        } else if( mySpinner.getSelectedItem().toString().trim().equals("Choose activity level"))
        {
            Toast.makeText(DataRegistery.this, "choose your activity level", Toast.LENGTH_LONG).show();
            return;
        }



        databaseinfo = FirebaseDatabase.getInstance().getReference("Personal Info");







       // Id =  //currentuser; //databaseinfo.push().getKey(); //
        Weight = WeightField.getText().toString().trim();
        Height = HeightField.getText().toString().trim();
        Name = NameField.getText().toString().trim();
        Protein = ProteinsField.getText().toString().trim();
        Fat = FatField.getText().toString().trim();
        Carbs = CarbsField.getText().toString().trim();
        Birthday = DayField.getText().toString().trim() + "/" + MonthField.getText().toString().trim() + "/" + YearField.getText().toString().trim();



        if(TextUtils.isEmpty(Name))
        {
            NameField.setError("Can't Be Empty");
            return;
        } else if(TextUtils.isEmpty(Birthday))
        {
            DayField.setError("Can't Be Empty");
            MonthField.setError("Can't Be Empty");
            YearField.setError("Can't Be Empty");
            return;
        }else if(TextUtils.isEmpty(Weight))
        {
            WeightField.setError("Can't Be Empty");
            return;
        }else if(TextUtils.isEmpty(Height))
        {
            HeightField.setError("Can't Be Empty");
            return;
        }


        int prt = Integer.parseInt(Protein);
        int crb = Integer.parseInt(Carbs);
        int ft = Integer.parseInt(Fat);

        if(prt + crb + ft != 100)
        {
            Toast.makeText(DataRegistery.this, "Proteins, Carbs and Fat should equal to 100%", Toast.LENGTH_LONG).show();
            return;
        }


        PersonalInfo info = new PersonalInfo(Id, Name, Birthday, Weight, Height, Gender, Protein, Carbs, Fat, Weightloss, activitylevel, profilepicurl);

        databaseinfo.child(Id).setValue(info);
        Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();

        Intent nextpage = new Intent(this, DataRegisteryContinued.class);
        startActivity(nextpage);

    }


    public void malelogic(View view)
    {

        if(malebutton.isChecked())
        {
            Gender = "Male";
            femalebutton.setChecked(false);
        }

        System.out.println(Gender);

    }

    public void femalelogic(View view)
    {

        if(femalebutton.isChecked())
        {
            Gender = "Female";
            malebutton.setChecked(false);

        }

        System.out.println(Gender);


    }

    public void halfbuttonchecked(View view)
    {
        if(halfbutton.isChecked())
        {
            Weightloss = "130";
            onebutton.setChecked(false);
            twobutton.setChecked(false);
            threebutton.setChecked(false);
            fourbutton.setChecked(false);

        }

        }

        public void onebuttonchecked(View view)
        {
            if(onebutton.isChecked())
            {
                Weightloss = "260";
                halfbutton.setChecked(false);
                twobutton.setChecked(false);
                threebutton.setChecked(false);
                fourbutton.setChecked(false);
            }
        }

        public void twobuttonchecked(View view)
        {
            if(twobutton.isChecked())
            {
                Weightloss = "513";
                halfbutton.setChecked(false);
                onebutton.setChecked(false);
                threebutton.setChecked(false);
                fourbutton.setChecked(false);
            }
        }

        public void threebuttonchecked(View view)
        {
            if(threebutton.isChecked())
            {
                Weightloss = "770";
                halfbutton.setChecked(false);
                onebutton.setChecked(false);
                twobutton.setChecked(false);
                fourbutton.setChecked(false);
            }
        }

        public void fourbuttonchecked(View view)
        {
            if(fourbutton.isChecked())
            {
                Weightloss = "1026";
                halfbutton.setChecked(false);
                onebutton.setChecked(false);
                twobutton.setChecked(false);
                threebutton.setChecked(false);

            }
        }



    }

