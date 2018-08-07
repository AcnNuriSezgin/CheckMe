package nurisezgin.com.checkme;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by nuri on 07.08.2018
 */
public class SingleSelectionGroup extends LinearLayout {

    private OnCheckedChangeListener mChildCheckedChangeListener;
    private OnCheckedChangeListener mListener = new OnCheckedChangeListener.Empty();
    private boolean mPreventCheckedStateUpdate;
    private int mCheckedId;

    public SingleSelectionGroup(Context context) {
        super(context);
        init(null);
    }

    public SingleSelectionGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SingleSelectionGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        parseXmlAttributes(attrs);

        mChildCheckedChangeListener = new CheckedStateTracker();
        ChildAddListener listener = new ChildAddListener();
        setOnHierarchyChangeListener(listener);
        setOrientation(VERTICAL);
    }

    private void parseXmlAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.SingleSelectionGroup);
            mCheckedId = arr.getResourceId(R.styleable.SingleSelectionGroup_checkedId, View.NO_ID);
            arr.recycle();
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        mListener = l;
    }

    public boolean hasCheckedView() {
        return mCheckedId != View.NO_ID;
    }

    public View getCheckedView() {
        return findViewById(mCheckedId);
    }

    public void check(int id) {
        if (id != View.NO_ID && id == mCheckedId) {
            return;
        }

        if (mCheckedId != View.NO_ID) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != View.NO_ID) {
            setCheckedStateForView(id, true);
        }

        setCheckedId(findViewById(id));
    }

    public void clearCheck() {
        check(View.NO_ID);
    }

    private void setCheckedId(View checkedView) {
        if (checkedView == null) {
            mCheckedId = View.NO_ID;
            return;
        }

        mCheckedId = checkedView.getId();
        mListener.onCheckedChanged(checkedView, ((CheckableView) checkedView).isChecked());
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof CheckableView) {
            ((CheckableView) checkedView).setChecked(checked);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (mCheckedId != View.NO_ID) {
            mPreventCheckedStateUpdate = true;
            setCheckedStateForView(mCheckedId, true);
            mPreventCheckedStateUpdate = false;
            setCheckedId(findViewById(mCheckedId));
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof CheckableView) {
            CheckableView view = (CheckableView) child;
            if (view.isChecked()) {
                mPreventCheckedStateUpdate = true;
                if (mCheckedId != View.NO_ID) {
                    setCheckedStateForView(mCheckedId, false);
                }
                mPreventCheckedStateUpdate = false;
                setCheckedId(view);
            }
        }

        super.addView(child, index, params);
    }

    private class CheckedStateTracker implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(View view, boolean isChecked) {
            if (mPreventCheckedStateUpdate) {
                return;
            }

            mPreventCheckedStateUpdate = true;
            if (mCheckedId != View.NO_ID) {
                setCheckedStateForView(mCheckedId, false);
            }
            ((CheckableView) view).setChecked(true);
            mPreventCheckedStateUpdate = false;

            setCheckedId(view);
        }
    }

    private class ChildAddListener implements ViewGroup.OnHierarchyChangeListener {

        @Override
        public void onChildViewAdded(View parent, View child) {
            if (parent == SingleSelectionGroup.this && child instanceof CheckableView) {
                int id = child.getId();

                if (id == View.NO_ID) {
                    id = View.generateViewId();
                    child.setId(id);
                }

                ((CheckableView) child).setCheckedChangeListener(mChildCheckedChangeListener);
            }
        }

        @Override
        public void onChildViewRemoved(View parent, View child) {
            if (parent == SingleSelectionGroup.this && child instanceof CheckableView) {
                ((CheckableView) child).setCheckedChangeListener(
                        new OnCheckedChangeListener.Empty());
            }
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.mCheckedId = mCheckedId;
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

        mCheckedId = ss.mCheckedId;
        check(mCheckedId);
    }

    public static class SavedState extends BaseSavedState {

        int mCheckedId;

        public SavedState(Parcel source) {
            super(source);
            mCheckedId = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mCheckedId);
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
