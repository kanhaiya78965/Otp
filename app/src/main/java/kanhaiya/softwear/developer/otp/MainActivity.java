package kanhaiya.softwear.developer.otpconcept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText enterNumber;
    Button getOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterNumber = findViewById(R.id.input_mobile_number);
        getOtp = findViewById(R.id.btnGetOtp);

        ProgressBar progressBar = findViewById(R.id.sendProgress);


        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!enterNumber.getText().toString().trim().isEmpty()) {

                    if ((enterNumber.getText().toString().trim().length()) == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        getOtp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enterNumber.getText().toString(),
                                60, TimeUnit.SECONDS,
                                MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtp.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), "error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String sentOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        //super.onCodeSent(sentOtp, forceResendingToken);
                                        progressBar.setVisibility(View.GONE);
                                        getOtp.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(getApplicationContext(), verifyOtpActivity.class);
                                        intent.putExtra("mobile", enterNumber.getText().toString());
                                        intent.putExtra("backendOtp",sentOtp);
                                        startActivity(intent);

                                    }
                                }
                        );

//                        Intent intent = new Intent(getApplicationContext(),verifyOtpActivity.class);
//                        intent.putExtra("mobile",enterNumber.getText().toString());
//                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "please enter correct number", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "please enter number", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}