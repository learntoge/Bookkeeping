package cn.shuzilearn.bookkeeping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.noteDao;
import cn.shuzilearn.bookkeeping.pojo.note;


import java.util.List;

public class NoteAdapter extends ArrayAdapter<note> {
    List<note> objects;
    private noteDao dao;

    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<note> objects) {
        super(context, resource, objects);
        this.objects = objects;
        dao = Myapplication.getInstance().getDatabase().notedao();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.note_module, null);
        }
        note thenote = getItem(position);
        convertView.setId(thenote.getId());
        convertView.setTag(thenote);
        TextView tt = convertView.findViewById(R.id.note_title);
        TextView te = convertView.findViewById(R.id.note_text);
        TextView ti = convertView.findViewById(R.id.note_time);
        tt.setText(thenote.getTitle());
        te.setText(thenote.getContent());
        ti.setText("创建时间：" + thenote.getCreateTime());
        convertView.setTag(thenote);
        convertView.findViewById(R.id.delete_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int anoteID = thenote.getId();
                dao.deleteOfID(anoteID);
                objects.remove(position);  // 你可以根据你使用的 List 实现修改这行
                notifyDataSetChanged();
            }
        });
        return convertView;

    }
}
