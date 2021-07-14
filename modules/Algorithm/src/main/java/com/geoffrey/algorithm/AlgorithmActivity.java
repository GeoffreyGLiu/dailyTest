package com.geoffrey.algorithm;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：3/18/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AlgorithmActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_input;
    private TextView tv_palindrome, tv_mergearray, tv_findlongest, tv_findkth,tv_eraseinterval,tv_arrowshot,tv_charreset,tv_subsequence, tv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        initView();
        initListener();
    }

    private void initView() {
        et_input = findViewById(R.id.et_input);
        tv_palindrome = findViewById(R.id.tv_palindrome);
        tv_mergearray = findViewById(R.id.tv_mergearray);
        tv_findlongest = findViewById(R.id.tv_findlongest);
        tv_findkth = findViewById(R.id.tv_findkth);
        tv_eraseinterval = findViewById(R.id.tv_eraseinterval);
        tv_arrowshot = findViewById(R.id.tv_arrowshot);
        tv_charreset = findViewById(R.id.tv_charreset);
        tv_subsequence = findViewById(R.id.tv_subsequence);
        tv_show = findViewById(R.id.tv_show);

    }

    private void initListener() {
        tv_palindrome.setOnClickListener(this);
        tv_mergearray.setOnClickListener(this);
        tv_findlongest.setOnClickListener(this);
        tv_findkth.setOnClickListener(this);
        tv_eraseinterval.setOnClickListener(this);
        tv_arrowshot.setOnClickListener(this);
        tv_charreset.setOnClickListener(this);
        tv_subsequence.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_palindrome) {
            //查询回文字符串
            String str = String.valueOf(et_input.getText());
            boolean result = AlgorithmUtils.validPalindrome(str);
            tv_show.setText(str + " = " + result);
        } else if (id == R.id.tv_mergearray) {
            int[] arrayOne = new int[]{1, 2, 3, 0, 0, 0};
            int[] arrayTwo = new int[]{7, 8, 9};
            arrayOne = AlgorithmUtils.mergeTwoOrderedArray(arrayOne, 3, arrayTwo, 3);
            StringBuilder builder = new StringBuilder();
            builder.append("arrayOne = " + arrayToString(new int[]{1, 2, 3, 0, 0, 0}));
            builder.append("\r\n");
            builder.append("arrayTwo = " + arrayToString(arrayTwo));
            builder.append("\r\n");
            builder.append("arrayResult = " + arrayToString(arrayOne));
            tv_show.setText(builder.toString());
        } else if (id == R.id.tv_findlongest) {
            String src = "abpcpleamoaenkey";
            List<String> d = new ArrayList<>(Arrays.asList("ale", "apple", "monkey", "plea"));
            String result = AlgorithmUtils.findLongestWord(src, d);
            StringBuilder builder = new StringBuilder();
            builder.append("src = " + src);
            builder.append("\r\n");
            builder.append("d = " + d.toString());
            builder.append("\r\n");
            builder.append("result = " + result);
            tv_show.setText(builder.toString());
        } else if (id == R.id.tv_findkth) {
            int[] array = new int[]{1, 5, 4, 3, 6, 7, 2};
            if (!TextUtils.isEmpty(et_input.getText())) {
                String temp = et_input.getText().toString();
                int i = Integer.parseInt(temp);
                if (i <= array.length) {
                    int j = AlgorithmUtils.getKthLargest(array,i);
                    StringBuilder builder = new StringBuilder();
                    builder.append("array = " + arrayToString(array));
                    builder.append("\r\n");
                    builder.append("k = " + i);
                    builder.append("\r\n");
                    builder.append("num = " + j);
                    tv_show.setText(builder.toString());
                }
            }
        }else if (id == R.id.tv_eraseinterval){
            Interval interval1 = new Interval(1,3);
            Interval interval2 = new Interval(2,3);
            Interval interval3 = new Interval(3,4);
            Interval interval4 = new Interval(3,5);
            Interval interval5 = new Interval(5,6);
            Interval[] intervals = {interval1,interval2,interval3,interval4,interval5};
            int delete = AlgorithmUtils.eraseOverlapInterval(intervals);
            StringBuilder builder = new StringBuilder();
            for (Interval interval : intervals){
                builder.append("[");
                builder.append(interval.getStartMillis());
                builder.append(",");
                builder.append(interval.getEndMillis());
                builder.append("]");
                builder.append(",");
            }
            builder.append("\r\n");
            builder.append("delete array num = " + delete);
            tv_show.setText(builder.toString());
        }else if (id == R.id.tv_arrowshot){
            int[][] intarrays = new int[5][2];
            intarrays[0][0] = 1;intarrays[0][1] = 3;
            intarrays[1][0] = 2;intarrays[1][1] = 3;
            intarrays[2][0] = 3;intarrays[2][1] = 5;
            intarrays[3][0] = 6;intarrays[3][1] = 8;
            intarrays[4][0] = 9;intarrays[4][1] = 11;
            int minShot = AlgorithmUtils.findMinArrowShots(intarrays);
            StringBuilder builder = new StringBuilder();
            for (int[] interval : intarrays){
                builder.append("[");
                builder.append(interval[0]);
                builder.append(",");
                builder.append(interval[1]);
                builder.append("]");
                builder.append(",");
            }
            builder.append("\r\n");
            builder.append("min shot = " + minShot);
            tv_show.setText(builder.toString());
        }else if (id == R.id.tv_charreset){
            String str = "ababcbacadefegdehijhklij";
            List<Integer> result = AlgorithmUtils.partitionLabels(str);
            StringBuilder builder = new StringBuilder();
            builder.append("src = " + str);
            builder.append("\r\n");
            builder.append("result = ");
            for (int interval : result){
                builder.append(interval);
            }
            tv_show.setText(builder.toString());
        }else if (id == R.id.tv_subsequence){
            String str = "sfjeigdrsioejse";
            String subStr = "sefj";
            boolean isSub = AlgorithmUtils.isSubsequence(str,subStr);
            StringBuilder builder = new StringBuilder();
            builder.append("str = " + str);
            builder.append("\r\n");
            builder.append("subStr = " + subStr);
            builder.append("\r\n");
            builder.append("isSub = "+ isSub);
            tv_show.setText(builder.toString());
            int iTemp;
            testInt(iTemp = 3);
        }
    }

    private String arrayToString(int[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i : array) {
            builder.append(i);
            builder.append(",");
        }
        return builder.toString();
    }


    private int testInt(int intTemp){
        Toast.makeText(this, "intTemp = " + intTemp, Toast.LENGTH_SHORT).show();
        return intTemp;
    }}
