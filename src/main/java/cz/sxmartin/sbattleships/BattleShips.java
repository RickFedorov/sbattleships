package cz.sxmartin.sbattleships;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import cz.sxmartin.sbattleships.engine.Game;
import cz.sxmartin.sbattleships.engine.Grid;
import cz.sxmartin.sbattleships.engine.log.ExceptionLog;
import cz.sxmartin.sbattleships.engine.log.MessageLog;

public class BattleShips extends AppCompatActivity {

    public static Game GAME;

    View.OnTouchListener gridPointListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            GAME.testTURN();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ships);



        try{
             GAME = new Game();

            TableLayout playerGridMap = (TableLayout) findViewById(R.id.playerGridMap);
            TableLayout botGridMap = (TableLayout) findViewById(R.id.botGridMap);

            _generateGridMap(GAME.getPlayer(true).getGrid(), playerGridMap);
            _generateGridMap(GAME.getPlayer(false).getGrid(), botGridMap);

            GAME.testTURN();
        }
        catch (Exception e){
            new ExceptionLog(e);
        }
    }

    private void _generateGridMap(Grid grid, TableLayout gridMap){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,0);

        for (int r = 1; r <= grid.getRows(); r++){
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tr.setBackgroundColor(Color.parseColor("#ffff00"));

            for (int c = 1; c <= grid.getColumns(); c++){
                PointView td = new PointView(this, grid.getPointXY(r,c));
                td.setOnTouchListener(gridPointListner);

                //add Point
                tr.addView(td);
            }

            gridMap.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        }

    }
}
