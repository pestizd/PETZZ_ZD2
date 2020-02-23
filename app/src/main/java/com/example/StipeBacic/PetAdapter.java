package com.example.StipeBacic;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class PetAdapter extends ListAdapter<IDPet, PetAdapter.BiljHolder> {
    private OnItemClickListener listener;



    public PetAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<IDPet> DIFF_CALLBACK = new DiffUtil.ItemCallback<IDPet>() {
        @Override
        public boolean areItemsTheSame(IDPet oldItem, IDPet newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(IDPet oldItem, IDPet newItem) {
            return oldItem.getNaslov().equals(newItem.getNaslov()) &&
                    oldItem.getDeskripcija().equals(newItem.getDeskripcija()) &&
                    oldItem.getPrioritet() == newItem.getPrioritet();
        }
    };

    @NonNull
    @Override
    public BiljHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entit_pet, parent, false);
        return new BiljHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull BiljHolder holder, int position) {
        IDPet trenutneBiljeske = getItem(position);
        holder.textViewNaslov.setText(trenutneBiljeske.getNaslov());
        holder.textViewDeskripcija.setText(trenutneBiljeske.getDeskripcija());
        holder.textViewPrioritet.setText(String.valueOf(trenutneBiljeske.getPrioritet()));
    }



    public IDPet getBiljeskeAt(int position) {
        return getItem(position);
    }


    class BiljHolder extends RecyclerView.ViewHolder {
        private TextView textViewNaslov;
        private TextView textViewDeskripcija;
        private TextView textViewPrioritet;

        public BiljHolder(View itemView) {
            super(itemView);
            textViewNaslov = itemView.findViewById(R.id.text_view_naslov);
            textViewDeskripcija = itemView.findViewById(R.id.text_view_deskripcija);
            textViewPrioritet= itemView.findViewById(R.id.text_view_prioritet);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }

            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(IDPet IDPet);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}