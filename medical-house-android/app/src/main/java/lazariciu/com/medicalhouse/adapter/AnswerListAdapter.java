package lazariciu.com.medicalhouse.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lazariciu.com.medicalhouse.R;
import lazariciu.com.medicalhouse.domain.Answer;
import lazariciu.com.medicalhouse.domain.Question;
import lazariciu.com.medicalhouse.service.TestGenerationService;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.AnswerListHolder> {
    private List<Answer> mAnswers;
    private Context mContext;
    private Question question;

    public AnswerListAdapter(List<Answer> answerList, Context ctx, Question question) {
        this.mAnswers = answerList;
        this.mContext = ctx;
        this.question = question;
    }

    @Override
    public AnswerListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);

        return new AnswerListHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnswerListHolder holder, int position) {
        final Answer answer = mAnswers.get(position);
        holder.answerTitle.setText(answer.getText());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TestGenerationService.givenAnswers.get(question) == null) {
                    holder.cardView.setCardBackgroundColor(Color.GREEN);
                    TestGenerationService.givenAnswers.put(question, answer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    public class AnswerListHolder extends RecyclerView.ViewHolder {
        private TextView answerTitle;
        private CardView cardView;

        public AnswerListHolder(View itemView) {
            super(itemView);
            answerTitle = (TextView) itemView.findViewById(R.id.answerTitle);
            cardView = (CardView) itemView.findViewById(R.id.answer_card);
        }
    }
}
