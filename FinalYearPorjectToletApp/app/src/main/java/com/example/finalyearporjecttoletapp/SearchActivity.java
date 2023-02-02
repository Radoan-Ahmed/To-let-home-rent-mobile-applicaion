package com.example.finalyearporjecttoletapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SearchActivity extends AppCompatActivity {

    String selectedDivision, selectedDistrict, selectedArea;
    Spinner divisionSpinner,DistrictSpinner,areaSpinner;
    ArrayAdapter<CharSequence> divisionAdapter,districtAdapter,areaAdapters;

    EditText searchLocation,firstAmount, secondAmount;
    Button searchBt;
    RadioGroup radioGroup1,radioGroup2,radioGroup3;
    String location, fAmount, sAmount, propertyType, propertyBedroomitems, bathroomAmount,kitchenNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);




        /*searchLocation = findViewById(R.id.searchLocation);
        firstAmount = findViewById(R.id.firstAmount);
        secondAmount = findViewById(R.id.secondAmount);*/
        searchBt = findViewById(R.id.searchButton);


        divisionSpinner = findViewById(R.id.spinner_bangla_divisions);
        DistrictSpinner = findViewById(R.id.spinner_bangla_districts);
        areaSpinner = findViewById(R.id.spinner_bangla_areas);
        divisionAdapter = ArrayAdapter.createFromResource(this,R.array.array_bangla_division,R.layout.spinner_layout);

        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(divisionAdapter);

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                selectedDivision = divisionSpinner.getSelectedItem().toString();

                int parentID = adapterView.getId();

                if(parentID == R.id.spinner_bangla_divisions){
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
                if(parentsID == R.id.spinner_bangla_districts){
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       /* String [] items = {"family","bachelor","sublet"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(SearchActivity.this,R.layout.items_list,items);
        autoCompleteTextView.setAdapter(itemAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                propertyType = itemAdapter.getItem(i);
                //Toast.makeText(PostHome.this,propertyType,Toast.LENGTH_LONG).show();
            }
        });


        String [] bedroomitems = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> bedroomitemAdapter = new ArrayAdapter<>(SearchActivity.this,R.layout.bedroom_items_list,bedroomitems);
        bedroomautoCompleteTextView.setAdapter(bedroomitemAdapter);
        bedroomautoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                propertyBedroomitems = bedroomitemAdapter.getItem(i);
            }
        });


        String [] bathroomitems = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> bathroomitemAdapter = new ArrayAdapter<>(SearchActivity.this,R.layout.items_list,bathroomitems);
        bathroomautoCompleteTextView.setAdapter(bathroomitemAdapter);
        bathroomautoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bathroomAmount = bathroomitemAdapter.getItem(i);
            }
        });

        String [] kitchenroomitems = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> kitchenroomitemAdapter = new ArrayAdapter<>(SearchActivity.this,R.layout.items_list,kitchenroomitems);
        kitchenroomautoCompleteTextView.setAdapter(kitchenroomitemAdapter);
        kitchenroomautoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                kitchenNumber = kitchenroomitemAdapter.getItem(i);
            }
        });


*/


      /*  radioGroup1 = findViewById(R.id.radioGrouped1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);


        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonP:
                        propertyType = "famiry";
                        break;
                    case R.id.radioButtonP2:
                        propertyType = "bachelor";
                        break;
                    case R.id.radioButtonP3:
                        propertyType = "sublet";
                        break;

                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonB1:
                        bedroomAmount = "1";
                        break;
                    case R.id.radioButtonB2:
                        bedroomAmount = "2";
                        break;
                    case R.id.radioButtonB3:
                        bedroomAmount = "3";
                        break;
                    case R.id.radioButtonB4:
                        bedroomAmount = "4";
                        break;
                }
            }
        });


        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonBa1:
                        bathroomAmount = "1";
                        break;
                    case R.id.radioButtonBa2:
                        bathroomAmount = "2";
                        break;
                    case R.id.radioButtonBa3:
                        bathroomAmount = "3";
                        break;
                    case R.id.radioButtonBa4:
                        bathroomAmount = "4";
                        break;
                }
            }
        });
*/

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*location = searchLocation.getText().toString().trim();
                fAmount = firstAmount.getText().toString();
                sAmount = secondAmount.getText().toString();*/

                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
               /* intent.putExtra("Location",location);
                intent.putExtra("FAmount",fAmount);
                intent.putExtra("SAmount",sAmount);
                intent.putExtra("PropertyType",propertyType);
                intent.putExtra("bedRoom",propertyBedroomitems);
                intent.putExtra("bathRoom",bathroomAmount);
                intent.putExtra("kitchenroom",kitchenNumber);*/
                intent.putExtra("district",selectedDistrict);
                intent.putExtra("division",selectedDivision);
                intent.putExtra("area",selectedArea);
                startActivity(intent);

            }
        });



    }
}