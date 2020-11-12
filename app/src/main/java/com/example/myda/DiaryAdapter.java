package com.example.myda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myda.dao.Note;

import java.util.List;


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>
{

    public List<Note> getNoteData() {
        return noteData;
    }

    public void setNoteData(List<Note> noteData) {
        this.noteData = noteData;
        notifyDataSetChanged();
    }

    private List<Note> noteData ;

    public interface ItemClickListner
    {
        void onItemClick(View view, int position);
    }

    public  ItemClickListner mListner;

    public void setOnItemClickListner(ItemClickListner listner)
    {
        this.mListner=listner;
    }

    public DiaryAdapter(List<Note> noteList )
    {
        noteData=noteList;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new DiaryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false));
    }


    @Override
    //绑定数据
    public void onBindViewHolder(final DiaryViewHolder holder, int position)
    {
        //将数据填充到具体的view中
        TextView ti=holder.mTexView;
        TextView da=holder.newsTime;
        TextView co=holder.content;
        TextView na = holder.etname;

        ti.setText(noteData.get(position).getTitle());
        da.setText(noteData.get(position).getDate());
        co.setText(noteData.get(position).getContent());
        na.setText(noteData.get(position).getAuthor());
        if (mListner!=null) holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                mListner.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    //返回数据集合大小
    public int getItemCount()
    {
        return noteData==null?0:noteData.size();
    }

    //获取对应布局数据
    public class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView mTexView;
        TextView newsTime;
        TextView content;
        TextView etname;
        DiaryViewHolder(View view)
        {
            super(view);
            mTexView=(TextView)view.findViewById(R.id.title_view);
            newsTime=(TextView)view.findViewById(R.id.date_view);
            content=(TextView)view.findViewById(R.id.content_view);
            etname = (TextView)view.findViewById(R.id.name_view);
        }
    }

}
