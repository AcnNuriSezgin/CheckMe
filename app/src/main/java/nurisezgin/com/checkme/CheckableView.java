package nurisezgin.com.checkme;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by nuri on 07.08.2018
 */
public class CheckableView extends FrameLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private OnCheckedChangeListener mListener = new OnCheckedChangeListener.Empty();
    private boolean mChecked;
    private boolean useChildrenStates;

    public CheckableView(Context context) {
        super(context);
        init(null);
    }

    public CheckableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CheckableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            parseXmlAttributes(attrs);
        }

        applyAttributes();
        setOnClickListener((v) -> toggle());
    }

    private void parseXmlAttributes(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.CheckableView);
        mChecked = arr.getBoolean(R.styleable.CheckableView_isChecked, false);
        useChildrenStates = arr.getBoolean(R.styleable.CheckableView_useChildrenState, true);
        arr.recycle();
    }

    private void applyAttributes() {
        setChecked(mChecked);
        setAddStatesFromChildren(useChildrenStates);
    }

    @Override
    public boolean performClick() {
        toggle();

        final boolean handled = super.performClick();
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK);
        }

        return handled;
    }

    public void setCheckedChangeListener(OnCheckedChangeListener l) {
        if (l == null) {
            l = new OnCheckedChangeListener.Empty();
        }

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

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.isChecked = isChecked();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mChecked = ss.isChecked;
        setChecked(mChecked);
    }

    public static class SavedState extends BaseSavedState {

        boolean isChecked;

        public SavedState(Parcel source) {
            super(source);
            isChecked = source.readInt() == 1 ? true : false;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isChecked ? 1 : 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
    
}
