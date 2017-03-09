package greek.quiz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.TextView;

public class IntroActivity extends Activity {
    TextView textView2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.big_font_size));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, StartActivity.class);
                startActivity(intent);
            }
        }, 2000);

   }
}
