package lazariciu.com.medicalhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lazariciu.com.medicalhouse.R;
import lazariciu.com.medicalhouse.TestActivity;
import lazariciu.com.medicalhouse.domain.Test;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.TestListHolder> {
    private List<Test> mTests;
    private Context mContext;

    public TestListAdapter(List<Test> test, Context ctx) {
        this.mTests = test;
        this.mContext = ctx;
    }

    @Override
    public TestListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);

        return new TestListHolder(view);
    }

    @Override
    public void onBindViewHolder(TestListHolder holder, final int position) {
        final Test test = mTests.get(position);
        holder.testTitle.setText(test.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startTestIntent = new Intent(mContext, TestActivity.class);
                startTestIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startTestIntent.putExtra("test", test);
                mContext.startActivity(startTestIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    public class TestListHolder extends RecyclerView.ViewHolder {
        private TextView testTitle;
        private CardView cardView;

        public TestListHolder(View itemView) {
            super(itemView);
            testTitle = (TextView) itemView.findViewById(R.id.testTitle);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
