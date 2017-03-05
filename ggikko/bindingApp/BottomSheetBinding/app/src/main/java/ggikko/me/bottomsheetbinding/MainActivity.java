package ggikko.me.bottomsheetbinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ggikko.me.bottomsheet.BasicBottomSheetDialogFragment;
import ggikko.me.bottomsheet.GgikkoBottomSheetKnife;
import me.ggikko.BottomSheet;

public class MainActivity extends AppCompatActivity {

    @BottomSheet(title = "hh", content = "haha")
    BasicBottomSheetDialogFragment greetingBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GgikkoBottomSheetKnife.bind(this);
        findViewById(R.id.bottomSheetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greetingBottomSheet.show();
            }
        });
    }
}
