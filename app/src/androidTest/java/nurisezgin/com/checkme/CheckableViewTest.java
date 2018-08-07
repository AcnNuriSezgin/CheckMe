package nurisezgin.com.checkme;

import android.content.Context;
import android.os.Parcel;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 07.08.2018
 */
@RunWith(AndroidJUnit4.class)
public class CheckableViewTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule
                    = new ActivityTestRule<>(TestActivity.class);

    private Context ctx;

    @Before
    public void setUp_() {
        ctx = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void should_ToggleCorrect() {
        final boolean expected = true;

        CheckableView view = new CheckableView(ctx);
        view.toggle();

        boolean actual = view.isChecked();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void should_IsCheckedTrueCorrect() {
        CheckableView view = new CheckableView(ctx);
        view.setChecked(true);

        boolean actual = view.isChecked();
        assertThat(actual).isTrue();
    }

    @Test
    public void should_IsCheckedFalseCorrect() {
        CheckableView view = new CheckableView(ctx);
        view.setChecked(false);

        boolean actual = view.isChecked();
        assertThat(actual).isFalse();
    }

    @Test
    public void should_ObserveListenerCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);

        CheckableView view = new CheckableView(ctx);
        view.setCheckedChangeListener(mockListener);

        view.setChecked(true);

        verify(mockListener).onCheckedChanged(any(), eq(true));
    }

    @Test
    public void should_NeverObserveWhenStateNotChangeCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);

        CheckableView view = new CheckableView(ctx);
        view.setChecked(true);

        view.setCheckedChangeListener(mockListener);
        view.setChecked(true);

        verify(mockListener, never()).onCheckedChanged(any(), eq(true));
    }

    @Test
    public void should_SaveStateCorrect() {
        final boolean expected = true;
        CheckableView view = new CheckableView(ctx);
        view.setChecked(true);

        CheckableView.SavedState state = (CheckableView.SavedState) view.onSaveInstanceState();
        boolean actual = state.isChecked;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void should_RestoreStateCorrect() {
        Parcel parcel = Parcel.obtain();
        CheckableView.SavedState state = new CheckableView.SavedState(parcel);
        state.isChecked = true;

        CheckableView view = new CheckableView(ctx);
        view.onRestoreInstanceState(state);

        assertThat(view.isChecked()).isTrue();
    }

}