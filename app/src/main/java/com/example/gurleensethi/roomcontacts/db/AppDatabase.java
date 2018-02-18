package com.example.gurleensethi.roomcontacts.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.gurleensethi.roomcontacts.db.typeconverters.DateTypeConverter;
import com.example.gurleensethi.roomcontacts.models.Contact;

/**
 * Created by gurleensethi on 04/02/18.
 */
@Database(entities = {Contact.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
