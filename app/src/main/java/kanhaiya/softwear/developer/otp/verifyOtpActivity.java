package kanhaiya.softwear.developer.otpconcept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyOtpActivity extends AppCompatActivity {
    EditText otp1,otp2,otp3,otp4,otp5,otp6;
    Button submitOtpBtn,resend;

    String getOtpBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        submitOtpBtn =findViewById(R.id.submitOtpBtn);
        resend = findViewById(R.id.resend);

        TextView phone = findViewById(R.id.phoneNum);
        phone.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        getOtpBackend = getIntent().getStringExtra("backendOtp");

        ProgressBar progressBar = findViewById(R.id.verifyProgress);

        submitOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!otp1.getText().toString().trim().isEmpty() && !otp2.getText().toString().trim().isEmpty() && !otp3.getText().toString().trim().isEmpty() && !otp4.getText().toString().trim().isEmpty() && !otp5.getText().toString().trim().isEmpty() && !otp6.getText().toString().trim().isEmpty()) {
                   // Toast.makeText(getApplicationContext(),"OTP is verified",Toast.LENGTH_SHORT).show();
                   String enteredOtp = otp1.getText().toString()
                           +otp2.getText().toString()
                           +otp3.getText().toString()
                           +otp4.getText().toString()
                           +otp5.getText().toString()
                           +otp6.getText().toString();
                   if (getOtpBackend != null) {
                       progressBar.setVisibility(View.VISIBLE);
                       submitOtpBtn.setVisibility(View.INVISIBLE);

                       PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                               getOtpBackend,enteredOtp);

                       FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       progressBar.setVisibility(View.GONE);
                                       submitOtpBtn.setVisibility(View.VISIBLE);

                                       if (task.isSuccessful()) {
                                           Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                           startActivity(intent);
                                       }else {
                                           Toast.makeText(getApplicationContext(),"Enter Correct Otp",Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });
                   }else {
                       Toast.makeText(getApplicationContext(),"Check internet connection",Toast.LENGTH_SHORT).show();
                   }

                }else {
                    Toast.makeText(getApplicationContext(),"Plz enter correct OTP",Toast.LENGTH_SHORT).show();

                }

            }
        });

        numberOtpMove();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60, TimeUnit.SECONDS,
                        verifyOtpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(getApplicationContext(), "error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newSentOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                //super.onCodeSent(sentOtp, forceResendingToken);
                                getOtpBackend = newSentOtp;
                                Toast.makeText(getApplicationContext(), "OTP resend successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });

    }

    private void numberOtpMove() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}