package therealshabi.technolifestyle.com.lapit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import static android.R.attr.data;
import static android.R.id.message;

public class SignInActivity extends AppCompatActivity {

    Toolbar toolBar;
    Button mButton;
    EditText mEmail,mPassword;

    DatabaseReference mRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toolBar = (Toolbar) findViewById(R.id.SignInActivityToolbar);
        mButton = (Button) findViewById(R.id.SignInActivitySignInButton);
        mPassword = (EditText) findViewById(R.id.SignInActivityPassword);
        mEmail = (EditText) findViewById(R.id.SignInActivityEmail);
        setSupportActionBar(toolBar);

        mRoot = FirebaseDatabase.getInstance().getReference().child("Users");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_action_name));
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mRoot.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> i = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> itr = i.iterator();
                        while(itr.hasNext()) {
                            DataSnapshot data = itr.next();
                            String user = data.getKey();
                            Iterable<DataSnapshot> i1 = data.getChildren();
                            Iterator<DataSnapshot> itr1 = i1.iterator();
                            while (itr1.hasNext()) {
                                //String name = itr1.next().getValue().toString();
                                if ((itr1.next()).getValue().toString().equals(email)) {
                                    if (itr1.next().getValue().toString().equals(password)) {
                                        String uri = itr1.next().getValue().toString();//"https://firebasestorage.googleapis.com/v0/b/lapit-a8d0e.appspot.com/o/Users%2Fshahbaz.h96%40gmail.com?alt=media&token=179a9bb8-c168-426d-b63d-0f24308131b3";
                                        Toast.makeText(getBaseContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        intent.putExtra("URL", uri);
                                        intent.putExtra("Name",user);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                supportFinishAfterTransition();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
