package nurisezgin.com.checkme;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by nuri on 07.08.2018
 */
public class CheckableView extends FrameLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private OnCheckedChangeListener mListener = new OnCheckedChangeListener.Empty();
    private boolean mChecked;

    public CheckableView(Context context) {
        super(context);
    }

    public CheckableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        mListener = l;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            mListener.onCheckedChanged(this, mChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }

        return drawableState;
    }
}
