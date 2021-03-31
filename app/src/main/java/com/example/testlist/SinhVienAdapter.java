package com.example.testlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SinhVienAdapter extends BaseAdapter implements Filterable {

    Context context;
    int layout;
    private List<SinhVien> sinhVienList;
    private List<SinhVien> sinhVienListTwo;

    public SinhVienAdapter(Context context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
        this.sinhVienListTwo = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_sinh_vien,null);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvDateOfbirth = convertView.findViewById(R.id.tvDateOfbirth);
        TextView tvPhonenumber = convertView.findViewById(R.id.tvPhonenumber);
        TextView tvSpecialized = convertView.findViewById(R.id.tvSpecialized);
        TextView tvLevel = convertView.findViewById(R.id.tvLevel);

        SinhVien sinhVien = sinhVienList.get(position);
        tvName.setText(sinhVien.Name);
        tvDateOfbirth.setText(sinhVien.dateOfbirth);
        tvPhonenumber.setText(sinhVien.phoneNumber);
        tvSpecialized.setText(sinhVien.specialized);
        tvLevel.setText(sinhVien.Level);

        Collections.sort(sinhVienList, new Comparator<SinhVien>() {
            @Override
            public int compare(SinhVien sv1, SinhVien sv2) {
                return (sv1.getName().compareTo(sv2.getName()));
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();

                if(search.isEmpty()){
                    sinhVienList = sinhVienListTwo;
                }else {
                    List<SinhVien> list = new ArrayList<>();
                    for(SinhVien sinhVien : sinhVienListTwo){
                        if(sinhVien.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(sinhVien);
                        }
                    }
                    sinhVienList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sinhVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sinhVienList = (List<SinhVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
