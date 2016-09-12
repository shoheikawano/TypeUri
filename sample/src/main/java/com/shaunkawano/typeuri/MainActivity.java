package com.shaunkawano.typeuri;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.shaunkawano.TypeUri;
import java.net.URI;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  private static final String DEFAULT_INPUT = "https://github.com/shaunkawano/typeuri";

  private String input;
  private EditText editText;
  private Button okButton;
  private TextView resultText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editText = (EditText) findViewById(R.id.sample_edit_text);
    editText.setText(input = DEFAULT_INPUT);
    editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        input = charSequence.toString();
      }

      @Override public void afterTextChanged(Editable editable) {
      }
    });

    okButton = (Button) findViewById(R.id.button_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        updateSampleResult();
      }
    });

    resultText = (TextView) findViewById(R.id.sample_result);
  }

  private void updateSampleResult() {
    URI inputUri;
    try {
      inputUri = URI.create(input);
    } catch (IllegalArgumentException e) {
      resultText.setText(e.toString());
      return;
    }

    TypeUri typeUri = TypeUri.parse(inputUri);
    resultText.setText(String.format(Locale.JAPAN, "\n" +
            "input=%s\n" + "isOpaque=%s\n" + "isAbsolute=%s\n" + "queryMap=%s\n" + "hasQuery=%s\n"
            + "getQuery=%s\n" + "getRawQuery=%s\n" + "hasEmptyPath=%s\n" + "getPath=%s\n"
            + "getRawPath=%s\n" + "pathSegments=%s\n" + "getHost=%s\n" + "getRawSchemeSpecificPart=%s\n"
            + "getSchemeSpecificPart=%s\n",

        input, inputUri.isOpaque(), inputUri.isAbsolute(), typeUri.queryMap(), typeUri.hasQuery(),
        typeUri.URI().getQuery(), typeUri.URI().getRawQuery(), typeUri.hasEmptyPath(),
        typeUri.URI().getPath(), typeUri.URI().getRawPath(), typeUri.getPathSegments(),
        typeUri.URI().getHost(), typeUri.URI().getRawSchemeSpecificPart(),
        typeUri.URI().getSchemeSpecificPart()));
  }
}
