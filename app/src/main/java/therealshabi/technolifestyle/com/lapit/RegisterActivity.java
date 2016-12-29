package therealshabi.technolifestyle.com.lapit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    Toolbar toolbar;
    CircleImageView mCircleImageView;
    Button mRegister;
    DatabaseReference mRoot;
    StorageReference mStorage;
    EditText mDisplayName;
    EditText mPassword;
    EditText mEmail;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegister = (Button) findViewById(R.id.RegisterActivityGetStartedButton);
        toolbar = (Toolbar) findViewById(R.id.RegisterActivityToolbar);
        mCircleImageView = (CircleImageView) findViewById(R.id.RegisterActivityImageView);
        mStorage = FirebaseStorage.getInstance().getReference();
        mEmail = (EditText) findViewById(R.id.RegisterActivityEmailId);
        mPassword = (EditText) findViewById(R.id.RegisterActivityPassword);
        mDisplayName = (EditText) findViewById(R.id.RegisterActivityDisplayName);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
         mRoot = FirebaseDatabase.getInstance().getReference().child("Users");


        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mDisplayName.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();
                String id = mRoot.push().getKey();
                Map<String, Object> user = new HashMap<>();
                user.put("Display Name", username);
                user.put("Email", email);
                user.put("Password", password);
                DatabaseReference userChild = mRoot.child(id);
                userChild.updateChildren(user);
                //Upload Profile Pic
                uploadFile(email);
            }
        });
    }

    private void uploadFile(String email) {
        if (selectedImage != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Registering..");
            progressDialog.show();

            StorageReference userImage = mStorage.child("Users").child(email);
            userImage.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Profile Created Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this,SignInActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please choose a valid Image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            Picasso.with(getApplicationContext()).load(selectedImage).into(mCircleImageView);
        }
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
