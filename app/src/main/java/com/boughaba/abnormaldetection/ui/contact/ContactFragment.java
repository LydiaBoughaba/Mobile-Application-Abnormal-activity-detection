package com.boughaba.abnormaldetection.ui.contact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boughaba.abnormaldetection.R;
import com.boughaba.abnormaldetection.model.Contact;
import com.boughaba.abnormaldetection.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";

    private ContactViewModel contactViewModel;
    private ContactsAdapter adapter;

    @BindView(R.id.recycler_view_contacts)
    RecyclerView recyclerView;
    private Bundle bundle;
    private Unbinder unbinder;
    @BindView(R.id.bt_add)
    FloatingActionButton btAdd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bundle = savedInstanceState;
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        unbinder = ButterKnife.bind(this, root);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT /*| ItemTouchHelper.RIGHT*/) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contactViewModel.deleteContact(adapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ContactsAdapter(); // Add this
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Add this
        recyclerView.setAdapter(adapter); // Add this
        contactViewModel.getListContact().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setItems(contacts);
            }
        });
    }


    @OnClick(R.id.bt_add)
    public void addClicked() {
        Log.e(TAG, "addClicked: ");
        Intent intent = new Intent(getActivity(), AddContactActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}