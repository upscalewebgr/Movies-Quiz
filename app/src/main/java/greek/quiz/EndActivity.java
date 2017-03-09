package greek.quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class EndActivity extends Activity {
    private InterstitialAd interstitialFullScreen;
    private Button buttonNextQuiz;
    private TextView textViewScore;
    private TextView textViewMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(greek.quiz.R.layout.activity_end);

        interstitialFullScreen = new InterstitialAd(this);
        interstitialFullScreen.setAdUnitId("ca-app-pub-3619467272714143/7988623710");
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        buttonNextQuiz = (Button) findViewById((R.id.buttonNextQuiz));

        final MediaPlayer soundTryAgain = MediaPlayer.create(this, greek.quiz.R.raw.sound1);
        final MediaPlayer soundGood = MediaPlayer.create(this, greek.quiz.R.raw.sound2);
        final MediaPlayer soundVeryGood = MediaPlayer.create(this, greek.quiz.R.raw.sound3);
        final MediaPlayer soundPerfect = MediaPlayer.create(this, greek.quiz.R.raw.sound4);

        Bundle c = this.getIntent().getExtras();
        int myScore = c.getInt("game_score");
        Initialise_TextSize();

        requestInterstitial();
        String message;

        if (myScore >= 0 && myScore <= 20) {
            message = "Είσαι άσχετος απο ελληνικές κωμωδίες...";
            textViewMessage.setText(String.valueOf(message));
            textViewScore.setText(myScore + "%");
            soundTryAgain.start();
        } else if (myScore > 20 && myScore <= 50) {
            message = "Είσαι καλός, ομως θέλεις δουλειά ακόμα";
            textViewMessage.setText(String.valueOf(message));
            textViewScore.setText(myScore + "%");
            soundGood.start();
        } else if (myScore > 50 && myScore < 80) {
            message = "Μπράβο. Είσαι πολύ καλός στις ελληνικές κωμωδίες";
            textViewMessage.setText(String.valueOf(message));
            textViewScore.setText(myScore + "%");
            soundVeryGood.start();
        } else if (myScore >= 80) {
            message = "Συγχαρητήρια. Εσύ είσαι παντογνώστης!";
            textViewScore.setText(myScore + "%");
            textViewMessage.setText(String.valueOf(message));
            soundPerfect.start();
        }

        buttonNextQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialFullScreen.isLoaded()) {
                    interstitialFullScreen.show();}
                else{
                    GoToStartActivity();
                }
            }
        });

        interstitialFullScreen.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestInterstitial();
                GoToStartActivity();
            }
            public void onAdLoaded(){
            }

            public void onAdOpened() {
            }

            public void onAdFailedToLoad(int errorCode){

            }
        });
    }

    private void requestInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
       // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialFullScreen.loadAd(adRequest);
    }

    private void GoToStartActivity(){
        requestInterstitial();
        if (interstitialFullScreen.isLoaded()) {
            interstitialFullScreen.show();
        }
        Intent intent = new Intent(EndActivity.this, StartActivity.class);
        startActivity(intent);
    }
    private void Initialise_TextSize(){
        buttonNextQuiz.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
        textViewScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.big_font_size));
        textViewMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
    }

}
