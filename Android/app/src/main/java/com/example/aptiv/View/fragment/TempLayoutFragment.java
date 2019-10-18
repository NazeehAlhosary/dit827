package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.sdsmdg.harjot.crollerTest.Croller;

import androidx.fragment.app.Fragment;

public class TempLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;

    private TextView SetText;
    private TextView TempValue;
    private TextView tempChangeValue;

    private LinearLayout SetTempLayout;
    private Switch TempTypeSwitch;



    public TempLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_templayout, container, false);

        setUpView();
        setUpElements();
        zoneIsSelected();
        setUpTimer();

        return _view;
    }

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        tempChangeValue = _view.findViewById(R.id.tempChangeValue);
        TempValue = _view.findViewById(R.id.tempValue);
        SetTempLayout = _view.findViewById(R.id.SetTempLayout);
        TempTypeSwitch = _view.findViewById(R.id.TempTypeSwitch);

    }

    private void setUpElements(){

        TempValue.setText(_baseViewModel.MiddleZone.getTemperature() + "° C");

    }


    @Override
    public void zoneIsSelected() {
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
              SetText.setVisibility(View.GONE);
              SetTempLayout.setVisibility(View.VISIBLE);
              tempChangeValue.setVisibility(View.VISIBLE);
              updateTempValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);

        }else{
              SetText.setVisibility(View.VISIBLE);
              tempChangeValue.setVisibility(View.GONE);
              SetTempLayout.setVisibility(View.GONE);


        }
    }

    private void updateTempValue(boolean Driver, boolean Passenger , boolean Back) {
        double temp = 0;
        String fahrenheit = "° F";
        String celsius = "° C";
        String tempType =(TempTypeSwitch.isChecked()) ? fahrenheit: celsius;

        if(Driver && Passenger && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+  Double.parseDouble(_baseViewModel.PassengerZone.getTemperature()) + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature()) +  Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/4)+32 : temp/4;
        }
        else if(Driver && Passenger){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+  Double.parseDouble(_baseViewModel.PassengerZone.getTemperature()) +  Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/3)+32 : temp/3;
        }
        else if(Passenger && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+  Double.parseDouble(_baseViewModel.PassengerZone.getTemperature()) + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/3)+32 : temp/3;
        }
        else if(Driver && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature()) + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature()) +  Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/3)+32 : temp/3;
        }
        else if(Driver){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/2)+32 : temp/2;
        }
        else if(Passenger){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+Double.parseDouble(_baseViewModel.PassengerZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/2)+32 : temp/2;
        }
        else if(Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+Double.parseDouble(_baseViewModel.BackseatZone.getTemperature());
            temp = (tempType == fahrenheit) ? ((1.8*temp)/2)+32 : temp/2;
        }


        if(temp == 0 && tempType == celsius){
            TempValue.setText(_baseViewModel.MiddleZone.getTemperature() + celsius);
            tempChangeValue.setText(_baseViewModel.MiddleZone.getTemperature() + celsius);
        }
        else if (temp == 0){
            double midTemp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
            midTemp = (1.8 *midTemp) +32;
            TempValue.setText(midTemp + fahrenheit);
            tempChangeValue.setText(midTemp + fahrenheit);
        }
        else {

            TempValue.setText(String.valueOf((int)temp) + tempType);
            tempChangeValue.setText(String.valueOf((int)temp) + tempType);
        }


    }

    private void setUpTimer(){
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                UpdateData();
                setUpTimer();
            }
        }.start();
    }

    private void UpdateData() {
        _baseViewModel.UpdateData();
        updateView();
    }

    private void updateView() {
        TempValue.setText(_baseViewModel.MiddleZone.getTemperature()  + "° C");
        updateTempValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
    }


}