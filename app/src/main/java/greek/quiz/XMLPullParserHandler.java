package greek.quiz;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {
    ArrayList<QuestionObject> questionsBank = new ArrayList<>();
    private QuestionObject q;
    private String text;

    public XMLPullParserHandler() {
        questionsBank = new ArrayList<>();
    }

    public List<QuestionObject> getQuestions() {
        return questionsBank;
    }

    public ArrayList<QuestionObject> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("qt")) {
                            q = new QuestionObject("", "", "", "", "", 0);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("qt")) {
                            questionsBank.add(q);
                        } else if (tagname.equalsIgnoreCase("movie")) {
                            if(text.length()>0) {
                                q.setQuestionMovieTitle(text);
                            }
                        } else if (tagname.equalsIgnoreCase("title")) {
                            if(text.length()>0) {q.setQuestionTitle(text);}
                        } else if (tagname.equalsIgnoreCase("first")) {
                            if(text.length()>0) {q.setQuestionAnswerA(text);}
                        } else if (tagname.equalsIgnoreCase("second")) {
                            if(text.length()>0) {q.setQuestionAnswerB(text);}
                        } else if (tagname.equalsIgnoreCase("third")) {
                            if(text.length()>0) {q.setQuestionAnswerC(text);}
                        } else if (tagname.equalsIgnoreCase("correct")) {
                            if(text.length()>0) {q.setQuestionCorrectAnswer(Integer.parseInt(text));}
                            break;
                        }
                            break;
                         default:
                            break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionsBank;
    }
}