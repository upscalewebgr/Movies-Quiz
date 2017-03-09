package greek.quiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;
import java.util.Random;
import static greek.quiz.StartActivity.levelQuestions;

public class Main extends Activity {
    private QuestionObject q = new QuestionObject("", "", "", "", "", 0);
    private ArrayList<QuestionObject> LevelQuestionsFirst = new ArrayList<>();
    private Button buttonA, buttonB, buttonC, buttonUseHelp;
    private TextView textMovie, textQuestion, textQuestionsLeft;
    private boolean HelpUsed = false;
    private int intCorrectAnswers = 0;
    private int intSelAnswer = 1;
    private int HintsLeft = 1;
    private int iD = 0;
    static GoogleAnalytics analytics;
    static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonUseHelp = (Button) findViewById(R.id.buttonUseHelp);
        textMovie = (TextView) findViewById(R.id.textMovie);
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textQuestionsLeft = (TextView) findViewById(R.id.textQuestionsLeft);
        try{
            analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker("UA-84674756-1");
        }
        catch (Exception | Error e) {}

        try{
            Chartboost.startWithAppId(this, getResources().getString(R.string.ChartBoostID), getResources().getString(R.string.ChartBoostSign));
            Chartboost.onCreate(this);
            Chartboost.setDelegate(delegate);
        }
        catch (Exception | Error e) {}
        Initialise_TextSize();
        UpdateHelpsText();
        final MediaPlayer soundHint = MediaPlayer.create(this, R.raw.hint);
        LevelQuestionsFirst = levelQuestions;

        if (LevelQuestionsFirst.size() > 0) {
            q = LevelQuestionsFirst.get(iD);
            printQuestion();
        }
        buttonUseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!HelpUsed) {
                    if ((HintsLeft > 0)) {
                        SendAnalyticsAction(2);
                        Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);
                        soundHint.start();
                        Random rand = new Random();
                        int n = rand.nextInt(2) + 1;

                        switch (q.checkCorrectAnswer()) {
                            case 1:
                                if (n == 1) {
                                    DisableWrongAnswer(2);
                                } else {
                                    DisableWrongAnswer(3);
                                }
                                break;
                            case 2:
                                if (n == 1) {
                                    DisableWrongAnswer(1);
                                } else {
                                    DisableWrongAnswer(3);
                                }
                                break;
                            case 3:
                                if (n == 1) {
                                    DisableWrongAnswer(1);
                                } else {
                                    DisableWrongAnswer(2);
                                }
                                break;
                        }
                        HintsLeft--;
                        HelpUsed = true;
                        UpdateHelpsText();
                    } else if (HintsLeft == 0) {
                        if (hasInternet()) {
                            if (Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT)) {
                                Chartboost.showRewardedVideo(CBLocation.LOCATION_DEFAULT);
                            }else {
                                Toast.makeText(getApplicationContext(), "Προσπαθήστε ξανά αργότερα", Toast.LENGTH_SHORT).show();
                                Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);
                            }
                        } else if (!hasInternet()) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.textCheckInternet), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intSelAnswer = 1;
                if (q.checkCorrectAnswer() == intSelAnswer) {
                    intCorrectAnswers = intCorrectAnswers + 1;
                }
                if (iD < LevelQuestionsFirst.size()) {
                    q = LevelQuestionsFirst.get(iD);
                    printQuestion();
                } else {
                    MoveToEndActivity();
                }
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intSelAnswer = 2;
                if (q.checkCorrectAnswer() == intSelAnswer) {
                    intCorrectAnswers = intCorrectAnswers + 1;
                }
                if (iD < LevelQuestionsFirst.size()) {
                    q = LevelQuestionsFirst.get(iD);
                    printQuestion();
                } else {
                    MoveToEndActivity();
                }
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intSelAnswer = 3;
                if (q.checkCorrectAnswer() == intSelAnswer) {
                    intCorrectAnswers = intCorrectAnswers + 1;
                }
                if (iD < LevelQuestionsFirst.size()) {
                    q = LevelQuestionsFirst.get(iD);
                    printQuestion();
                } else {
                    MoveToEndActivity();
                }
            }
        });
    }

    private boolean hasInternet(){
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }


   private final ChartboostDelegate delegate = new ChartboostDelegate() {
        @Override
        public void didCompleteRewardedVideo(java.lang.String location, int reward) {
            HintsLeft = HintsLeft + 2;
            UpdateHelpsText();
            Toast.makeText(getApplicationContext(),"Κέρδισες 2 επιπλέον βοήθειες",Toast.LENGTH_SHORT).show();
            SendAnalyticsAction(3);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Chartboost.onStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Chartboost.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Chartboost.onPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Chartboost.onStop(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Chartboost.onDestroy(this);
    }

    @Override
    public void onBackPressed() {
        if (Chartboost.onBackPressed())
            return;
        else
            super.onBackPressed();
    }
    private void MoveToEndActivity() {
        SendAnalyticsAction(1);
        Intent intent = new Intent(Main.this, EndActivity.class);
        intent.putExtra("game_score", (intCorrectAnswers * 100) / LevelQuestionsFirst.size());
        startActivity(intent);
    }

    private void ResetAnswerButtonsStyle() {
        buttonA.setBackgroundResource(R.drawable.button_status_list_drawable);
        buttonA.setPaintFlags(0);
        buttonB.setBackgroundResource(R.drawable.button_status_list_drawable);
        buttonB.setPaintFlags(0);
        buttonC.setBackgroundResource(R.drawable.button_status_list_drawable);
        buttonC.setPaintFlags(0);
    }

    private void DisableWrongAnswer(int Answer) {
        switch (Answer) {
            case 1:
                buttonA.setBackgroundResource(R.drawable.nice_looking_deactivated_buttons);
                buttonA.setPaintFlags(buttonA.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 2:
                buttonB.setBackgroundResource(R.drawable.nice_looking_deactivated_buttons);
                buttonB.setPaintFlags(buttonB.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 3:
                buttonC.setBackgroundResource(R.drawable.nice_looking_deactivated_buttons);
                buttonC.setPaintFlags(buttonC.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
        }
    }

    private void UpdateHelpsText() {
        buttonUseHelp.setText(getResources().getString(R.string.buttonHintsString) + " (" + String.valueOf(HintsLeft)+")");
    }

    private void printQuestion() {
        ResetAnswerButtonsStyle();
        textQuestionsLeft.setText(getResources().getString(R.string.textCurrentQuestion) + " " + String.valueOf(LevelQuestionsFirst.indexOf(q) + 1) + "/" + LevelQuestionsFirst.size());
        textQuestion.setText(q.returnQuestion());
        textMovie.setText(q.returnMovie());
        buttonA.setText(q.returnFirst());
        buttonB.setText(q.returnSecond());
        buttonC.setText(q.returnThird());
        iD = iD + 1;
        HelpUsed = false;
        UpdateHelpsText();
    }
    private void Initialise_TextSize(){
        textQuestionsLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        textQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        textMovie.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        buttonA.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        buttonB.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        buttonC.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        buttonUseHelp.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
    }

    private void SendAnalyticsAction(int Action){
        switch (Action){
            case 1:
                tracker.send(new HitBuilders.EventBuilder().setCategory("QUIZ").setAction("completed").build());
                break;
            case 2:
                tracker.send(new HitBuilders.EventBuilder().setCategory("QUIZ").setAction("use_help").build());
                break;
            case 3:
                tracker.send(new HitBuilders.EventBuilder().setCategory("QUIZ").setAction("won_help").build());
                break;
        }
    }
}


