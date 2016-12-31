package therealshabi.technolifestyle.com.lapit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView mProfilePic;
    Uri uri;
    TextView mDisplayText;
    Button mChatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfilePic = (CircleImageView) findViewById(R.id.profilePic);
        mDisplayText = (TextView) findViewById(R.id.displayName);
        String url = getIntent().getStringExtra("URL");
        String name = getIntent().getStringExtra("Name");
        SharedPreferences share = getSharedPreferences("Share_Chatterbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("URL", url);
        editor.putString("Name", name);
        editor.commit();
        editor.apply();
        mDisplayText.setText("Welcome, " + name);
        uri = Uri.parse(url);
        mChatter = (Button) findViewById(R.id.starting);
        Picasso.with(getBaseContext()).load(uri).into(mProfilePic);

        mChatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RoomActivity.class));
            }
        });

    }

   /* class ProfilePicLoader extends AsyncTask<Uri,Integer,Integer>{

        ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getBaseContext());
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(Uri... uris) {
            Picasso.with(getBaseContext()).load(uri).into(mProfilePic);
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mProgressDialog.dismiss();
        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
