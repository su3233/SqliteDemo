package com.sts.sqlitedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sts.sqlitedemo.R;
import com.sts.sqlitedemo.bean.Person;

import java.util.List;

/**
 * @author SuTs
 * @create 2019/10/29
 * @Describe
 */
public class PageOffsetAdapter extends BaseAdapter {
    private Context mContext;
    private List<Person> personList;

    public PageOffsetAdapter(Context mContext, List<Person> personList) {
        this.mContext = mContext;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int i) {
        return personList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PersonViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cursor_lv, null);
            viewHolder = new PersonViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PersonViewHolder) convertView.getTag();
        }
        viewHolder.tvId.setText(personList.get(position).get_id()+"");
        viewHolder.tvAge.setText(personList.get(position).getAge() + "");
        viewHolder.tvName.setText(personList.get(position).getName());
        return convertView;
    }

    static class PersonViewHolder {
        private final TextView tvAge, tvName, tvId;

        public PersonViewHolder(View view) {
            tvId = view.findViewById(R.id.tv_id);
            tvName = view.findViewById(R.id.tv_name);
            tvAge = view.findViewById(R.id.tv_age);

        }
    }

}
