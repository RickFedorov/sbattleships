package cz.sxmartin.sbattleships;

import android.content.Context;
import android.widget.TableRow;

import cz.sxmartin.sbattleships.engine.Point;


public class PointView extends android.support.v7.widget.AppCompatImageView {
    protected final Point point;

    public PointView(Context context, Point point) {
        super(context);
        this.point = point;
        this.point.setView(this);

        this.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        this.setImageResource(R.mipmap.fog);
        updatePointView();

    }

    public void updatePointView(){
        boolean ownGrid = this.point.getGrid().getPlayer().isHuman();
        int display = R.mipmap.fog;

        switch (this.point.getStatus()){
            case FOG: display = ownGrid == true ? (this.point.isEmpty() ? R.mipmap.fog : R.mipmap.ship) : R.mipmap.fog ;
                break;
            case HIT: display = ownGrid == true ? R.mipmap.ship_hit : R.mipmap.hit;
                break;
            case EMPTY: display = R.mipmap.empty;
                break;
        }
        this.setImageResource(display);
        invalidate();
    }

    public Point getPoint() {
        return point;
    }
}
