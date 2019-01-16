package com.simplestepapp.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.simplestepapp.R;
import com.simplestepapp.activities.ViewPagerActivity;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.models.AnswerOptions;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.models.WhyOptions;
import com.simplestepapp.utils.MyGridView;

import java.util.ArrayList;

public class WaterInTakeFragment extends Fragment {

    MyGridView grid_view;
ScrollView scroll_View;
    ArrayList<String> timeSlots;
    ArrayList<AnswerOptions> answerOptions;
    ArrayList<WhyOptions> whyOptions;

    CustomAdapter customAdapter;

    LinearLayout lyt_list_Why, lyt_QtnOptns;

    AppCompatTextView txt_QtnHdng, txt_QtnCaptn, txt_QtnOptns, txt_Next;

    RadioGroup rG_WakeUp, rGrp_WhyOptions;

    RadioButton rBtn_WOne, rBtn_WTwo, rBtn_WThre, rBtn_WFur, rBtn_op1, rBtn_op2, rBtn_op3;

    String s_WITTime = "", s_WITQtnOption = "", s_WITWhyOptn="",colorName="";;

    int sPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_waterintake, container, false);
        initviews(v);
        timeSlots = new ArrayList<>();
        timeSlots.add("< 5:00");
        timeSlots.add("5:00");
        timeSlots.add("5:15");
        timeSlots.add("5:30");
        timeSlots.add("5:45");
        timeSlots.add("6:00");
        timeSlots.add("6:15");
        timeSlots.add("6:30");
        timeSlots.add("6:45");
        timeSlots.add("7:00");
        timeSlots.add("7:15");
        timeSlots.add("7:30");
        timeSlots.add("7:45");
        timeSlots.add("8:00");
        timeSlots.add("8:15");
        timeSlots.add("8:30");
        timeSlots.add("8:45");
        timeSlots.add("9:00");
        timeSlots.add("9:00 >");
        timeSlots.add("None");

        try {

            txt_QtnHdng.setText(ViewPagerActivity.questionerArrayList.get(3).getQuestion());
            txt_QtnCaptn.setText(ViewPagerActivity.questionerArrayList.get(3).getDescription());
            txt_QtnOptns.setText(ViewPagerActivity.questionerArrayList.get(3).getAnswerCaption());
            answerOptions = new ArrayList<>();
            answerOptions = ViewPagerActivity.questionerArrayList.get(3).getAnswerOptions();
            rBtn_WOne.setText(answerOptions.get(0).getDescription());
            rBtn_WTwo.setText(answerOptions.get(1).getDescription());
            rBtn_WThre.setText(answerOptions.get(2).getDescription());
            rBtn_WFur.setText(answerOptions.get(3).getDescription());
            whyOptions = new ArrayList<>();
            whyOptions = ViewPagerActivity.questionerArrayList.get(3).getWhyOptions();
            rBtn_op1.setText(whyOptions.get(0).getDescription());
            rBtn_op2.setText(whyOptions.get(1).getDescription());
            rBtn_op3.setText(whyOptions.get(2).getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }


        customAdapter = new CustomAdapter(getActivity().getApplicationContext(), timeSlots);
        grid_view.setAdapter(customAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customAdapter.setSelectedIndex(position);
                s_WITTime = (String) parent.getItemAtPosition(position);
                sPosition = position;
                lyt_QtnOptns.setVisibility(View.VISIBLE);
            }
        });

        rG_WakeUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                lyt_list_Why.setVisibility(View.VISIBLE);
                scroll_View.fullScroll(ScrollView.FOCUS_DOWN);
                switch (checkedId) {
                    case R.id.rBtn_WOne:
                        s_WITQtnOption = rBtn_WOne.getText().toString();
                        colorName="G";
                        break;
                    case R.id.rBtn_WTwo:
                        s_WITQtnOption = rBtn_WTwo.getText().toString();
                        colorName="B";
                        break;
                    case R.id.rBtn_WThre:
                        s_WITQtnOption = rBtn_WThre.getText().toString();
                        colorName="O";
                        break;
                    case R.id.rBtn_WFur:
                        s_WITQtnOption = rBtn_WFur.getText().toString();
                        colorName="R";
                        break;
                    default:
                        s_WITQtnOption = "";
                        break;
                }
            }
        });

        rGrp_WhyOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                txt_Next.setVisibility(View.VISIBLE);
                switch (checkedId){
                    case R.id.rBtn_op1:
                        s_WITWhyOptn=rBtn_op1.getText().toString();
                        break;
                    case R.id.rBtn_op2:
                        s_WITWhyOptn=rBtn_op2.getText().toString();
                        break;
                    case R.id.rBtn_op3:
                        s_WITWhyOptn=rBtn_op3.getText().toString();
                        break;
                    default:
                        s_WITWhyOptn="";
                        break;
                }
            }
        });

        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerActivity.pager.setCurrentItem(4);
                QAnswerModel qAnswerModel = new QAnswerModel();
                qAnswerModel.setTimeSlotOption(s_WITTime);
                qAnswerModel.setAnswerOption(s_WITQtnOption);
                qAnswerModel.setWhyOption(s_WITWhyOptn);
                qAnswerModel.setS_Position(sPosition);
                qAnswerModel.setQuestionId(ViewPagerActivity.questionerArrayList.get(3).get_id());
                ViewPagerActivity.qAnswerModelArrayList.add(qAnswerModel);


            }
        });

        return v;
    }


    private void initviews(View v) {
        txt_QtnHdng = v.findViewById(R.id.txt_QtnHdng);
        txt_QtnCaptn = v.findViewById(R.id.txt_QtnCaptn);
        txt_QtnOptns = v.findViewById(R.id.txt_QtnOptns);
        rG_WakeUp = v.findViewById(R.id.rG_WakeUp);
        rGrp_WhyOptions = v.findViewById(R.id.rGrp_WhyOptions);
        rBtn_WOne = v.findViewById(R.id.rBtn_WOne);
        rBtn_WTwo = v.findViewById(R.id.rBtn_WTwo);
        rBtn_WThre = v.findViewById(R.id.rBtn_WThre);
        rBtn_WFur = v.findViewById(R.id.rBtn_WFur);
        rBtn_op1 = v.findViewById(R.id.rBtn_op1);
        rBtn_op2 = v.findViewById(R.id.rBtn_op2);
        rBtn_op3 = v.findViewById(R.id.rBtn_op3);
        grid_view = v.findViewById(R.id.grid_view);
        lyt_list_Why = v.findViewById(R.id.lyt_list_Why);
        lyt_QtnOptns = v.findViewById(R.id.lyt_QtnOptns);
        txt_Next = v.findViewById(R.id.txt_Next);
        scroll_View = v.findViewById(R.id.scroll_View);
    }

    private static WaterInTakeFragment instance = null;


    public static WaterInTakeFragment newInstance(String text) {

        if (instance == null) {
            // new instance
            instance = new WaterInTakeFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {

            return instance;
        }

    }

}
