package com.july.pigeon.ui.activity.jiaohuan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.ForgetPassWordActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.DateUtils;
import com.july.pigeon.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 设置脚环
 * Created by Administrator on 2017/11/1 0001.
 */

public class SetJiaohuan extends BaseActivity {
    @BindView(R.id.title_center)
    public TextView title;
    @BindView(R.id.left_tv)
    public TextView leftTv;
    @BindView(R.id.intervalSpinner)
    public Spinner intervalSpinner;
    @BindView(R.id.postSpinner)
    public Spinner postSpinner;
    @BindView(R.id.startTime)
    public TextView startTime;
    @BindView(R.id.endTime)
    public TextView endTime;

    private BaseListAdapter adapter;
    private BaseListAdapter adapter1;
    private List<String> list = new ArrayList<String>();
    private String intervalTime = "";//采集时间间隔
    private String postTime = "";//发送时间间隔
    private String startTimeString = "";//开始时间
    private String endTimeString = "";//结束时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_jiaohuan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        list.add("0.5");
        list.add("1");
        list.add("1.5");
        list.add("2");

        title.setText("忘记密码");
        leftTv.setText("取消");
        leftTv.setVisibility(View.VISIBLE);
        setAdapter();
    }

    private void setAdapter() {
        adapter = new BaseListAdapter(this, list, R.layout.spinner_item) {
            @Override
            public void convert(CommViewHolder holder) {
                TextView tv = holder.getView(R.id.time);
                tv.setText(list.get(holder.getPosition()) + "分钟");
            }
        };
        intervalSpinner.setAdapter(adapter);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intervalTime = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter1 = new BaseListAdapter(this, list, R.layout.spinner_item) {
            @Override
            public void convert(CommViewHolder holder) {
                TextView tv = holder.getView(R.id.time);
                tv.setText(list.get(holder.getPosition()) + "分钟");
            }
        };
        postSpinner.setAdapter(adapter1);
        postSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                postTime = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.left_tv)
    public void finishs() {
        finish();
    }

    @OnClick(R.id.next_step)
    public void sendRuquest() {
        if (StringUtils.isEmpty(intervalTime)) {
            BasicTool.showToast(this, "请选择采集频率");
            return;
        }
        if (StringUtils.isEmpty(postTime)) {
            BasicTool.showToast(this, "请选择发送频率");
            return;
        }
        if (StringUtils.isEmpty(startTimeString)) {
            BasicTool.showToast(this, "请选择开始时间");
            return;
        } if (StringUtils.isEmpty(endTimeString)) {
            BasicTool.showToast(this, "请选择结束时间");
            return;
        }




    }

    @OnClick(R.id.startTimeLayout)
    public void selectStartTime() {
        MyDatePickerDialog dialog = new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                startTime.setText(year + "年" + month + "月" + dayOfMonth + "日");
                startTimeString = year + "-" + month + "-" + dayOfMonth;
                showTimeDialog();
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialog.getDatePicker();
        // datePicker.setMinDate(new Date().getTime());
        dialog.show();
    }

    @OnClick(R.id.endTimeLayout)
    public void selectEndTime() {
        MyDatePickerDialog dialog = new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                endTime.setText(year + "年" + month + "月" + dayOfMonth + "日");
                endTimeString = year + "-" + month + "-" + dayOfMonth;
                showEndTimeDialog();
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialog.getDatePicker();
        // datePicker.setMinDate(new Date().getTime());
        dialog.show();
    }

    private Calendar c;

    private void showTimeDialog() {
        c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        MyTimePickerDialog timeDialog = new MyTimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                c.setTimeInMillis(System.currentTimeMillis());

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                c.set(Calendar.MINUTE, minute);

                c.set(Calendar.SECOND, 0);

                c.set(Calendar.MILLISECOND, 0);
                if (StringUtils.isEmpty(startTimeString)) {
                    return;
                }
                String lastEndTime = startTimeString.substring(startTimeString.length() - 2, startTimeString.length());
                if (!StringUtils.isEquals("00", lastEndTime)) {
                    startTimeString = startTimeString + " " + hourOfDay + ":" + minute + ":00";
                }

                if (DateUtils.strToDate(startTimeString).getTime() < new Date().getTime()) {
                    Toast.makeText(SetJiaohuan.this, "截止日期不得早于当前日期", Toast.LENGTH_SHORT).show();
                    startTimeString = "";
                    startTime.setText("");
                } else {
                    String lastCode = startTime.getText().toString().subSequence(startTime.getText().toString().length() - 1, startTime.getText().toString().length()) + "";
                    if (!StringUtils.isEquals("分", lastCode)) {
                        startTime.setText(startTime.getText() + "  " + hourOfDay + "时" + minute + "分");
                    }

                }

            }
        }, hour, minute, true);
        timeDialog.show();
    }

    private void showEndTimeDialog() {
        c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        MyTimePickerDialog timeDialog = new MyTimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                c.setTimeInMillis(System.currentTimeMillis());

                c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                c.set(Calendar.MINUTE, minute);

                c.set(Calendar.SECOND, 0);

                c.set(Calendar.MILLISECOND, 0);
                if (StringUtils.isEmpty(endTimeString)) {
                    return;
                }
                String lastEndTime = endTimeString.substring(endTimeString.length() - 2, endTimeString.length());
                if (!StringUtils.isEquals("00", lastEndTime)) {
                    endTimeString = endTimeString + " " + hourOfDay + ":" + minute + ":00";
                }

                if (DateUtils.strToDate(endTimeString).getTime() < new Date().getTime()) {
                    Toast.makeText(SetJiaohuan.this, "截止日期不得早于当前日期", Toast.LENGTH_SHORT).show();
                    endTimeString = "";
                    endTime.setText("");
                } else {
                    String lastCode = endTime.getText().toString().subSequence(endTime.getText().toString().length() - 1, endTime.getText().toString().length()) + "";
                    if (!StringUtils.isEquals("分", lastCode)) {
                        endTime.setText(endTime.getText() + "  " + hourOfDay + "时" + minute + "分");
                    }

                }

            }
        }, hour, minute, true);
        timeDialog.show();
    }

    private class MyDatePickerDialog extends DatePickerDialog {

        public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
        }

        @Override
        protected void onStop() {

        }
    }

    private class MyTimePickerDialog extends TimePickerDialog {

        public MyTimePickerDialog(Context context, int theme, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
            super(context, theme, callBack, hourOfDay, minute, is24HourView);
        }

    }
}
