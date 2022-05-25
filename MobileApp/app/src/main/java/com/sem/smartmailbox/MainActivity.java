package com.sem.smartmailbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sem.smartmailbox.App.CHANNEL_1_ID;
import static com.sem.smartmailbox.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Esp_32");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        myRef.addChildEventListener(new ChildEventListener() {


            int i = 2 ;
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                i ++ ;

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , CHANNEL_1_ID);
                mBuilder.setSmallIcon(R.drawable.ic_notification) ;// notification icon
                mBuilder.setContentTitle("Smart MailBox"); // title for notification
                mBuilder.setContentText("You Have New Mail !!");// message for notification
                mBuilder.setAutoCancel(true); // clear notification after click
                mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);



                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(i, mBuilder.build());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
