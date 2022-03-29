package jempasam.mathank.ihm.activity;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jempasam.mathank.R;
import jempasam.swj.data.chunk.DataChunk;
import jempasam.swj.data.chunk.ObjectChunk;
import jempasam.swj.data.chunk.ValueChunk;
import jempasam.swj.data.deserializer.DataDeserializers;
import jempasam.swj.logger.SLogger;
import jempasam.swj.logger.SimpleSLogger;

public class HelpMenu extends AppCompatActivity {

    SLogger logger=new SimpleSLogger(System.out);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpmenu);

        LinearLayout list=(LinearLayout)findViewById(R.id.helplist);

        try{
            // Read Help File
            StringBuilder sb=new StringBuilder();
            BufferedReader reader=new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.help)));
            String line;
            while((line=reader.readLine())!=null)sb.append(line);

            // Read JSON
            JSONArray array=new JSONArray(sb.toString());

            // Display
            for(int i=0; i<array.length(); i++){
                JSONObject o=array.getJSONObject(i);
                View helpelement= LayoutInflater.from(this).inflate(R.layout.helpelement,null);
                ((TextView)helpelement.findViewById(R.id.title)).setText(o.getString("title"));
                ((TextView)helpelement.findViewById(R.id.desc)).setText(o.getString("desc"));
                list.addView(helpelement);
            }

        }catch(IOException| JSONException e){

        }
    }
}
