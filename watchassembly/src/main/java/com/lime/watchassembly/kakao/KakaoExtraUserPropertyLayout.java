package com.lime.watchassembly.kakao;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.lime.watchassembly.R;

import java.util.HashMap;
import java.util.Map;

public class KakaoExtraUserPropertyLayout extends FrameLayout {
    // property key
    private  static final String NAME_KEY = "name";
    private  static final String AGE_KEY = "age";
    private  static final String GENDER_KEY = "gender";

    private EditText name;
    private EditText age;
    private Spinner gender;

    public KakaoExtraUserPropertyLayout(Context context) {
        super(context);
    }

    public KakaoExtraUserPropertyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KakaoExtraUserPropertyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow () {
        super.onAttachedToWindow();
        final View view = inflate(getContext(), R.layout.kakao_extra_user_property, this);
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        gender = (Spinner) view.findViewById(R.id.gender);
    }

    HashMap<String, String> getProperties(){
        final String nickNameValue = name.getText().toString();
        final String ageValue = age.getText().toString();
        final String genderValue = String.valueOf(gender.getSelectedItem());

        HashMap<String, String> properties = new HashMap<String, String>();
        if(nickNameValue != null)
            properties.put(NAME_KEY, nickNameValue);
        if(ageValue != null)
            properties.put(AGE_KEY, ageValue);
        if(genderValue != null)
            properties.put(GENDER_KEY, genderValue);

        return properties;
    }

    void showProperties(final Map<String, String> properties) {
        final String nameValue = properties.get(NAME_KEY);
        if (nameValue != null)
            name.setText(nameValue);

        final String ageValue = properties.get(AGE_KEY);
        if (ageValue != null)
            age.setText(ageValue);

        final String genderValue = properties.get(GENDER_KEY);
        if (genderValue != null) {
            ArrayAdapter<String> myAdap = (ArrayAdapter<String>) gender.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(genderValue);
            gender.setSelection(spinnerPosition);
        }
    }


}
