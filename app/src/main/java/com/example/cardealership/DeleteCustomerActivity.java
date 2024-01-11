package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class DeleteCustomerActivity extends AppCompatActivity implements HeaderCommunicator {
    ImageView backgroundImg;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this, User.dbName, null,1);

    ArrayList<User> userArrayList;
    ListView userListView;
    static DeleteCustomerCustomAdapter deleteCustomerCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_customer);

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        userArrayList = new ArrayList<User>();

        userListView = (ListView) findViewById(R.id.listViewCustomers);

        Cursor temp = dataBaseHelper.getNonAdmins();

        while(temp.moveToNext()){
            userArrayList.add(new User(
                    temp.getString(0),
                    temp.getString(1),
                    temp.getString(2),
                    temp.getString(3),
                    temp.getString(4),
                    "none",
                    temp.getString(5),
                    temp.getString(7)

            ));
        }


        deleteCustomerCustomAdapter = new DeleteCustomerCustomAdapter(userArrayList, DeleteCustomerActivity.this) ;

        userListView.setAdapter(deleteCustomerCustomAdapter);

    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("DELETE CUSTOMERS");

    }

    public static void calltoDeleteCustomer(Context context, String email){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, User.dbName, null,1);
        dataBaseHelper.deleteUser(email);
        deleteCustomerCustomAdapter.notifyDataSetChanged();
    }

}