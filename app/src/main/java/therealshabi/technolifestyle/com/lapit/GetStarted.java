package therealshabi.technolifestyle.com.lapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {

    Button mSignIn, mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        mSignIn = (Button) findViewById(R.id.GetStartedActivitySignIn);
        mRegister = (Button) findViewById(R.id.GetStartedActivityRegister);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetStarted.this,SignInActivity.class));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetStarted.this,RegisterActivity.class));
            }
        });
    }
}
