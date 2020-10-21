package com.example.mathquizzer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_start,btn_answer0,btn_answer1,btn_answer2,btn_answer3,retry;
    TextView tv_score,tv_questions,tv_timer,tv_bottommessage,gameOver;
    ProgressBar prog_timer;
    ConstraintLayout layout;
    String m_Text = "";

    Game g = new Game();

    int secondsRemaining = 60;
    final CountDownTimer timer =new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining-=1;
            tv_timer.setText(Integer.toString(secondsRemaining) + "sec");
            prog_timer.setProgress(60 - secondsRemaining);
        }

        @Override
        public void onFinish() {
            btn_answer0.setEnabled(false);
            btn_answer0.setVisibility(View.INVISIBLE);
            btn_answer1.setEnabled(false);
            btn_answer1.setVisibility(View.INVISIBLE);
            btn_answer2.setEnabled(false);
            btn_answer2.setVisibility(View.INVISIBLE);
            btn_answer3.setEnabled(false);
            btn_answer3.setVisibility(View.INVISIBLE);
            gameOver.setVisibility(View.VISIBLE);
            prog_timer.setVisibility(View.INVISIBLE);
            tv_timer.setVisibility(View.INVISIBLE);
            tv_questions.setVisibility(View.INVISIBLE);
            tv_score.setVisibility(View.INVISIBLE);
            retry.setVisibility(View.VISIBLE);
            if(m_Text.matches(""))
                m_Text="Guest";
            gameOver.setText("Game Over\n\n"+m_Text+"\nYour Score\n"+tv_score.getText());
            tv_bottommessage.setText("Time is up " + (g.getNumberCorrect())+ "/"+ (g.getTotalQuestions() -1));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.lout);

        btn_start = findViewById(R.id.btn_start);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);
        gameOver = findViewById(R.id.gameOver);

        tv_score = findViewById(R.id.tv_score);
        tv_bottommessage = findViewById(R.id.tv_bottommessage);
        tv_questions = findViewById(R.id.tv_questions);
        tv_timer = findViewById(R.id.tv_timer);
        prog_timer = findViewById(R.id.prog_timer);
        retry=findViewById(R.id.retry);

        tv_timer.setText("0 sec");
        tv_questions.setText("");
        tv_bottommessage.setText("Click on \"START\"");
        tv_score.setText("0 pts");

        start();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }



    public void start()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name : ");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.show();

        View.OnClickListener startButtonClickListener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;
                start_button.setVisibility(View.INVISIBLE);
                nextTurn();
                timer.start();
            }
        };

        View.OnClickListener answerButtonClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;
                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        };

        btn_start.setOnClickListener(startButtonClickListener);
        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_button:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                if(tv_score.getText()!="0 pts")
                {
                    String shareBody = "Hello "+m_Text+"\nYour Score is "+tv_score.getText()+"\nDownload the App from Play Store and give 5 star rating to our app";
                    String shareSubject = "Math Quiz";
                    sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
                    startActivity(Intent.createChooser(sharingIntent,"Share Using"));
                }
                else
                {
                    String shareBody = "Download the App from Play Store and give 5 star rating to our app";
                    String shareSubject = "Math Quiz";
                    sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
                    startActivity(Intent.createChooser(sharingIntent,"Share Using"));
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nextTurn()
    {
        g.makeNewQuestion();
        int [] answer= g.getCurrentQuestion().getAnswerArray();
        btn_answer0.setText(Integer.toString(answer[0]));
        btn_answer1.setText(Integer.toString(answer[1]));
        btn_answer2.setText(Integer.toString(answer[2]));
        btn_answer3.setText(Integer.toString(answer[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);

        tv_questions.setText(g.getCurrentQuestion().getQuestionPhrase());

        tv_bottommessage.setText(g.getNumberCorrect()+"/"+(g.getTotalQuestions() -1));

    }
}

