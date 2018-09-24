package cz.sxmartin.sbattleships;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import cz.sxmartin.sbattleships.engine.Game;
import cz.sxmartin.sbattleships.engine.Grid;
import cz.sxmartin.sbattleships.engine.Point;
import cz.sxmartin.sbattleships.engine.PointType;
import cz.sxmartin.sbattleships.engine.Ship;
import cz.sxmartin.sbattleships.engine.exception.GameException;
import cz.sxmartin.sbattleships.engine.log.ExceptionLog;

public class BattleShips extends AppCompatActivity {
    public static AppCompatActivity BATTLESHIPS_ACTIVITY;
    public static Game GAME;
    public static Context BATTLESHIPS_CONTEXT;

    private List<Updateable> updateables = new ArrayList<>();

    private Ship placeShip;
    private ArrayDeque<Point> shipPoints = new ArrayDeque<>();
    private GestureDetector gdPlayerGrid;


    final View.OnTouchListener gridPointListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Point point = ((PointView) view).getPoint();

            //ignore touch in own gridMap
            if(!point.getGrid().getPlayer().isHuman() && GAME.getCurrentPlayer().isHuman() && GAME.hasStarted())
            {
                ((PointView) view).getPoint().processFire();
                ((PointView) view).setOnTouchListener(null);
                GAME.turn();
                updateViews();
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_UP && placeShip != null && shipPoints.size() > 0){
                _placeShipEvaluate();
            }


            return true;
        }
    };


    final View.OnTouchListener shipTextListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //not started so let's place own ships
            if(!GAME.hasStarted())
            {
                placeShip = ((ShipTextView) view).getShip();
                shipPoints = new ArrayDeque<>();
            }
            updateViews();
            return false;
        }
    };

    final GestureDetector.OnGestureListener onScrollPlayerGridListener = new GestureDetector.OnGestureListener() {
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

            //ignoce after start or ignore when no ship selected
            if (GAME.hasStarted() || placeShip == null){
                return false;
            }

            final PointView collisionWith = (PointView) this.getCollisionWith(motionEvent1);

            if (collisionWith != null){
                _placeShipOnScroll(collisionWith);
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        /** Returns the View colliding with the TouchEvent. */
        private final View getCollisionWith(final MotionEvent pMotionEvent) {

            try {
                final int[] lLocationBuffer = new int[2];

                //check Player gridmap only
                for (Point point : GAME.getPlayer(true).getGrid().getGridMap()) {
                    final View lView = point.getView();
                    // Fetch the View's location.
                    lView.getLocationOnScreen(lLocationBuffer);

                    if(pMotionEvent.getRawX() > lLocationBuffer[0] && pMotionEvent.getRawX() < lLocationBuffer[0] + lView.getWidth() && pMotionEvent.getRawY() > lLocationBuffer[1] && pMotionEvent.getRawY() < lLocationBuffer[1] + lView.getHeight()) {
                        // Return the colliding View.
                        return lView;
                    }
                }
            } catch (GameException e) {
                new ExceptionLog(e);
            }
            // no collision
            return null;
        }
    };

    final View.OnTouchListener btRestart = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                GAME = new Game();


                _generateGridMap(GAME.getPlayer(true).getGrid(), findViewById(R.id.playerGridMap));
                gdPlayerGrid = new GestureDetector(BATTLESHIPS_CONTEXT, onScrollPlayerGridListener);

                _generateGridMap(GAME.getPlayer(false).getGrid(), findViewById(R.id.botGridMap));

                _generateShips(GAME.getPlayer(true).getShips(), findViewById(R.id.playerRemainingShipsText));
                _generateShips(GAME.getPlayer(false).getShips(), findViewById(R.id.botRemainingShipsText));

                updateViews();
                ((Button)  findViewById(R.id.btStart)).setActivated(false);

                Toast.makeText(BATTLESHIPS_CONTEXT, "Game restarted! Place ships into your grid by tapping the ship type.", Toast.LENGTH_LONG).show();
            } catch (GameException e) {
                new ExceptionLog(e);
            }
            return false;
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return gdPlayerGrid.onTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_battle_ships);
        BATTLESHIPS_ACTIVITY = this;
        BATTLESHIPS_CONTEXT = getApplicationContext();
        ((Button) findViewById(R.id.btRestart)).setOnTouchListener(btRestart);

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
            gdPlayerGrid = new GestureDetector(BATTLESHIPS_CONTEXT, this.onScrollPlayerGridListener);

            _generateGridMap(GAME.getPlayer(false).getGrid(), findViewById(R.id.botGridMap));

            _generateShips(GAME.getPlayer(true).getShips(), findViewById(R.id.playerRemainingShipsText));
            _generateShips(GAME.getPlayer(false).getShips(), findViewById(R.id.botRemainingShipsText));

            updateViews();
            Toast.makeText(BATTLESHIPS_CONTEXT, "Place ships into your grid by tapping the ship type.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            new ExceptionLog(e);
        }
    }

    private void _generateGridMap(Grid grid, TableLayout gridMap){
        gridMap.removeAllViews();


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,0);

        for (int r = 1; r <= grid.getRows(); r++){
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tr.setBackgroundColor(Color.parseColor("#000000")); //for zoom

            for (int c = 1; c <= grid.getColumns(); c++){
                PointView td = new PointView(this, grid.getPointXY(r,c));
                td.setOnTouchListener(gridPointListener);


                //add Point
                tr.addView(td);
            }

            gridMap.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        }
        updateables.add(grid);

    }

    private void _generateShips(List<Ship> ships, LinearLayout textPlaceholder){
        textPlaceholder.removeAllViews();

        for (Ship ship : ships){
            ShipTextView tw = new ShipTextView(this, ship);
            tw.setOnTouchListener(shipTextListener);
            updateables.add(tw);


            textPlaceholder.addView(tw);
        }
    }

    private void _placeShipOnScroll(PointView pointView){
        final Point point = pointView.getPoint();

        //point not use
        if (point.isEmpty()){
            if (shipPoints.size() == 0){
                pointView.setImageResource(R.mipmap.ship);
                shipPoints.add(point);
            }
            else{
                int rowF = Math.abs(shipPoints.getFirst().getRow() - point.getRow());
                int columnF = Math.abs(shipPoints.getFirst().getColumn() - point.getColumn());

                int rowL = Math.abs(shipPoints.getLast().getRow() - point.getRow());
                int columnL = Math.abs(shipPoints.getLast().getColumn() - point.getColumn());

                //move by 1
                if ((rowF == 0 || columnF == 0) && ( (rowL == 0 && columnL == 1) ||  (rowL == 1 && columnL == 0) )){
                    pointView.setImageResource(R.mipmap.ship);
                    pointView.invalidate();
                    shipPoints.add(point);
                }

            }
        }
    }

    private void _placeShipEvaluate(){
        if (placeShip.getShipType().getSize() != shipPoints.size()){
            while (shipPoints.size() > 0){
                shipPoints.removeFirst().setStatus(PointType.FOG);
            }
        }
        else{
            try {
                if (GAME.getPlayer(true).getGrid().placeShip(placeShip,shipPoints.getFirst(),shipPoints.getLast())){
                    Toast.makeText(BATTLESHIPS_CONTEXT, placeShip.getShipType().getName() + " was assigned to grid!", Toast.LENGTH_SHORT).show();

                    placeShip = null;
                    shipPoints.clear();
                    checkAndActivateStartButton();
                }
                else
                {
                    while (shipPoints.size() > 0){
                        shipPoints.removeFirst().setStatus(PointType.FOG);
                    }
                }
            } catch (GameException e) {
                new ExceptionLog(e);
            }
        }
        updateViews();
    }

    private boolean _allShipsInGrid(){

        try {
            for (Ship ship :
                    GAME.getPlayer(true).getShips()) {
                if (ship.getPoints() == null) {
                    return false;
                }
            }
        } catch (GameException e) {
            new ExceptionLog(e);
        }

        return true;
    }

    public void checkAndActivateStartButton(){
        if (_allShipsInGrid()){
            Button btstart = findViewById(R.id.btStart);
            btstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GAME.beginGame();
                    btstart.setActivated(false);
                }
            });
            btstart.setActivated(true);
        }
    }

    public void updateViews(){
        for (Updateable view :
                updateables) {
            view.updateView();
        }
    }


}
