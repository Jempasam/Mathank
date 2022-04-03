package jempasam.mathank.ihm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jempasam.mathank.R;
import jempasam.mexpression.tree.MExpression;
import jempasam.mexpression.tree.builder.MExpressionBuilder;
import jempasam.mexpression.tree.builder.MExpressionTerm;
import jempasam.mexpression.tree.serializer.MExpressionSerializer;

public class EquationKeyboard extends Fragment {

    private List<String> keys;

    private  OnShoot onshoot;

    private LinearLayout keyboardZone;
    private LinearLayout keyboardZone2;
    private LinearLayout equationZone;
    public Button shoot;

    private List<View> equationviews;

    private Map<View,String> keyValues;

    private View root;

    public EquationKeyboard(List<String> keys){
        super();
        keyValues=new HashMap<>();
        equationviews=new ArrayList<>();
        this.keys=keys;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.keyboard,container,false);

        keyboardZone=root.findViewById(R.id.keyboardzone);
        keyboardZone2=root.findViewById(R.id.keyboardzone2);

        equationZone=root.findViewById(R.id.writingzone);
        shoot=root.findViewById(R.id.shoot);

        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onshoot!=null){
                    List<String> strs=new ArrayList<>();
                    for(int i=0; i<equationZone.getChildCount(); i++){
                        strs.add(keyValues.get(equationZone.getChildAt(i)));
                    }
                    MExpressionSerializer serializer=MExpressionSerializer.createBaseMExpressionSerializer();
                    try {
                        List<MExpressionTerm> terms=serializer.serialize(strs.iterator());
                        MExpressionBuilder builder=new MExpressionBuilder();
                        MExpression expression=builder.compile(terms);
                        onshoot.onShoot(expression);
                    } catch (Exception e) {
                        onshoot.onBadExpression();
                    }
                }
            }
        });
        for (String k : keys)addKey(k);

        return root;
    }

    public void addKey(String key){
        View keyview=getLayoutInflater().inflate(R.layout.keyboardkey,null);
        TextView text=keyview.findViewById(R.id.keytext);
        text.setText(key);
        keyValues.put(keyview,key);
        if(keyboardZone.getChildCount()>8)keyboardZone2.addView(keyview);
        else keyboardZone.addView(keyview);
        keyview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP)moveToEquation(keyview);
                return true;
            }
        });
        root.invalidate();
    }

    private void moveToEquation(View v){
        keyboardZone.removeView(v);
        keyboardZone2.removeView(v);
        equationZone.addView(v);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP)moveToKeyboard(v);
                return true;
            }
        });
    }

    private void moveToKeyboard(View v){
        equationZone.removeView(v);
        if(keyboardZone.getChildCount()>8)keyboardZone2.addView(v);
        else keyboardZone.addView(v);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP)moveToEquation(v);
                return true;
            }
        });
    }


    public void clear(){
        keyValues.clear();
        keyboardZone.removeAllViews();
        keyboardZone2.removeAllViews();
        equationZone.removeAllViews();
    }



    public void setOnShoot(OnShoot onshoot) {
        this.onshoot = onshoot;
    }

    public interface OnShoot{
        public void onShoot(MExpression mexpression);
        public void onBadExpression();
    }
}
