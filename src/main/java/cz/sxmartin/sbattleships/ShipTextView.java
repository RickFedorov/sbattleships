package cz.sxmartin.sbattleships;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.sxmartin.sbattleships.engine.Ship;

public class ShipTextView extends android.support.v7.widget.AppCompatTextView implements Updateable{
    protected final Ship ship;

    public ShipTextView (Context context, Ship ship){
        super(context);
        this.ship = ship;
        this.ship.setShipTextView(this);
        this.setText(this.ship.getShipType().getName() + "(" + this.ship.getShipType().getSize() + ")");
        this.setTextAppearance(R.style.TextAppearance_AppCompat_Body2);
        this.setTextColor((Color.parseColor("#FFFFFF")));
    }

    public void shipDestroyed(){
        ((ViewGroup)this.getParent()).removeView(this);
        invalidate();
    }

    public void updateView(){
        if (ship.getPoints() == null){
            this.setTextColor((Color.parseColor("#FFFFFF")));
        }
        else{
            if (ship.getPoints().length == ship.getShipType().getSize()){
                this.setTextColor((Color.parseColor("#00FF00")));
            }
            else{
                this.setTextColor((Color.parseColor("#FFFFFF")));
            }
        }

        invalidate();
    }

    public Ship getShip() {
        return ship;
    }
}
