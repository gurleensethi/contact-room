package com.example.gurleensethi.roomcontacts;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gurleensethi.roomcontacts.models.Contact;

import java.util.List;

/**
 * Created by gurleensethi on 03/02/18.
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder> {

    //Interface for callbacks
    interface ActionCallback {
        void onLongClickListener(Contact contact);
    }

    private Context context;
    private List<Contact> contactList;
    private int[] colors;
    private ActionCallback mActionCallbacks;

    ContactRecyclerAdapter(Context context, List<Contact> contactList, int[] colors) {
        this.context = context;
        this.contactList = contactList;
        this.colors = colors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    void updateData(List<Contact> contacts) {
        this.contactList = contacts;
        notifyDataSetChanged();
    }

    //View Holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView mNameTextView;
        private TextView mInitialsTextView;
        private GradientDrawable mInitialsBackground;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(this);

            mInitialsTextView = itemView.findViewById(R.id.initialsTextView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
            mInitialsBackground = (GradientDrawable) mInitialsTextView.getBackground();
        }

        void bindData(int position) {
            Contact contact = contactList.get(position);

            String fullName = contact.getFirstName() + " " + contact.getLastName();
            mNameTextView.setText(fullName);

            String initial = contact.getFirstName().toUpperCase().substring(0, 1);
            mInitialsTextView.setText(initial);

            mInitialsBackground.setColor(colors[position % colors.length]);
        }

        @Override
        public boolean onLongClick(View v) {
            if (mActionCallbacks != null) {
                mActionCallbacks.onLongClickListener(contactList.get(getAdapterPosition()));
            }
            return true;
        }
    }

    void addActionCallback(ActionCallback actionCallbacks) {
        mActionCallbacks = actionCallbacks;
    }
}
