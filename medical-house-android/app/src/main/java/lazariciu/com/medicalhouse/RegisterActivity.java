package lazariciu.com.medicalhouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import lazariciu.com.medicalhouse.helper.HttpRequestMethod;
import lazariciu.com.medicalhouse.helper.RESTCaller;
import lazariciu.com.medicalhouse.helper.Urls;
import lazariciu.com.medicalhouse.service.SharedPreferencesService;

public class RegisterActivity extends AppCompatActivity {

    //UI references
    private EditText mEmail;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;
    private EditText mGender;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mAge = (EditText) findViewById(R.id.age);
        mGender = (EditText) findViewById(R.id.gender);
        mRegisterButton = (Button) findViewById(R.id.register_button);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String age = mAge.getText().toString();
        String gender = mGender.getText().toString();

        UserRegisterTask registerTask = new UserRegisterTask(email,password,firstName,lastName,age,gender);
        registerTask.execute();
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String age;
        private String gender;

        public UserRegisterTask(String email,String password, String firstName, String lastName, String age, String gender) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RESTCaller restCall = new RESTCaller();
            restCall.addParam("email", email);
            restCall.addParam("password", password);
            restCall.addParam("firstName", firstName);
            restCall.addParam("lastName", lastName);
            restCall.addParam("age", age);
            restCall.addParam("gender", gender);
            JSONObject response = restCall.executeCall(Urls.HOST + "/user/register", HttpRequestMethod.POST);

            if(response != null) {
                try {
                    if (HttpURLConnection.HTTP_OK == Integer.valueOf(response.get("responseCode").toString())) {
                        JSONObject user = (JSONObject) response.get("user");
                        String loggedUser = user.get("lastName").toString();
                        SharedPreferencesService.saveLoggedUser(getApplicationContext(), loggedUser);
                        return true;
                    }
                } catch (JSONException e) {
                    Log.e("LoginActivity", "Error at parsing JSON!");
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Intent startMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startMainActivity);
                finish();
            }
        }
    }
}
