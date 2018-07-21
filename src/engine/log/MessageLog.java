package engine.log;

import com.google.gson.Gson;

public class MessageLog {
    public MessageLog(String text) {

        System.out.print(text);


/*        int i = 0;
        for(StackTraceElement stack : Thread.currentThread().getStackTrace()){

            if(stack.getClassName().equals(".engine.log.MessageLog")){
                Log.d("sBattleship",text +" >> " + Thread.currentThread().getStackTrace()[i+1].toString());
                break;
            }
            i++;
        }*/
    }

}
