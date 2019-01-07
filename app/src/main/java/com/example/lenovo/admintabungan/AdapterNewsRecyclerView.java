package com.example.lenovo.admintabungan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class AdapterNewsRecyclerView extends RecyclerView.Adapter<AdapterNewsRecyclerView.ViewHolder> {

    private ArrayList<News> daftarNews;
    private Context context;
    FirebaseDataListener listener;



    public AdapterNewsRecyclerView(ArrayList<News> newss, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarNews = newss;
        context = ctx;
        listener = (FirebaseDBReadActivity)ctx;

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView rvGambar;
        public TextView rvJudul,rvTitik;

        ViewHolder(View v) {
            super(v);
            rvGambar   	= itemView.findViewById(R.id.rv_gambar);
            rvJudul 	= itemView.findViewById(R.id.rv_judul);
            rvTitik	    = itemView.findViewById(R.id.rv_titik);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String name = daftarNews.get(position).getJudul();

        System.out.println("News Data one by one " + position + daftarNews.size());
        holder.rvJudul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(FirebaseDBReadSingleActivity.getActIntent((Activity) context).putExtra("data", daftarNews.get(position)));
            }
        });


        holder.rvTitik.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        context.startActivity(FirebaseDBUpdateActivity.getActIntent((Activity) context).putExtra("data", daftarNews.get(position)));
                    }
                });

                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 *  Kodingan Delete
                                 */
                                listener.onDeleteData(daftarNews.get(position), position);
                            }
                        }
                );
                return true;
            }
        });
        holder.rvJudul.setText(name);
    }


    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada News
         */
        return daftarNews.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(News news, int position);
    }
}
