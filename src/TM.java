import java.lang.String;
/**
 * Created by chengangbao on 2016/12/3.
 */
public class TM {
    int beltLength=100;
    //private String inputString;  //input string such as aa bb cc dddddddd (ixjxk=l)
    private int State=0;    // state such as q1 q2 q3....
    private char[] paperBelt=new char[beltLength];
    //paper belt ,length is 1000, begin at 100, so the length of input string is at most 900
    private String outputPattern;  // uqv
    private String outputState;      // reject or accept
    private int pointLoc=10;
    public void setState(int state){
        this.State=state;
    }
    public int getState(){
        return this.State;
    }
    public void setPaperBelt(char[] paperBelt){
        //System.out.println(paperBelt);
        this.paperBelt=paperBelt;
        //System.out.println(this.paperBelt);
    }
    public char[] getPaperBelt(){
        return this.paperBelt;
    }
    public void setOutputPattern(String outputPattern){
        this.outputPattern=outputPattern;
    }
    public String getOutputPattern(){
        return this.outputPattern;
    }
    public void setOutputState(String outputState){
        this.outputState=outputState;
    }
    public String getOutputState(){
        return this.outputState;
    }
    public void setPointLoc(int pointLoc){
        this.pointLoc=pointLoc;
    }
    public int getPointLoc(){
        return this.pointLoc;
    }

}
