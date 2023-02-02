package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Adapter.ImageAdapter;
import com.example.finalyearporjecttoletapp.Model.LocationModel;
import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostHome extends AppCompatActivity {


    SupportMapFragment smf;
    FusedLocationProviderClient client;
    GoogleMap mMap;

    double locate;
    double  logn;

    RecyclerView recyclerView;
    ArrayList<Uri> urilist = new ArrayList<>();
    ImageAdapter imageAdapter;
    private static final int Read_Permission = 101;



    String selectedDivision, selectedDistrict, selectedArea;
    Spinner divisionSpinner,DistrictSpinner,areaSpinner;
    ArrayAdapter<CharSequence> divisionAdapter,districtAdapter,areaAdapters;


    TextInputLayout textInputLayout,bedroomtextInputLayout,bathroomtextInputLayout,kitchenroomtextInputLayout;
    AutoCompleteTextView autoCompleteTextView,bedroomautoCompleteTextView,bathroomautoCompleteTextView,kitchenroomautoCompleteTextView;

    TextInputEditText price,propertyLocation;


    RadioGroup radioGroup1;
    EditText  bedRoomsNumber, bathRoomsNumber, kitchenRoomsNumber;
    TextView camera,mark,setLocation;
    Button submit;
    ImageView homeImageView;
    String propertyType, propertyPrice, propertyBedRoomsNumber, propertyBathRoomsNumber, kitchenNumber, location;

    FirebaseDatabase database;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseStorage storage;
    DatabaseReference reference;
    Uri uri;
    String currentuser;
    public int id = 0;
    String userId, keyId;

    ProgressDialog progressDialog;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_home);

       // txt = findViewById(R.id.txt);
        //mark = findViewById(R.id.mark);



        recyclerView = findViewById(R.id.image_recyclerview);
        imageAdapter = new ImageAdapter(urilist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(imageAdapter);


        if(ContextCompat.checkSelfPermission(PostHome.this,Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PostHome.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Read_Permission);
        }



        setLocation = findViewById(R.id.setLocation);
        divisionSpinner = findViewById(R.id.spinner_bangla_division);
        DistrictSpinner = findViewById(R.id.spinner_bangla_district);
        areaSpinner = findViewById(R.id.spinner_bangla_area);
        divisionAdapter = ArrayAdapter.createFromResource(this,R.array.array_bangla_division,R.layout.spinner_layout);

        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(divisionAdapter);

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                selectedDivision = divisionSpinner.getSelectedItem().toString();

                int parentID = adapterView.getId();

                if(parentID == R.id.spinner_bangla_division){
                    switch (selectedDivision){
                        case "Select Your Division": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_default_districts,R.layout.spinner_layout);
                            break;

                        case "Dhaka": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_dhaka_districts,R.layout.spinner_layout);
                            break;

                        case "Barishal": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_barisal_districts,R.layout.spinner_layout);
                            break;

                        case "Sylhet": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_sylhet_districts,R.layout.spinner_layout);
                            break;

                        case "Rangpur": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_rangpur_districts,R.layout.spinner_layout);
                            break;

                        case "Khulna": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_khulna_districts,R.layout.spinner_layout);
                            break;

                        case "Mymensingh": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_mymensingh_districts,R.layout.spinner_layout);
                            break;

                        case "Rajshahi": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_rajshahi_districts,R.layout.spinner_layout);
                            break;

                        case "Chattogram": districtAdapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_chattogram_districts,R.layout.spinner_layout);
                            break;

                        default: break;
                    }

                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    DistrictSpinner.setAdapter(districtAdapter);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedDistrict = DistrictSpinner.getSelectedItem().toString();
                int parentsID = adapterView.getId();
                if(parentsID == R.id.spinner_bangla_district){
                    switch (selectedDistrict){
                        case "Select Your Districts": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_default_aria,R.layout.spinner_layout);
                            break;
                        case "Dhaka": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_dhaka_aria,R.layout.spinner_layout);
                            break;

                        case "Faridpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_faridpur_aria,R.layout.spinner_layout);
                            break;

                        case "Gazipur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_gazipur_aria,R.layout.spinner_layout);
                            break;
                        case "Gopalganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_gopalganj_aria,R.layout.spinner_layout);
                            break;
                        case "Kishoreganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_kishoreganj_aria,R.layout.spinner_layout);
                            break;
                        case "Madaripur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_madairpur_aria,R.layout.spinner_layout);
                            break;
                        case "Manikganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_manikganj_aria,R.layout.spinner_layout);
                            break;
                        case "Munshiganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_munshiganj_aria,R.layout.spinner_layout);
                            break;
                        case "Narayanganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_narayanganj_aria,R.layout.spinner_layout);
                            break;
                        case "Narsingdi": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_narsingdi_aria,R.layout.spinner_layout);
                            break;

                        case "Rajbari": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_rajbari_aria,R.layout.spinner_layout);
                            break;

                        case "Shariatpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_shariatpur_aria,R.layout.spinner_layout);
                            break;
                        case "Tangail": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_tangail_aria,R.layout.spinner_layout);
                            break;
                        case "Barguna": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_barguna_aria,R.layout.spinner_layout);
                            break;
                        case "Barisal": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_barisal_aria,R.layout.spinner_layout);
                            break;
                        case "Bhola": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_bhola_aria,R.layout.spinner_layout);
                            break;
                        case "Jhalokati": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_jhalokati_aria,R.layout.spinner_layout);
                            break;
                        case "Patuakhali": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_patuakhali_aria,R.layout.spinner_layout);
                            break;
                        case "Pirojpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_pirojpur_aria,R.layout.spinner_layout);
                            break;

                        case "Bandarban": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_bandarban_aria,R.layout.spinner_layout);
                            break;
                        case "Brahmanbaria": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_brahmanbaria_aria,R.layout.spinner_layout);
                            break;
                        case "Chandpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_chandpur_aria,R.layout.spinner_layout);
                            break;
                        case "Chittagong": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_chittagong_aria,R.layout.spinner_layout);
                            break;
                        case "Comilla": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_comilla_aria,R.layout.spinner_layout);
                            break;

                        case "Khulna": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_khulna_aria,R.layout.spinner_layout);
                            break;
                        case "Kushtia": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_kushtia_aria,R.layout.spinner_layout);
                            break;
                        case "Magura": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_magura_aria,R.layout.spinner_layout);
                            break;
                        case "Meherpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_meherpur_aria,R.layout.spinner_layout);
                            break;
                        case "Narail": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_narail_aria,R.layout.spinner_layout);
                            break;
                        case "Satkhira": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_satkhira_aria,R.layout.spinner_layout);
                            break;
                        case "Bagerhat": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_bagerhat_aria,R.layout.spinner_layout);
                            break;
                        case "Chuadanga": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_chuadanga_aria,R.layout.spinner_layout);
                            break;
                        case "Jessore": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_jessore_aria,R.layout.spinner_layout);
                            break;
                        case "Jhenaidah": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_jhenaidah_aria,R.layout.spinner_layout);
                            break;
                        case "Bogra": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_bogra_aria,R.layout.spinner_layout);
                            break;
                        case "Joypurhat": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_joypurhat_aria,R.layout.spinner_layout);
                            break;
                        case "Naogaon": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_naogaon_aria,R.layout.spinner_layout);
                            break;
                        case "Natore": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_natore_aria,R.layout.spinner_layout);
                            break;
                        case "Rajshahi": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_rajshahi_aria,R.layout.spinner_layout);
                            break;
                        case "Sirajganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_sirajganj_aria,R.layout.spinner_layout);
                            break;
                        case "Dinajpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_dinajpur_aria,R.layout.spinner_layout);
                            break;
                        case "Gaibandha": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_gaibandha_aria,R.layout.spinner_layout);
                            break;
                        case "Rangpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_rangpur_aria,R.layout.spinner_layout);
                            break;
                        case "Kurigram": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_kurigram_aria,R.layout.spinner_layout);
                            break;
                        case "Lalmonirhat": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_lalmonirhat_aria,R.layout.spinner_layout);
                            break;
                        case "Nilphamari": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_nilphamari_aria,R.layout.spinner_layout);
                            break;
                        case "Panchangarh": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_panchangarh_aria,R.layout.spinner_layout);
                            break;
                        case "Thakurgaon": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_thakurgaon_aria,R.layout.spinner_layout);
                            break;



                        case "Habiganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_habiganj_aria,R.layout.spinner_layout);
                            break;
                        case "Moulvibazar": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_moulvibazar_aria,R.layout.spinner_layout);
                            break;
                        case "Sunamganj": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_sunamganj_aria,R.layout.spinner_layout);
                            break;
                        case "Sylhet": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_sylhet_aria,R.layout.spinner_layout);
                            break;

                        case "Jamalpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_jamalpur_aria,R.layout.spinner_layout);
                            break;
                        case "Mymensingh": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_mymensingh_aria,R.layout.spinner_layout);
                            break;
                        case "Netrokona": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_netrokona_aria,R.layout.spinner_layout);
                            break;
                        case "Sherpur": areaAdapters = ArrayAdapter.createFromResource(adapterView.getContext(),
                                R.array.array_sherpur_aria,R.layout.spinner_layout);
                            break;

                        default:
                            break;
                    }

                    areaAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapters);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArea = areaSpinner.getSelectedItem().toString();
                //Toast.makeText(PostHome.this,selectedArea,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        textInputLayout = findViewById(R.id.menu_drop);
        autoCompleteTextView = findViewById(R.id.drop_items);
        bedroomtextInputLayout = findViewById(R.id.bedroom_menu_drop);
        bedroomautoCompleteTextView = findViewById(R.id.bedroom_drop_items);
        bathroomtextInputLayout = findViewById(R.id.bathroom_menu_drop);
        bathroomautoCompleteTextView = findViewById(R.id.bathroom_drop_items);
        kitchenroomtextInputLayout = findViewById(R.id.kitchenroom_menu_drop);
        kitchenroomautoCompleteTextView = findViewById(R.id.kitchenroom_drop_items);

        //radioGroup1 = findViewById(R.id.radioGroup1);
        price = findViewById(R.id.price);
        //bedRoomsNumber = findViewById(R.id.bedroomsNumber);
        //bathRoomsNumber = findViewById(R.id.bathroomsNumber);
        //kitchenRoomsNumber = findViewById(R.id.kitchenroomsNumber);
        //homeImageView = findViewById(R.id.homeImageView);
        camera = findViewById(R.id.camera);
        submit = findViewById(R.id.submit);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("HomeInfo");
        storage = FirebaseStorage.getInstance();
        user = auth.getCurrentUser();
        currentuser = user.toString();
        propertyLocation = findViewById(R.id.locationView);

        linearLayout = findViewById(R.id.linearLayout);


        progressDialog = new ProgressDialog(PostHome.this);
        progressDialog.setTitle("Post Home");
        progressDialog.setMessage("Posting home info");


     /*   radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        propertyType = "family";
                        break;
                    case R.id.radioButton2:
                        propertyType = "bachelor";
                        break;
                    case R.id.radioButton3:
                        propertyType = "sublet";
                        break;

                }
            }
        });*/


        String [] items = {"family","bachelor","sublet"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(PostHome.this,R.layout.items_list,items);
        autoCompleteTextView.setAdapter(itemAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                propertyType = itemAdapter.getItem(i);
                //Toast.makeText(PostHome.this,propertyType,Toast.LENGTH_LONG).show();
            }
        });


        String [] bedroomitems = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> bedroomitemAdapter = new ArrayAdapter<>(PostHome.this,R.layout.bedroom_items_list,bedroomitems);
        bedroomautoCompleteTextView.setAdapter(bedroomitemAdapter);
        bedroomautoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                propertyBedRoomsNumber = bedroomitemAdapter.getItem(i);
            }
        });


        String [] bathroomitems = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> bathroomitemAdapter = new ArrayAdapter<>(PostHome.this,R.layout.items_list,bathroomitems);
        bathroomautoCompleteTextView.setAdapter(bathroomitemAdapter);
        bathroomautoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                propertyBathRoomsNumber = bathroomitemAdapter.getItem(i);
            }
        });

        String [] kitchenroomitems = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> kitchenroomitemAdapter = new ArrayAdapter<>(PostHome.this,R.layout.items_list,kitchenroomitems);
        kitchenroomautoCompleteTextView.setAdapter(kitchenroomitemAdapter);
        kitchenroomautoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                kitchenNumber = kitchenroomitemAdapter.getItem(i);
            }
        });






       /* MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayout,mapFragment);
        transaction.commit();*/


        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);

        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();

                    }
                }).check();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueSubmit();
            }
        });

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlocation();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* ImagePicker.with(PostHome.this)
                        //.crop()	    			//Crop image(Optional), Check Customization for more option
                        // .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        // .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();*/

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);


            }
        });

    }



    private void getmylocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                smf.getMapAsync(new OnMapReadyCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {


                        googleMap.setMyLocationEnabled(true);
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions markerOptions =  new MarkerOptions().position(latLng);

                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                            locate = latLng.latitude;
                            logn = latLng.longitude;



                        //Toast.makeText(PostHome.this, locName, Toast.LENGTH_SHORT).show();

                       /* Geocoder coder = new Geocoder(PostHome.this);
                        List<Address> address;
                        try {
                            address = coder.getFromLocationName(locName,5);
                            Address locations = address.get(0);
                            LatLng latLng = new LatLng(locations.getLatitude(),locations.getLongitude());
                            MarkerOptions markerOptions =  new MarkerOptions().position(latLng);

                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                            locate = latLng.latitude;
                            logn = latLng.longitude;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
*/


                       // locate = String.valueOf(locates);
                       // logn = String.valueOf(logns);


                         //locate = String.valueOf(location.getLatitude());
                        //  logn = String.valueOf(location.getLongitude());



                    }
                });

            }
        });

    }
    private void getlocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                smf.getMapAsync(new OnMapReadyCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {


                        googleMap.setMyLocationEnabled(true);
                        if(selectedDivision.equals("")||selectedDistrict.equals("")||selectedArea.equals("")){
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions markerOptions =  new MarkerOptions().position(latLng);

                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                            locate = latLng.latitude;
                            logn = latLng.longitude;

                        }
                        else {
                            String locName = selectedDistrict+selectedDivision+selectedArea;
                            Geocoder coder = new Geocoder(PostHome.this);
                            List<Address> address;
                            try {
                                address = coder.getFromLocationName(locName,5);
                                Address locations = address.get(0);
                                LatLng latLng = new LatLng(locations.getLatitude(),locations.getLongitude());
                                MarkerOptions markerOptions =  new MarkerOptions().position(latLng);

                                googleMap.addMarker(markerOptions);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                                locate = latLng.latitude;
                                logn = latLng.longitude;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }



                        //Toast.makeText(PostHome.this, locName, Toast.LENGTH_SHORT).show();

                       /* Geocoder coder = new Geocoder(PostHome.this);
                        List<Address> address;
                        try {
                            address = coder.getFromLocationName(locName,5);
                            Address locations = address.get(0);
                            LatLng latLng = new LatLng(locations.getLatitude(),locations.getLongitude());
                            MarkerOptions markerOptions =  new MarkerOptions().position(latLng);

                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                            locate = latLng.latitude;
                            logn = latLng.longitude;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
*/


                        // locate = String.valueOf(locates);
                        // logn = String.valueOf(logns);


                        //locate = String.valueOf(location.getLatitude());
                        //  logn = String.valueOf(location.getLongitude());



                    }
                });

            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      //  uri = data.getData();
       // propertyImage = uri.getPath().toString();
       // homeImageView.setImageURI(uri);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            if(data.getClipData()!= null){
                int x = data.getClipData().getItemCount();
                for(int i=0;i<x;i++){
                    urilist.add(data.getClipData().getItemAt(i).getUri());
                }
                imageAdapter.notifyDataSetChanged();
                uri = data.getClipData().getItemAt(0).getUri();

            }else if(data.getData() != null){
                String imageUrl = data.getData().getPath();
                urilist.add(Uri.parse(imageUrl));
                uri = data.getData();

            }
        }



    }
    private void valueSubmit() {


        propertyPrice = price.getText().toString().trim();
        //propertyBedRoomsNumber = bedRoomsNumber.getText().toString().trim();
       // propertyBathRoomsNumber = bathRoomsNumber.getText().toString().trim();
       // kitchenNumber = kitchenRoomsNumber.getText().toString().trim();
        location = propertyLocation.getText().toString().trim();
        //reference = database.getReference().child("HomeInfo").child(propertyType);


        progressDialog.show();
        StorageReference filepath = storage.getReference().child("imagePost").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            progressDialog.dismiss();
                            DatabaseReference newPost = reference.push();
                            keyId = newPost.getKey();
                            PostHomeModel model = new PostHomeModel(propertyBathRoomsNumber, propertyBedRoomsNumber, propertyType, task.getResult().toString(), kitchenNumber, location, propertyPrice, userId, keyId, selectedDivision, selectedDistrict, selectedArea);


                            newPost.setValue(model);


                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Location").child(keyId);

                            LocationModel locationModel = new LocationModel(locate, logn);

                            ref.setValue(locationModel);


                            if (task.isSuccessful()) {
                                Intent intent = new Intent(PostHome.this, HomePage.class);
                                startActivity(intent);
                                Toast.makeText(PostHome.this, "Post submit successfull", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PostHome.this, "can not submit post", Toast.LENGTH_LONG).show();

                            }

                        }
                    });


                }
            });


        StorageReference filepathh = storage.getReference().child("imagepost");

        for(int i=0;i<urilist.size();i++) {
            Uri inImage = urilist.get(i);
            StorageReference imageName = filepathh.child(inImage.getLastPathSegment());
            imageName.putFile(inImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String urll = String.valueOf(uri);
                            StoreLink(urll);
                        }
                    });

                }
            });
        }



    }

    private void StoreLink(String url) {

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("ImageName").child(keyId);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Imagelink",url);
        dref.push().setValue(hashMap);

    }


}
