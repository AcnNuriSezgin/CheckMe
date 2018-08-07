package nurisezgin.com.checkme;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nuri on 07.08.2018
 */
public class MultipleSelectionGroup extends LinearLayout {

    private OnCheckedChangeListener mChildCheckedChangeListener;
    private OnCheckedChangeListener mListener = new OnCheckedChangeListener.Empty();
    private List<Integer> mCheckedIds = new ArrayList<>();
    private boolean mPreventCheckedStateUpdate;

    public MultipleSelectionGroup(Context context) {
        super(context);
        init(null);
    }

    public MultipleSelectionGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultipleSelectionGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mChildCheckedChangeListener = new CheckedStateTracker();
        setOnHierarchyChangeListener(new ChildAddRemoveListener());
        setOrientation(VERTICAL);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        if (l == null) {
            l = new OnCheckedChangeListener.Empty();
        }

        mListener = l;
    }

    public List<View> getCheckedViews() {
        List<View> views = new ArrayList<>();

        for (int id : mCheckedIds) {
            views.add(findViewById(id));
        }

        return views;
    }

    public void clearCheck() {
        mPreventCheckedStateUpdate = true;

        for (Iterator<Integer> it = mCheckedIds.iterator(); it.hasNext(); ) {
            CheckableView view = findViewById(it.next());
            view.setChecked(false);

            it.remove();
        }

        mPreventCheckedStateUpdate = false;
    }

    private class CheckedStateTracker implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(View view, boolean isChecked) {
            if (mPreventCheckedStateUpdate) {
                return;
            }

            if (isChecked) {
                mCheckedIds.add(view.getId());
            } else {
                if (mCheckedIds.size() > 0) {
                    mCheckedIds.remove((Integer) view.getId());
                }
            }

            mListener.onCheckedChanged(view, isChecked);
        }
    }

    private class ChildAddRemoveListener implements ViewGroup.OnHierarchyChangeListener {

        @Override
        public void onChildViewAdded(View parent, View child) {
            if (parent == MultipleSelectionGroup.this && child instanceof CheckableView) {
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
            if (parent == MultipleSelectionGroup.this && child instanceof CheckableView) {
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

        int[] arr = new int[mCheckedIds.size()];
        for (int i = 0; i < mCheckedIds.size(); ++i) {
            arr[i] = mCheckedIds.get(i);
        }
        ss.mCheckedIds = arr;

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

        for (int i = 0; i < ss.mCheckedIds.length; i++) {
            mCheckedIds.add(i);
        }
    }

    public static class SavedState extends BaseSavedState {

        int[] mCheckedIds;

        public SavedState(Parcel source) {
            super(source);
            int size = source.readInt();
            mCheckedIds = new int[size];
            source.readIntArray(mCheckedIds);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeIntArray(mCheckedIds);
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
