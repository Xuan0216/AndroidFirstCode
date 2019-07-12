package cn.edu.hznu.providertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private int resourceId;

    public  ContactAdapter(Context context, int textViewResourceId, List<Contact> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        Contact contact=getItem(position);//获取当前项的实例
        View view=LayoutInflater.from(getContext()).
                    inflate(resourceId,parent,false);//加载布局
        TextView Name = (TextView) view.findViewById(R.id.contact_name);
        TextView Mobile = (TextView) view.findViewById(R.id.contact_mobile);
        Name.setText(contact.getName());
        Mobile.setText(contact.getMobile());
        return view;
    }
}
