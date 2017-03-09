package greek.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class StartActivity extends Activity {
    private Button buttonPlayGame;
    private ArrayList<QuestionObject> questionsBank = new ArrayList<>();
    static final ArrayList<QuestionObject> levelQuestions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        buttonPlayGame = (Button) findViewById(R.id.buttonPlayGame);
        Initialise_TextSize();
        LoadXmlFile();
        GrabLevelQuestions();
        buttonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              GrabLevelQuestions();
              Intent i = new Intent(StartActivity.this, Main.class);
              startActivity(i);
            }
        });
    }

    private void LoadXmlFile(){
        try {
            XMLPullParserHandler parser = new XMLPullParserHandler();
            questionsBank = parser.parse(getAssets().open("data.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GrabLevelQuestions(){
        levelQuestions.clear();
        int questionsLeft = 6;
        Random randomGenerator = new Random();
        do {
            int randomInt = randomGenerator.nextInt(questionsBank.size());
            QuestionObject q = questionsBank.get(randomInt);
            if (!levelQuestions.contains(q)) {
                levelQuestions.add(q);
                questionsLeft--;
            }
        } while (questionsLeft > 0);
    }

//        questionsListBank.add(new QuestionObject(65,"Δεσποινίς διευθυντής","Εδώ παιδάκι μου δεν είναι εταιρεία. Είναι αντρολίβαδο!","Λιλλή Παπαγιάνη","...","...",1));
//        questionsListBank.add(new QuestionObject(66,"Ένα βότσαλο στη λίμνη","Να που το κάψαμε το φως. Στον γάμο του Καραγκιόζη το κάψαμε το φως","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(67,"Αλλίμονο στους νέους","Εγώ να ζηλέψω εσένα; Μα εγώ είμαι νέος! Ζηλεύει η άνοιξη τη βαρυχειμωνιά;","Δ.Χόρν","...","...",1));
//        questionsListBank.add(new QuestionObject(71,"Η γυναίκα μου τρελλάθηκε","Θέλω να φάω μικρές πράσινες μολόχες","","","Λ. Κωσταντάρας",1));
//        questionsListBank.add(new QuestionObject(74,"Ο Μικές παντρεύεται","Σας χαιρέτησα δεν σας χαιρέτησα. Χαίρεται τι κάνετε? Καλά ευχαριστώ","","","",1));
//        questionsListBank.add(new QuestionObject(76,"Ο στριγγλος που εγινε αρνάκι","Ναι κόρναρε,κόρναρε μας και φύγουν οι κατσαρίδες","Νίκος Κυριακίδης","Σταύρος Ξενίδης","Λάμπρος Κωσταντάρας",3));
//        questionsListBank.add(new QuestionObject(76,"Ο στριγγλος που εγινε αρνάκι","Ναι κόρναρε,κόρναρε μας και φύγουν οι κατσαρίδες","Νίκος Κυριακίδης","Σταύρος Ξενίδης","Λάμπρος Κωσταντάρας",3));
//        questionsListBank.add(new QuestionObject(99,"Γαμπρός απ το λονδίνο","Οξω ρε, θα μας φάνε τα σκυλιά ρε","","","",1));

//        questionsListBank.add(new QuestionObject(69,"Η θεια μου η χίππισα","ερωτηση 7","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(71,"Η κόρη μου η σοσιαλίστρια","ερωτηση 11","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(72,"Ο ατσίδας","ερωτηση 13","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(73,"Η Ρένα είναι οφσαιντ","ερωτηση 14","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(74,"Ο άνθρωπος που έσπαγε πλάκα","ερωτηση 15","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(75,"Η παριζιάνα","ερωτηση 16","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(76,"Η νεράιδα και το παλικάρι","ερωτηση 17","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(78,"Ο κύριος πτέραρχος","ερωτηση 20","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(80,"Ο ψευτοθόδωρος","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(83,"Ενας ήρως με παντούφλες","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(85,"Μια τρελή, τρελή οικογένοια","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(86,"Η κρεβατομουρμούρα","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(88,"Ούτε γάτα, ούτε ζημιά","ερωτηση 20","a1","a2","a3",1));
//        questionsListBank.add(new QuestionObject(89,"Δεσποινίς ετών 39","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(90,"Ο ζηλιαρόγατος","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(91,"Η καφετζού","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(92,"Το κοροϊδάκι της δεσποινίδος","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(93,"Υπάρχει και φιλότιμο","Θα σας εξαφανίσωμεν","","","",0));
////        questionsListBank.add(new QuestionObject(94,"Η ωραία του κουρέα","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(95,"Γοργόνες και μάγκες","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(98,"Ο τρελοπενηντάρης","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(45,"Ο φαντασμένος","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(47,"Ο φίλος μου ο Λευτεράκης","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(49,"Ο καζανόβας","ερωτηση 20","Χατζηχρήστος","","",0));
//        questionsListBank.add(new QuestionObject(51,"Ο ρωμιός έχει φιλότιμο","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(57,"Για ποιόν χτυπά η κουδούνα","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(58,"Ο πεθερόπληκτος","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(59,"Το στραβόξυλο","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(60,"Οι απάχηδες των Αθηνών","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(61,"Η κάλπικη λίρα","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(62,"Ποιός Θανάσης;","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(63,"Ο εξυπνάκιας","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(66,"Καλωσήρθε το δολάριο","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(68,"Ενας ήρως με παντούφλες","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(70,"Τρελός παλαβός και Βέγγος","Ξέρεις απο βέσπα?","","","",0));
//        questionsListBank.add(new QuestionObject(71,"Μια τρελή, τρελή οικογένοια","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(72,"Η κρεβατομουρμούρα","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(75,"Ο δασκαλάκος ήταν λεβεντιά","ερωτηση 20","","","",0));
//        questionsListBank.add(new QuestionObject(76,"Ο ψευτοθόδωρος","ερωτηση 20","","","",0));
   // }

    private void Initialise_TextSize(){
        buttonPlayGame.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font_size));
    }

}
