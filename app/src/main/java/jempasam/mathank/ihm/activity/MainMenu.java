package jempasam.mathank.ihm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jempasam.mathank.R;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=this;

        setContentView(R.layout.mainmenu);

        Button bBattle=(Button)findViewById(R.id.battle);
        Button bPuzzle=(Button)findViewById(R.id.puzzle);
        Button bHelp=(Button)findViewById(R.id.help);
        Button bSettings=(Button)findViewById(R.id.settings);

        bBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });

        bHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,HelpMenu.class);
                startActivity(intent);
            }
        });
    }
}
