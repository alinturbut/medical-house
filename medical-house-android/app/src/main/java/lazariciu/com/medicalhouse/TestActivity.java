package lazariciu.com.medicalhouse;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

import lazariciu.com.medicalhouse.adapter.AnswerListAdapter;
import lazariciu.com.medicalhouse.domain.Answer;
import lazariciu.com.medicalhouse.domain.Question;
import lazariciu.com.medicalhouse.domain.Test;
import lazariciu.com.medicalhouse.helper.HttpRequestMethod;
import lazariciu.com.medicalhouse.helper.RESTCaller;
import lazariciu.com.medicalhouse.helper.Urls;
import lazariciu.com.medicalhouse.service.SharedPreferencesService;
import lazariciu.com.medicalhouse.service.TestGenerationService;

public class TestActivity extends AppCompatActivity {
    //UI references
    private TextView mQuestionTitle;
    private Button mBack;
    private Button mForward;
    private RecyclerView mRecyclerView;
    private int questionPosition = 0;

    private Test givenTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        givenTest = (Test) getIntent().getSerializableExtra("test");
        setContentView(R.layout.activity_test);
        mQuestionTitle = (TextView) findViewById(R.id.questionTitle);
        mBack = (Button) findViewById(R.id.questionBack);
        mForward = (Button) findViewById(R.id.questionForward);
        mRecyclerView = (RecyclerView) findViewById(R.id.answerList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionPosition > 0) {
                    renderQuestion(--questionPosition);
                }
            }
        });

        mForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionPosition < givenTest.getQuestions().size() - 1) {
                    renderQuestion(++questionPosition);
                } else if (questionPosition == givenTest.getQuestions().size() - 1) {
                    int score = 0;
                    for(Map.Entry<Question, Answer> entry : TestGenerationService.givenAnswers.entrySet()) {
                        if(entry.getValue().isCorrect()) {
                            score += entry.getKey().getScore();
                        }
                    }
                    mQuestionTitle.setText("The test has finished! You scored: " + score);
                    mRecyclerView.setAdapter(null);
                    mBack.setVisibility(View.INVISIBLE);
                    final int finalScore = score;
                    mForward.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TestGenerationService.givenAnswers.clear();
                            new SaveScoreTask(finalScore).execute();
                            finish();
                        }
                    });
                }
            }
        });

        renderQuestion(questionPosition);
    }

    public void renderQuestion(int position) {
        Question question = givenTest.getQuestions().get(position);
        mQuestionTitle.setText(question.getText());
        AnswerListAdapter answerListAdapter = new AnswerListAdapter(question.getAnswers(), getApplicationContext(), question);
        mRecyclerView.setAdapter(answerListAdapter);
    }

    public class SaveScoreTask extends AsyncTask<Void, Void, Boolean> {
        private int score = 0;

        public SaveScoreTask(int score) {
            this.score = score;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            RESTCaller restCaller = new RESTCaller();
            restCaller.addParam("email", SharedPreferencesService.getLoggedUserEmail(getApplicationContext()));
            restCaller.addParam("test", givenTest.getName());
            restCaller.addParam("score", String.valueOf(score));
            restCaller.executeCall(Urls.HOST + "/user/score", HttpRequestMethod.POST);

            return true;
        }
    }

}
