package arch.s.irc1java;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class LogsAdapter<String> extends ArrayAdapter<String> {

    public LogsAdapter(Context context, List<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_line, parent, false);
        }

        String item = getItem(position);

        // Find the view
        TextView line = (TextView) convertView.findViewById(R.id.text1);

        // Populate markup data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            line.setText(Html.fromHtml((java.lang.String) item, Html.FROM_HTML_MODE_COMPACT));
        else
            line.setText(Html.fromHtml((java.lang.String) item));

        // Return the completed view to render on screen
        return convertView;
    }

}
