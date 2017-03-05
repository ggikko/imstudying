package ggikko.me.bottomsheet;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private String tagForShow;

    protected Activity activity() {
        return getActivity();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded()) {
            return;
        }
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
        }
    }

    public void setTagForShow(String tagForShow) {
        this.tagForShow = tagForShow;
    }

    public void show(FragmentManager manager) {
        if (tagForShow != null) {
            show(manager, tagForShow);
        } else {
            show(manager, getClass().getCanonicalName());
        }
    }
}
