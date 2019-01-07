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



public class AdapterAsistenRecyclerView extends RecyclerView.Adapter<AdapterAsistenRecyclerView.ViewHolder> {

    private ArrayList<Asisten> daftarAsisten;
    private Context context;
    FirebaseDataListener listener;



    public AdapterAsistenRecyclerView(ArrayList<Asisten> asistens, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarAsisten = asistens;
        context = ctx;
        listener = (FirebaseDBReadAsisten)ctx;

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView rvGambar;
        public TextView rvNama,rvPilih,rvEmail;

        ViewHolder(View v) {
            super(v);
            rvGambar   	= itemView.findViewById(R.id.rv_gambar);
            rvNama 	    = itemView.findViewById(R.id.rv_nama);
            rvEmail	    = itemView.findViewById(R.id.rv_email);
            rvPilih	    = itemView.findViewById(R.id.rv_titik);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asisten, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String name = daftarAsisten.get(position).getNama();
        final String email = daftarAsisten.get(position).getEmail();
        System.out.println("Asisten Data one by one " + position + daftarAsisten.size());
        holder.rvNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(FirebaseDBReadSingleAsisten.getActIntent((Activity) context).putExtra("data", daftarAsisten.get(position)));
            }
        });


        holder.rvPilih.setOnLongClickListener(new View.OnLongClickListener() {
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
                        context.startActivity(FirebaseDBUpdateAsisten.getActIntent((Activity) context).putExtra("data", daftarAsisten.get(position)));
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
                                listener.onDeleteData(daftarAsisten.get(position), position);
                            }
                        }
                );
                return true;
            }
        });
        holder.rvNama.setText(name);
        holder.rvEmail.setText(email);
    }


    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada Asisten
         */
        return daftarAsisten.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Asisten asisten, int position);
    }
}
