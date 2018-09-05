package cz.sxmartin.sbattleships.engine.log;

public class ExceptionLog {
    public ExceptionLog(Exception exeption) {

        exeption.printStackTrace();

/*        int y = 0;
        for(StackTraceElement stack : Thread.currentThread().getStackTrace()){
            if(stack.getClassName().equals("com.cardgame.cardgame.engine.log.ExceptionLog")){
                Log.d("CardGame",exeption.getMessage()+ " >>ERROR " + Thread.currentThread().getStackTrace()[y+1].toString());
                break;
            }
            y++;
        }

        int i = 0;
        for(StackTraceElement stack : exeption.getStackTrace()){
            Log.d("CardGame",stack.toString());
            if (i++ > 1){break;}
        }
        Log.d("CardGame","-------------------------");*/
    }
}
