package nurisezgin.com.checkme;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 07.08.2018
 */
@RunWith(AndroidJUnit4.class)
public class SingleSelectionGroupTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule
                    = new ActivityTestRule<>(TestActivity.class);

    private Context ctx;
    private SingleSelectionGroup viewGroup;

    @Before
    public void setUp_() {
        ctx = InstrumentationRegistry.getTargetContext();
        viewGroup = new SingleSelectionGroup(ctx);
    }

    @Test
    public void should_CheckedViewCorrect() {
        CheckableView view = new CheckableView(ctx);
        viewGroup.addView(view);

        view.setChecked(true);
        View checkedView = viewGroup.getCheckedView();

        assertThat(view).isEqualTo(checkedView);
    }

    @Test
    public void should_SingleSelectionCorrect() {
        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        view1.setChecked(true);
        view2.setChecked(true);

        View checkedView = viewGroup.getCheckedView();

        assertThat(view2).isEqualTo(checkedView);
    }

    @Test
    public void should_ClearCorrect() {
        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        view1.setChecked(true);

        viewGroup.clearCheck();

        assertThat(viewGroup.hasCheckedView()).isFalse();
    }

    @Test
    public void should_ObserveSingleTimeCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);
        viewGroup.setOnCheckedChangeListener(mockListener);

        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        view1.setChecked(true);

        verify(mockListener).onCheckedChanged(eq(view1), eq(true));
    }

    @Test
    public void should_ObserveSingleTimeOnMultipleInteractionsCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);
        viewGroup.setOnCheckedChangeListener(mockListener);

        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        view1.setChecked(true);
        view2.setChecked(true);

        verify(mockListener).onCheckedChanged(eq(view2), eq(true));
    }

    @Test
    public void should_SaveStateCorrect() {
        final int expected = View.generateViewId();
        CheckableView view = new CheckableView(ctx);
        view.setChecked(true);
        view.setId(expected);

        viewGroup.addView(view);

        Parcelable parcelable = viewGroup.onSaveInstanceState();
        int actual = ((SingleSelectionGroup.SavedState) parcelable).mCheckedId;

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void should_RestoreStateCorrect() {
        Parcel parcel = Parcel.obtain();
        SingleSelectionGroup.SavedState state = new SingleSelectionGroup.SavedState(parcel);
        state.mCheckedId = View.generateViewId();

        viewGroup.onRestoreInstanceState(state);

        assertThat(viewGroup.hasCheckedView()).isTrue();
    }

}