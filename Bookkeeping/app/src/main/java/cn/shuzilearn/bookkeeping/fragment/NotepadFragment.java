package cn.shuzilearn.bookkeeping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.adapter.NoteAdapter;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.noteDao;
import cn.shuzilearn.bookkeeping.page.EditnoteActivity;
import cn.shuzilearn.bookkeeping.pojo.note;

import java.time.LocalDate;
import java.util.List;


public class NotepadFragment extends Fragment implements AdapterView.OnItemClickListener {


    private noteDao notedao;
    private View view;
    private List<note> notes;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notepad, container, false);
        notedao = Myapplication.getInstance().getDatabase().notedao();
        notes = notedao.getAll();

        setNotedao();

        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setNotedao();
    }

    private void setNotedao() {
        notes = notedao.getAll();
        if (notes.size()<=0) {
            String date = LocalDate.now().toString();
            notes.add(new note("这是一篇展示的笔记","这是一篇默认生成的笔记，当你创建了新的笔记，这篇笔记将自动删除，我将介绍一下这个如何使用：\n1.请点击笔记列表右上角的+按钮，可以创建一个新的笔记\n" +
                    "2.当你编辑完成后，请点击右上角的保存按钮，将笔记保存下来\n3.点击左上角的返回按钮，可以退出笔记编辑器",date));
//            tip.setText("你还没有笔记，快去创建一个吧~");
        }

            listView = view.findViewById(R.id.showNoteList);
            NoteAdapter noteAdapter = new NoteAdapter(getContext(),R.layout.note_module,notes);
            listView.setAdapter(noteAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        note n = (note) view.getTag();
        Intent intent = new Intent(getContext(), EditnoteActivity.class);
        intent.putExtra("id", n.getId());
        intent.putExtra("title",n.getTitle());
        intent.putExtra("content",n.getContent());
        intent.putExtra("jointime",n.getCreateTime());

        startActivity(intent);
    }
}