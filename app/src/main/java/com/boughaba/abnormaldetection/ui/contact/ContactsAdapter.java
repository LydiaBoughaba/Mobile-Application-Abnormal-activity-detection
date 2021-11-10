package com.boughaba.abnormaldetection.ui.contact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.model.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> {
    private static final String TAG = "ContactAdapter";

    private Context context;
    private List<Contact> items = new ArrayList<>();
    String[] colors = new String[]{"#FF4057",
            "#113A5D",
            "#5E63B6",
            "#E43A19",
            "#2B3595"
    };

    public void setItems(List<Contact> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    public Contact getContactAt(int position) {
        return items.get(position);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardViewContainer;
        CardView cardViewLetter;
        TextView textViewLetters;
        TextView textViewName;
        TextView textViewPhoneNumber;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewContainer = itemView.findViewById(R.id.cardViewContainer);
            cardViewLetter = itemView.findViewById(R.id.letterCard);
            textViewLetters = itemView.findViewById(R.id.tv_letters);
            textViewName = itemView.findViewById(R.id.tv_name);
            textViewPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            cardViewContainer.setOnClickListener(this);
        }

        public void bind(int position) {
            Random random = new Random();
            Contact contact = items.get(position);
            cardViewLetter.setCardBackgroundColor(Color.parseColor(colors[random.nextInt(colors.length)]));
            textViewLetters.setText(contact.getFirstName().toUpperCase().charAt(0) + "" + contact.getName().toUpperCase().charAt(0));
            textViewName.setText(capitalize(contact.getFirstName()) + " " + capitalize(contact.getName()));
            textViewPhoneNumber.setText(contact.getPhoneNumber());
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Intent intent = new Intent(v.getContext(), ModifyContactActivity.class);
            intent.putExtra("contact", items.get(position));
            v.getContext().startActivity(intent);
        }

        String capitalize(String str) {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }
}
