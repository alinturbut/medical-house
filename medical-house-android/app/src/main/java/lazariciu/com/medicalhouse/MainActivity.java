package lazariciu.com.medicalhouse;

import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import lazariciu.com.medicalhouse.adapter.TestListAdapter;
import lazariciu.com.medicalhouse.service.SharedPreferencesService;
import lazariciu.com.medicalhouse.service.TestGenerationService;

public class MainActivity extends AppCompatActivity {
    private TestGenerationService testGenerationService = new TestGenerationService();

    //UI references
    private TextView mWelcomeUser;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWelcomeUser = (TextView) findViewById(R.id.welcome_user);
        mRecyclerView = (RecyclerView) findViewById(R.id.testList);
        String loggedUser = SharedPreferencesService.getLoggedUser(getApplicationContext());
        mWelcomeUser.setText("Welcome, " + loggedUser);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        TestListAdapter adapter = new TestListAdapter(testGenerationService.generateTests(), getApplicationContext());
        mRecyclerView.setAdapter(adapter);
    }
}
