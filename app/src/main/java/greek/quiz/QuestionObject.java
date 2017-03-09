package greek.quiz;
class QuestionObject{
    private String strMovie = "";
    private String strQuestion = "";
    private String strAnswerA = "";
    private String strAnswerB = "";
    private String strAnswerC = "";
    private int intCorrectAnswer = 0;

    public QuestionObject(String Movie, String Title , String First, String Second, String Third, int Correct) {
        strQuestion = Title;
        strMovie = Movie;
        strAnswerA = First;
        strAnswerB = Second;
        strAnswerC = Third;
        intCorrectAnswer = Correct;
    }

    public void setQuestionMovieTitle(String MovieTitle) {
        this.strMovie = MovieTitle;
    }
    public void setQuestionTitle(String QuestionText) {
        this.strQuestion = QuestionText;
    }
    public void setQuestionAnswerA(String FirstAnswer) {
        this.strAnswerA = FirstAnswer;
    }
    public void setQuestionAnswerB(String SecondAnswer) {
        this.strAnswerB = SecondAnswer;
    }
    public void setQuestionAnswerC(String ThirdAnswer) {
        this.strAnswerC = ThirdAnswer;
    }
    public void setQuestionCorrectAnswer(int CorrectAnswer) { this.intCorrectAnswer = CorrectAnswer;}

    public String returnMovie(){
        return  this.strMovie;
    }
    public String returnQuestion(){
        return  this.strQuestion;
    }
    public String returnFirst(){
        return  this.strAnswerA;
    }
    public String returnSecond(){
        return  this.strAnswerB;
    }
    public String returnThird(){
        return  this.strAnswerC;
    }
    public int checkCorrectAnswer(){
        return  this.intCorrectAnswer;
    }
}
