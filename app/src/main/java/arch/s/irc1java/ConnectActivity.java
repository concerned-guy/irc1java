package arch.s.irc1java;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ConnectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void connect(View v) {
        EditText host = findViewById(R.id.host);
        EditText port = findViewById(R.id.port);
        EditText nick = findViewById(R.id.nick);
        EditText password = findViewById(R.id.password);
        CheckBox auth_nickserv = findViewById(R.id.auth_nickserv);
        EditText autojoin = findViewById(R.id.autojoin);
        EditText script = findViewById(R.id.script);

        Intent intent = new Intent();
        intent.setClass(this, ConversationActivity.class);
        intent.putExtra("host", host.getText().toString());
        intent.putExtra("port", port.getText().toString());
        intent.putExtra("nick", nick.getText().toString());
        intent.putExtra("password", password.getText().toString());
        intent.putExtra("auth_nickserv", auth_nickserv.isChecked() ? "1" : "");
        intent.putExtra("autojoin", autojoin.getText().toString());
        intent.putExtra("script", script.getText().toString());

        startActivity(intent);
    }
}
