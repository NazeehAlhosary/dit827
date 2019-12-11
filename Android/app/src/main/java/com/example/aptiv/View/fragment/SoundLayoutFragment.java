package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.aptiv.Model.Helper.ProfileHelper;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

public class SoundLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private TextView _soundTextView;
    private TextView _zoneSoundTextView;
    private TextView _zoneTextView;
    private LinearLayout _zoneCrollerLayout;
    private ImageView _minusButton;
    private ImageView _plusButton;
    private double _desiredTemp;
    private boolean _plusMinusButtonClicked = false;
    double temp = 0;

    public SoundLayoutFragment(DashboardFragment parentFragment, MainActivity Owner, BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_soundlayout, container, false);

        setUpView();
        setUpElements();
        zoneIsSelected();
        setUpTimer();
        registerOnClickListeners();

        return _view;
    }

    private void registerOnClickListeners() {
        _plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredTemp++;
                PlusMinusButtonClicked(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
            }
        });
        _minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredTemp--;
                PlusMinusButtonClicked(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
            }
        });
    }

    private void setUpView() {
        _soundTextView = _view.findViewById(R.id.SoundValue);
        _zoneSoundTextView = _view.findViewById(R.id.SoundZoneValue);
        _zoneTextView = _view.findViewById(R.id.SelectZoneTextView);
        _zoneCrollerLayout = _view.findViewById(R.id.zoneCrollerLayout);
        _minusButton = _view.findViewById(R.id.minus);
        _plusButton = _view.findViewById(R.id.plus);
    }

    private void setUpElements() {
        _zoneTextView.setText("Please click on specific zone to change value in it");
        _soundTextView.setText(_baseViewModel.MiddleZone.getSound());
    }


    //When a zone is selected on the car
    //values need to be changed based on zone
    @Override
    public void zoneIsSelected() {
        _desiredTemp = temp;
        if (_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected) {
            _zoneTextView.setVisibility(View.GONE);
            _zoneSoundTextView.setVisibility(View.VISIBLE);
            _zoneCrollerLayout.setVisibility(View.VISIBLE);
            updateSoundValue(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
        } else {
            _zoneTextView.setVisibility(View.VISIBLE);
            _zoneSoundTextView.setVisibility(View.GONE);
            _zoneCrollerLayout.setVisibility(View.GONE);
        }
    }

    //calculate average temp based on zone that is selected
    private void updateSoundValue(boolean Driver, boolean Passenger, boolean Back) {
        if ((int) _desiredTemp == (int) temp) {
            _plusMinusButtonClicked = false;
        }

        temp = Double.parseDouble(_baseViewModel.MiddleZone.getSound());

        int count = 1;
        if (Driver) {
            temp = temp + Double.parseDouble(_baseViewModel.DriverZone.getSound());
            count++;
        }
        if (Passenger) {
            temp = temp + Double.parseDouble(_baseViewModel.PassengerZone.getSound());
            count++;
        }
        if (Back) {
            temp = temp + Double.parseDouble(_baseViewModel.BackseatZone.getSound());
            count++;
        }
        if (count == 4) {
            temp = Double.parseDouble(_baseViewModel.MiddleZone.getSound());
            count = 1;
        }
        temp = temp / count;
        _zoneSoundTextView.setText(String.valueOf((int) temp));

        if (!_plusMinusButtonClicked) {
            _zoneSoundTextView.setTextSize(50);
            _zoneSoundTextView.setText(String.valueOf((int) temp));
        }
        if (_plusMinusButtonClicked) {
            PlusMinusButtonClicked(Driver, Passenger, Back);
        }
    }

    private boolean checkZoneDifferences(boolean driver, boolean passenger, boolean backseat) {
        if (driver) {
            return ProfileHelper.checkSound(_baseViewModel.DriverZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.BackseatZone);
        }
        if (passenger) {
            return ProfileHelper.checkSound(_baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone,
                    _baseViewModel.BackseatZone);
        }
        if (backseat) {
            return ProfileHelper.checkSound(_baseViewModel.BackseatZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone);
        }
        return true;
    }

    private void PlusMinusButtonClicked(boolean Driver, boolean Passenger, boolean Back) {
        _plusMinusButtonClicked = true;

        if (checkZoneDifferences(Driver, Passenger, Back)) {
            _zoneSoundTextView.setTextSize(25);
            _zoneSoundTextView.setText("In progress...\n Changing Volume\n from " + (int) _desiredTemp + " to " + String.valueOf((int) temp));
            if (Driver) {
                _baseViewModel.DriverProfile.setSound(Double.toString(_desiredTemp));
            }
            if (Passenger) {
                _baseViewModel.PassengerProfile.setSound(Double.toString(_desiredTemp));
            }
            if (Back) {
                _baseViewModel.BackProfile.setSound(Double.toString(_desiredTemp));
            }
        } else {

            _parentFragment.CreatePopupView(Driver, Passenger, Back, "Sound is too different from other zones! Adjust other zones and try again.", false);
            //TODO
            //if yes: implement adjustment behavior
            //else: reset to original value
        }
    }


    //region Timer method
    private void setUpTimer() {
        new CountDownTimer(30000, 1000) {

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
        _soundTextView.setText(_baseViewModel.MiddleZone.getSound());
        updateSoundValue(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
    }
    //endregion
}
