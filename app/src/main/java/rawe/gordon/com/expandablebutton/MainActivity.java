package rawe.gordon.com.expandablebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ExpandableButton[] buttons;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findVisualElements();
        lastClickTime = System.currentTimeMillis();
        startConnections();
    }

    private void findVisualElements() {
        buttons = new ExpandableButton[]{
                (ExpandableButton) findViewById(R.id.red), (ExpandableButton) findViewById(R.id.green), (ExpandableButton) findViewById(R.id.blue)
        };
    }

    private void startConnections() {
        for (int i = 0; i < buttons.length; i++) {
            final ExpandableButton button = buttons[i];
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, button.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
