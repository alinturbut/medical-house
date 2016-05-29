package lazariciu.com.medicalhouse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazariciu.com.medicalhouse.domain.Answer;
import lazariciu.com.medicalhouse.domain.Question;
import lazariciu.com.medicalhouse.domain.Test;

public class TestGenerationService {
    public static Map<Question, Answer> givenAnswers = new HashMap<>();

    public List<Test> generateTests() {
        List<Test> generatedTests = new ArrayList<>();
        //generate first test
        Answer answer1 = generateAnswer("left leg", false);
        Answer answer2 = generateAnswer("right leg", true);
        Answer answer3 = generateAnswer("right hand", false);

        Question question1 = generateQuestion("Which is the longest member of a human?", Arrays.asList(answer1, answer2, answer3), 20);
        Question question2 = generateQuestion("Which is the hairiest member of a human?", Arrays.asList(answer1, answer2, answer3), 50);

        Test test1 = generateTest("About human members", Arrays.asList(question1, question2));

        generatedTests.add(test1);
        return generatedTests;
    }

    public Answer generateAnswer(String text, boolean isCorrect) {
        Answer answer = new Answer();
        answer.setText(text);
        answer.setCorrect(isCorrect);

        return answer;
    }

    public Question generateQuestion(String question, List<Answer> answers, int score) {
        Question question1 = new Question();
        question1.setText(question);
        question1.setAnswers(answers);
        question1.setScore(score);

        return question1;
    }

    public Test generateTest(String title, List<Question> questions){
        Test test = new Test();
        test.setName(title);
        test.setQuestions(questions);

        return test;
    }
}
