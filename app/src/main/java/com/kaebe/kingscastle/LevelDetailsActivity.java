package com.kaebe.kingscastle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kingscastle.gameUtils.Difficulty;
import com.kaebe.kingscastle.R;
import com.kingscastle.util.Strings;

public class LevelDetailsActivity extends Activity implements View.OnClickListener{

    @Nullable
    private String levelClassName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_details);

        SharedPreferences sp = getSharedPreferences(Strings.LevelDetails,MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            levelClassName = extras.getString("levelClassName");

            String completedOn = sp.getBoolean(levelClassName+"Easy",false) ? "Completed On: Easy," : "Completed On: ";
            completedOn += sp.getBoolean(levelClassName+"Medium",false) ? " Medium," : "";
            completedOn += sp.getBoolean(levelClassName+"Hard",false) ? " Hard" : "";
            if( completedOn.endsWith(",")) completedOn = completedOn.substring(0,completedOn.length()-1);
            if( completedOn.equals("Completed On: ")) completedOn += "None";

            long score = sp.getLong(levelClassName + "Score", 0);

            ((TextView) findViewById(R.id.textView_level_name)).setText(levelClassName);
            ((TextView) findViewById(R.id.textView_score)).setText("Best Score: "+score);
            ((TextView) findViewById(R.id.textView_CompletedOn)).setText(completedOn);
            findViewById(R.id.play_button).setOnClickListener(this);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent();

        Difficulty diff = Difficulty.Medium;

        if (((RadioButton) findViewById(R.id.radioButton_Easy)).isChecked())
            diff = Difficulty.Easy;

        if (((RadioButton) findViewById(R.id.radioButton_Medium)).isChecked())
            diff = Difficulty.Medium;

        if (((RadioButton) findViewById(R.id.radioButton_Hard)).isChecked())
            diff = Difficulty.Hard;

        i.putExtra(Strings.Difficulty, diff.name());
        i.putExtra(Strings.LevelClassName,levelClassName);
        setResult(RESULT_OK, i);
        //close this Activity...
        finish();
    }

}
