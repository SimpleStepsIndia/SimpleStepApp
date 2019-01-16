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

/**
 * Created by Srinivas on 12/20/2018.
 */

public class PhysicalFitFragment extends Fragment {

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

    String s_PFTTime = "", s_PFTQtnOption = "", s_PFTWhyOptn="",colorName="";;

    int sPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_pfitness, container, false);
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

            txt_QtnHdng.setText(ViewPagerActivity.questionerArrayList.get(4).getQuestion());
            txt_QtnCaptn.setText(ViewPagerActivity.questionerArrayList.get(4).getDescription());
            txt_QtnOptns.setText(ViewPagerActivity.questionerArrayList.get(4).getAnswerCaption());
            answerOptions = new ArrayList<>();
            answerOptions = ViewPagerActivity.questionerArrayList.get(4).getAnswerOptions();
            rBtn_WOne.setText(answerOptions.get(0).getDescription());
            rBtn_WTwo.setText(answerOptions.get(1).getDescription());
            rBtn_WThre.setText(answerOptions.get(2).getDescription());
            rBtn_WFur.setText(answerOptions.get(3).getDescription());
            whyOptions = new ArrayList<>();
            whyOptions = ViewPagerActivity.questionerArrayList.get(4).getWhyOptions();
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
                s_PFTTime = (String) parent.getItemAtPosition(position);
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
                        s_PFTQtnOption = rBtn_WOne.getText().toString();
                        colorName="G";
                        break;
                    case R.id.rBtn_WTwo:
                        s_PFTQtnOption = rBtn_WTwo.getText().toString();
                        colorName="B";
                        break;
                    case R.id.rBtn_WThre:
                        s_PFTQtnOption = rBtn_WThre.getText().toString();
                        colorName="O";
                        break;
                    case R.id.rBtn_WFur:
                        s_PFTQtnOption = rBtn_WFur.getText().toString();
                        colorName="R";
                        break;
                    default:
                        s_PFTQtnOption = "";
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
                        s_PFTWhyOptn=rBtn_op1.getText().toString();
                        break;
                    case R.id.rBtn_op2:
                        s_PFTWhyOptn=rBtn_op2.getText().toString();
                        break;
                    case R.id.rBtn_op3:
                        s_PFTWhyOptn=rBtn_op3.getText().toString();
                        break;
                    default:
                        s_PFTWhyOptn="";
                        break;
                }
            }
        });

        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerActivity.pager.setCurrentItem(5);
                QAnswerModel qAnswerModel = new QAnswerModel();
                qAnswerModel.setTimeSlotOption(s_PFTTime);
                qAnswerModel.setAnswerOption(s_PFTQtnOption);
                qAnswerModel.setWhyOption(s_PFTWhyOptn);
                qAnswerModel.setS_Position(sPosition);
                qAnswerModel.setColorCode(colorName);
                qAnswerModel.setQuestionId(ViewPagerActivity.questionerArrayList.get(4).get_id());
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
        scroll_View=v.findViewById(R.id.scroll_View);
    }
    private static PhysicalFitFragment instance = null;


    public static PhysicalFitFragment newInstance(String text){

        if(instance == null){
            // new instance
            instance = new PhysicalFitFragment();

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