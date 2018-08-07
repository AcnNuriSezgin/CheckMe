package nurisezgin.com.checkme;

import android.view.View;

/**
 * Created by nuri on 07.08.2018
 */
public interface OnCheckedChangeListener {

    void onCheckedChanged(View view, boolean isChecked);

    class Empty implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(View view, boolean isChecked) { }
    }


}
