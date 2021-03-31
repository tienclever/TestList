package com.example.testlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<SinhVien> sinhVienArrayList;
    private ArrayList<SinhVien> sinhVienArrayListSecond;
    private SinhVienAdapter adapter;
    ListView lvSinhVien;
    EditText etName;
    EditText etDateOfBirth;
    EditText etPhone;
    EditText etSpecialized;
    EditText etLevel;
    Button btnThem;
    Button btnUpdate;
    Button btnUniversity;
    Button btnColeges;
    Button btnAll;
    SearchView searchView;
    int vitri = -1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        DeleteItemList();
        sinhVienArrayList = new ArrayList<>();
        sinhVienArrayListSecond = new ArrayList<>();

        adapter = new SinhVienAdapter(this, R.layout.dong_sinh_vien, sinhVienArrayListSecond);
        lvSinhVien.setAdapter((ListAdapter) adapter);

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fullName = etName.getText().toString();
                    String specialized = etSpecialized.getText().toString();
                    String level = etLevel.getText().toString();

                    String checkNullDate = etDateOfBirth.getText().toString();
                    String checkNullPhone = etPhone.getText().toString();

                    if(fullName.length()>0 && specialized.length()>0 && checkNullDate.length()>0 && checkNullPhone.length()>0) {
                        try {
                            if (sinhVienArrayList.isEmpty()){
                                sinhVienArrayList.add(new SinhVien(fullName, checkNullDate, checkNullPhone, specialized, level));
                            }else {
                                for(SinhVien i : sinhVienArrayList){
                                    if(checkNullPhone != i.phoneNumber){
                                        sinhVienArrayList.add(new SinhVien(fullName, checkNullDate, checkNullPhone, specialized, level));
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Số điện thoại đã trùng!", Toast.LENGTH_SHORT).show();
                                        sinhVienArrayList.remove(new SinhVien(fullName, checkNullDate, checkNullPhone, specialized, level));
                                    }
                                }
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        sinhVienArrayListSecond.clear();
                        sinhVienArrayListSecond.addAll(sinhVienArrayList);
                        lvSinhVien.invalidateViews();
                        DeleteEditext();
                    }else {
                        Toast.makeText(getApplicationContext(), "Bạn nhập thiếu thông tin!", Toast.LENGTH_SHORT).show();
                    }

                }

            });


        lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                etName.setText(sinhVienArrayListSecond.get(position).getName());
                etDateOfBirth.setText(sinhVienArrayListSecond.get(position).getDateOfbirth());
                etPhone.setText(sinhVienArrayListSecond.get(position).getPhoneNumber());
                etSpecialized.setText(sinhVienArrayListSecond.get(position).getSpecialized());
                etLevel.setText(sinhVienArrayListSecond.get(position).getLevel());
                vitri = position;
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = etName.getText().toString();
                String specialized = etSpecialized.getText().toString();
                String checkNullDate = etDateOfBirth.getText().toString();
                String checkNullPhone = etPhone.getText().toString();
                String level = etLevel.getText().toString();
                sinhVienArrayListSecond.set(vitri, new SinhVien(fullName, checkNullDate, checkNullPhone, specialized, level));
                lvSinhVien.invalidateViews();
            }
        });

        btnUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sinhVienArrayListSecond.clear();
                for (SinhVien itemUniversity:sinhVienArrayList) {
                    if (itemUniversity.getLevel().equals("DH")) {
                        sinhVienArrayListSecond.add(itemUniversity);
                    }
                }
                lvSinhVien.invalidateViews();
            }
        });

        btnColeges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sinhVienArrayListSecond.clear();
                for(SinhVien itemColeges: sinhVienArrayList){
                    if(itemColeges.getLevel().equals("CD")){
                        sinhVienArrayListSecond.add(itemColeges);
                    }
                }
                lvSinhVien.invalidateViews();
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinhVienArrayListSecond.clear();
                sinhVienArrayListSecond.addAll(sinhVienArrayList);
                lvSinhVien.invalidateViews();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void DeleteEditext() {
        etName.getText().clear();
        etDateOfBirth.getText().clear();
        etLevel.getText().clear();
        etSpecialized.getText().clear();
        etPhone.getText().clear();
    }


    private void DeleteItemList() {
        lvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("xác nhận xóa");
                builder.setMessage("bạn có chắc xóa học sinh!");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sinhVienArrayListSecond.remove(position);
                        sinhVienArrayList.clear();
                        sinhVienArrayList.addAll(sinhVienArrayListSecond);
                        lvSinhVien.invalidateViews();
                    }
                });
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lvSinhVien.invalidateViews();
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    public void anhXa() {
        lvSinhVien = findViewById(R.id.lvSinhVien);
        etName = findViewById(R.id.etName);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etPhone = findViewById(R.id.etPhone);
        etSpecialized = findViewById(R.id.etSpecialized);
        etLevel = findViewById(R.id.etLevel);
        btnThem = findViewById(R.id.btnThem);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUniversity = findViewById(R.id.btnUniversity);
        btnColeges = findViewById(R.id.btnColeges);
        btnAll = findViewById(R.id.btnAll);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}