package cz.sxmartin.sbattleships;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.sxmartin.sbattleships.engine.Ship;

public class ShipTextView extends android.support.v7.widget.AppCompatTextView {
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

}
