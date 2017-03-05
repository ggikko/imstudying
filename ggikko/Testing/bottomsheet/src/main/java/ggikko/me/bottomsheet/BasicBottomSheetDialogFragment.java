package ggikko.me.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BasicBottomSheetDialogFragment extends BaseBottomSheetDialogFragment {

    TextView titleTextView;
    TextView contentTextView;
    public static String TAG = "basicBottomSheet";
    private Context context;

    private OnConfirmListener onConfirmListener;
    private String title, content;

    private Builder builder;

    public interface OnConfirmListener {
        void onConfirmButtonClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_confirm_bottom_sheet, container, false);

        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        contentTextView = (TextView) view.findViewById(R.id.contentTextView);

        setUpClickListener(view);
        setUpViews();
        return view;
    }

    private void setUpClickListener(View view) {
        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.confirmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirmButtonClick();
                }
                dismiss();
            }
        });
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
        this.title = builder.title;
        this.content = builder.content;
        this.onConfirmListener = builder.onConfirmListener;
        this.context = builder.context;
    }

    private void setUpViews() {
        if (Utils.isNotBlank(title)) {
            titleTextView.setText(title);
        }
        if (Utils.isNotBlank(content)) {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void show() {
        show(((FragmentActivity) context).getSupportFragmentManager(), TAG);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String content;
        private OnConfirmListener onConfirmListener;
        private boolean cancelable = true;
        private BasicBottomSheetDialogFragment basicBottomSheet;

        public BasicBottomSheetDialogFragment build() {
            basicBottomSheet = new BasicBottomSheetDialogFragment();
            basicBottomSheet.setCancelable(cancelable);
            basicBottomSheet.setBuilder(this);
            return basicBottomSheet;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder onPositiveListener(OnConfirmListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }
    }
}
