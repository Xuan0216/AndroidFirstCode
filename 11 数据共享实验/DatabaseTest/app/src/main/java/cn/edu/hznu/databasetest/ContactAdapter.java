package cn.edu.hznu.databasetest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private int resourceId;
    private int[] colors=new int[]{0x30FF0000,0x300000FF};
    private Map<Integer,Boolean> isCheck=new HashMap<Integer, Boolean>();//CheckBox 是否选择的存储集合

    public  ContactAdapter(Context context, int textViewResourceId, List<Contact> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        Contact contact=getItem(position);//获取当前项的实例
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.cbCheckBox = (CheckBox) view.findViewById(R.id.check_box);
            viewHolder.personName = (TextView) view.findViewById(R.id.contact_name);
            viewHolder.phoneNumber = (TextView) view.findViewById(R.id.contact_mobile);
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        // 勾选框的点击事件
        viewHolder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck.put(position, isChecked);
            }
        });
        if (isCheck.get(position) == null) {
            isCheck.put(position, false);
        }
        viewHolder.cbCheckBox.setChecked(isCheck.get(position));
        viewHolder.personName.setText(contact.getName());
        viewHolder.phoneNumber.setText(contact.getMobile());

        //联系人界面背景颜色设置
        int colorPosition=position % colors.length;
        if(colorPosition==1)
            view.setBackgroundColor(Color.argb(250, 176, 196, 222));
        else
            view.setBackgroundColor(Color.argb(250, 255, 255, 255));

        return view;
    }
    class ViewHolder{
        CheckBox cbCheckBox;
        TextView personName;
        TextView phoneNumber;
    }
    public Map<Integer, Boolean> getMap() {
        // 返回状态
        return isCheck;
    }
}
