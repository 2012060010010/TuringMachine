/**
 * Created by chengangbao on 2016/12/3.
 */
public class Process {
    static int beltBegin=10;
    /*
    * initPaperBelt
    * to init the paperbelt of turig machine
    * TM tm  is turing machine object;
    * String inputString  the input string will be decided.
    * return void , only set TM;
   */
    public void initTM(TM tm,String inputString){
        char[] paperBelt=new char[tm.beltLength];
        for(int i=0;i<paperBelt.length;i++){
            paperBelt[i]='0';
        }
        if(inputString.length()==0)
            System.out.println("Please re-input a valid String,length at least 1!!");
        else {
            for(int i=0;i<inputString.length();i++){
            paperBelt[i+beltBegin]=inputString.toCharArray()[i];
            }
        }
        tm.setPaperBelt(paperBelt);
        String outputPattern="q"+tm.getState()+inputString;
        tm.setOutputPattern(outputPattern);

//        tm.setPointLoc(beltBegin);
//        setPattern(tm,tm.getPointLoc());
    }
    /*
    * ValidInput to judge the input string is valid or not;
    * input TM
    * output if valid, true; else false;
    */
    public boolean ValidInput(TM tm){
        char[] paperBelt=tm.getPaperBelt();
        int chartype=0;
        int i=beltBegin;
        while(i<tm.beltLength){

            char ch=paperBelt[i];
            //System.out.println(ch);
            if(ch!='a'&&ch!='b'&&ch!='c'&&ch!='d'&&ch!='0'){
              tm.setOutputState("reject");
                return false;
            }
            if(ch=='a' && chartype==0){chartype=1;}
            if(ch=='b' && chartype==1){chartype=2;}
            if(ch=='c' && chartype==2){chartype=3;}
            if(ch=='d' && chartype==3){chartype=4;}
            i++;

        }
        if(chartype==4)
            return true;
        else{
            tm.setOutputState("reject");
            return false;
        }
    }
    /*
* find the position of first char c;
* input TM char c
* output position;
*/
    public int find_Char(TM tm,char c){
        char[] paperBelt=tm.getPaperBelt();
        int position=0;
        //System.out.println("find char");
        for(int i=beltBegin;i<tm.beltLength;i++){
           // System.out.println(paperBelt[i]);
            if(c==paperBelt[i]){
                position=i;
                break;
            }
            else{position=-1;}
        }
        return position;
    }
    /*
  * eliminate to judge the input string is valid or not;
  * input TM
  * output if valid, true; else false;
  */
    public void eliminate_CD(TM tm) {
        //System.out.println("CD");
        char[] paperBelt = tm.getPaperBelt();
        if(tm.getOutputState()!="reject") {// if TM is not halt
            while (find_Char(tm, 'c') != -1) { //c is not eliminated, loop
                int position_c = find_Char(tm, 'c');
                int position_d = find_Char(tm, 'd');

                tm.setPointLoc(position_c+1);
                setPattern(tm,tm.getPointLoc());
                tm.setPointLoc(position_d+1);
                setPattern(tm,tm.getPointLoc());

                if (position_c != -1 && position_d != -1) {
                    paperBelt[position_c] = '$';
                    paperBelt[position_d] = '%';
                }
                if (position_c != -1 && position_d == -1) {
                    //d is already be eliminated, but c is still can  be found,reject
                    tm.setOutputState("reject");
                    break;
                }
            }
        }
        tm.setPaperBelt(paperBelt);
    }

    /*
* recovery character ;
* input TM char
* renew paper belt of TM;
*/
    public void recovery_char(TM tm,char c){  //recovery all c that be eliminated
        //System.out.println("recovery");
        char[] paperBelt=tm.getPaperBelt();
        if(c=='c') {  //recovery all c
            while (find_Char(tm, '$') != -1) {
                int position = find_Char(tm, '$');
                paperBelt[position] = 'c';
            }
        }else{  //recovery all b
            while (find_Char(tm, '#') != -1) {
                int position= find_Char(tm, '#');
                paperBelt[position] = 'b';
            }
            while (find_Char(tm, '$') != -1) {
                int position= find_Char(tm, '$');
                paperBelt[position] = 'c';
            }
        }
        tm.setPaperBelt(paperBelt);
    }
    /*
* eliminate to judge the input string is valid or not;
* input TM
* output if valid, true; else false;
*/
    public void eliminate_B(TM tm){  // every time eliminate a b, must eliminate all pair CD ;
            char[] paperBelt = tm.getPaperBelt();
            //System.out.println("B");
            while (find_Char(tm, 'b') != -1 && tm.getOutputState()!="reject") {
                tm.setPointLoc(find_Char(tm,'b'));
                setPattern(tm,tm.getPointLoc());

                paperBelt[find_Char(tm,'b')]='#';
                recovery_char(tm, 'c');
                eliminate_CD(tm);

            }
        tm.setPaperBelt(paperBelt);
    }
    /*
* eliminate to judge the input string is valid or not;
* input TM
* output if valid, true; else false;
*/
    public void eliminate_A(TM tm){
        char[] paperBelt=tm.getPaperBelt();
        //System.out.println("A");
        while (find_Char(tm,'a')!=-1 && tm.getOutputState()!="reject") {
            tm.setPointLoc(find_Char(tm,'a'));
            setPattern(tm,tm.getPointLoc());
            paperBelt[find_Char(tm,'a')]='@';
            recovery_char(tm,'b');
            eliminate_B(tm);
        }
        tm.setPaperBelt(paperBelt);
    }
    /*
* eliminate to judge the input string is valid or not;
* input TM
* output if valid, true; else false;
*/
    public String Halt(TM tm){
        char[] paperBelt=tm.getPaperBelt();
        //System.out.println("Halt");
        if(find_Char(tm,'a')==-1&&find_Char(tm,'b')==-1&&find_Char(tm,'c')==-1&&find_Char(tm,'d')==-1){//when a and d is eliminated at the same time
            tm.setOutputState("accept");
        }
        return tm.getOutputState();
    }
    /*
* eliminate to judge the input string is valid or not;
* input TM
* output if valid, true; else false;
*/
    public static void setPattern(TM tm,int pointloc){
        char[] paperBelt=tm.getPaperBelt();
        String part1="",part2="",pattern="";
        for(int i=beltBegin;i<pointloc;i++){
            part1=part1+String.valueOf(paperBelt[i]);
        }
        for(int i=pointloc;i<tm.beltLength;i++){
            if(paperBelt[i]=='0'){break;}
            part2=part2+String.valueOf(paperBelt[i]);
        }
        //System.out.println("11");
        pattern=part1+"q"+part2;
        //System.out.println("22");
        tm.setOutputPattern(pattern);
        System.out.println(tm.getOutputPattern());
    }
    public static void main(String[] args) {
        String input="aabbccdddddddd";
        TM tm=new TM();
        Process pr=new Process();
        pr.initTM(tm,input);
        boolean va=pr.ValidInput(tm);
        pr.eliminate_A(tm);
        String state=pr.Halt(tm);
        System.out.println(state);
    }
}
