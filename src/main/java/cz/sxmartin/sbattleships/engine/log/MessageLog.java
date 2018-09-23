package cz.sxmartin.sbattleships.engine.log;


import android.util.Log;

public class MessageLog {
    public MessageLog(String text) {

        //System.out.print(text);


        int i = 0;
        for(StackTraceElement stack : Thread.currentThread().getStackTrace()){

            if(stack.getClassName().equals("cz.sxmartin.sbattleships.engine.log.MessageLog")){
                Log.d("_Log:",text +" >> " + Thread.currentThread().getStackTrace()[i+1].toString());
                break;
            }
            i++;
        }
    }

}
