package ggikko.me.testing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ggikko.me.bottomsheet.BasicBottomSheetDialogFragment;
import me.ggikko.BottomSheet;

public class MainActivity extends AppCompatActivity {

    @BottomSheet(title = "hh", content = "haha")
    BasicBottomSheetDialogFragment greetingBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetingBottomSheet.show();
    }
}
