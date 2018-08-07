package nurisezgin.com.checkme;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 07.08.2018
 */
@RunWith(AndroidJUnit4.class)
public class MultipleSelectionGroupTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule 
                    = new ActivityTestRule<>(TestActivity.class);
    
    private Context ctx;
    private MultipleSelectionGroup viewGroup;
    
    @Before
    public void setUp_() {
        ctx = InstrumentationRegistry.getTargetContext();
        viewGroup = new MultipleSelectionGroup(ctx);
    }

    @Test
    public void should_ObserveCheckedStateCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);
        CheckableView view = new CheckableView(ctx);
        viewGroup.addView(view);
        viewGroup.setOnCheckedChangeListener(mockListener);

        view.setChecked(true);

        verify(mockListener).onCheckedChanged(any(), anyBoolean());
    }

    @Test
    public void should_CheckedViewsSizeCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);
        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        viewGroup.setOnCheckedChangeListener(mockListener);

        view1.setChecked(true);
        view2.setChecked(true);

        List<View> checkedViews = viewGroup.getCheckedViews();
        assertThat(checkedViews).hasSize(2);
    }

    @Test
    public void should_ClearCorrect() {
        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        view1.setChecked(true);
        view2.setChecked(true);

        viewGroup.clearCheck();

        List<View> checkedViews = viewGroup.getCheckedViews();
        assertThat(checkedViews).hasSize(0);
    }

    @Test
    public void should_NeverObserveWithClearCorrect() {
        OnCheckedChangeListener mockListener = mock(OnCheckedChangeListener.class);
        CheckableView view1 = new CheckableView(ctx);
        CheckableView view2 = new CheckableView(ctx);
        viewGroup.addView(view1);
        viewGroup.addView(view2);

        view1.setChecked(true);
        view2.setChecked(true);

        viewGroup.setOnCheckedChangeListener(mockListener);
        viewGroup.clearCheck();

        verify(mockListener, never()).onCheckedChanged(any(), anyBoolean());
    }
    
}