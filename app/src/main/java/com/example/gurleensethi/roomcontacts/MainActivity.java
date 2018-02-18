package com.example.gurleensethi.roomcontacts;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gurleensethi.roomcontacts.db.AppDatabase;
import com.example.gurleensethi.roomcontacts.db.ContactDAO;
import com.example.gurleensethi.roomcontacts.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_CREATE_CONTACT = 1;
    private static final int RC_UPDATE_CONTACT = 2;
    private RecyclerView mContactsRecyclerView;
    private ContactRecyclerAdapter mContactRecyclerAdapter;
    private FloatingActionButton mAddContactFloatingActionButton;
    private ContactDAO mContactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactDAO = Room.databaseBuilder(this, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getContactDAO();

        mContactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        mContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddContactFloatingActionButton = findViewById(R.id.addContactFloatingActionButton);

        int colors[] = {ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, android.R.color.holo_red_light),
                ContextCompat.getColor(this, android.R.color.holo_orange_light),
                ContextCompat.getColor(this, android.R.color.holo_green_light),
                ContextCompat.getColor(this, android.R.color.holo_blue_dark),
                ContextCompat.getColor(this, android.R.color.holo_purple)};

        mContactRecyclerAdapter = new ContactRecyclerAdapter(this, new ArrayList<Contact>(), colors);
        mContactRecyclerAdapter.addActionCallback(new ContactRecyclerAdapter.ActionCallback() {
            @Override
            public void onLongClickListener(Contact contact) {
                Intent intent = new Intent(MainActivity.this, UpdateContactActivity.class);
                intent.putExtra(UpdateContactActivity.EXTRA_CONTACT_ID, contact.getPhoneNumber());
                startActivityForResult(intent, RC_UPDATE_CONTACT);
            }
        });
        mContactsRecyclerView.setAdapter(mContactRecyclerAdapter);

        mAddContactFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateContactActivity.class);
                startActivityForResult(intent, RC_CREATE_CONTACT);
            }
        });

        loadContacts();
    }

    private void loadContacts() {
        mContactRecyclerAdapter.updateData(mContactDAO.getContacts());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CREATE_CONTACT && resultCode == RESULT_OK) {
            loadContacts();
        } else if (requestCode == RC_UPDATE_CONTACT && resultCode == RESULT_OK) {
            loadContacts();
        }
    }
}
