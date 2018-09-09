package cz.sxmartin.sbattleships.engine.log;

import android.util.Log;

public class ExceptionLog {
    public ExceptionLog(Exception exeption) {

        //exeption.printStackTrace();

        int y = 0;
        for(StackTraceElement stack : Thread.currentThread().getStackTrace()){
            if(stack.getClassName().equals("cz.sxmartin.sbattleships.engine.log.ExceptionLog")){
                Log.d("BattleShip ErrorLog:",exeption.getMessage()+ " >>ERROR " + Thread.currentThread().getStackTrace()[y+1].toString());
                break;
            }
            y++;
        }

        int i = 0;
        for(StackTraceElement stack : exeption.getStackTrace()){
            Log.d("BattleShip ErrorLog",stack.toString());
            if (i++ > 1){break;}
        }
        Log.d("BattleShip ErrorLog","-------------------------");
    }
}
