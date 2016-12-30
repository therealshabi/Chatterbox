package therealshabi.technolifestyle.com.lapit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView mProfilePic;
    Uri uri;
    TextView mDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfilePic = (CircleImageView) findViewById(R.id.profilePic);
        mDisplayText = (TextView) findViewById(R.id.displayName);
        String url = getIntent().getStringExtra("URL");
        String name = getIntent().getStringExtra("Name");
        mDisplayText.setText("Welcome, "+name);
        uri = Uri.parse(url);
        Picasso.with(getBaseContext()).load(uri).into(mProfilePic);

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

}
