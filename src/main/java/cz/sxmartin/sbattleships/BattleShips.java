package cz.sxmartin.sbattleships;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

import cz.sxmartin.sbattleships.engine.Game;
import cz.sxmartin.sbattleships.engine.Grid;
import cz.sxmartin.sbattleships.engine.Ship;
import cz.sxmartin.sbattleships.engine.log.ExceptionLog;
import cz.sxmartin.sbattleships.engine.log.MessageLog;

public class BattleShips extends AppCompatActivity {
    public static AppCompatActivity BATTLESHIPS_ACTIVITY;
    public static Game GAME;

    final View.OnTouchListener gridPointListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //ignore touch in own gridMap
            if(!((PointView) view).getPoint().getGrid().getPlayer().isHuman() && GAME.getCurrentPlayer().isHuman())
            {
                ((PointView) view).getPoint().processFire();
                ((PointView) view).setOnTouchListener(null);
                GAME.turn();
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BATTLESHIPS_ACTIVITY = this;
        setContentView(R.layout.activity_battle_ships);

        //zoom
        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layout);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(BATTLESHIPS_ACTIVITY);
                return false;
            }
        });


        try{
             GAME = new Game();


            _generateGridMap(GAME.getPlayer(true).getGrid(), findViewById(R.id.playerGridMap));
            _generateGridMap(GAME.getPlayer(false).getGrid(), findViewById(R.id.botGridMap));

            _generateShips(GAME.getPlayer(true).getShips(), findViewById(R.id.playerRemainingShipsText));
            _generateShips(GAME.getPlayer(false).getShips(), findViewById(R.id.botRemainingShipsText));


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
            tr.setBackgroundColor(Color.parseColor("#000000")); //for zoom

            for (int c = 1; c <= grid.getColumns(); c++){
                PointView td = new PointView(this, grid.getPointXY(r,c));
                td.setOnTouchListener(gridPointListner);

                //add Point
                tr.addView(td);
            }

            gridMap.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        }

    }

    private void _generateShips(List<Ship> ships, LinearLayout textPlaceholder){
        for (Ship ship : ships){
            ShipTextView tw = new ShipTextView(this, ship);



            textPlaceholder.addView(tw);
        }
    }
}
