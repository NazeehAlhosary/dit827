package com.example.aptiv.View.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aptiv.Model.Class.Profile;
import com.example.aptiv.Model.Class.Zone;
import com.example.aptiv.Model.Class.Mode;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.R;
import com.example.aptiv.ViewModel.BaseViewModel;

public class DashboardFragment extends Fragment implements View.OnTouchListener {

    private MainActivity _owner;
    private View _view;
    private BaseViewModel _baseViewModel;
    public DefaultLayoutFragment DefaultLayoutFragment;
    private SoundLayoutFragment SoundLayoutFragment;
    private TempLayoutFragment TempLayoutFragment;
    private AirpLayoutFragment AirpLayoutFragment;
    private HumidityLayoutFragment HumidityLayoutFragment;
    private LuxLayoutFragment LuxLayoutFragment;
    private SettingsLayoutFragment SettingsLayoutFragment;
    private ModeLayoutFragment ModeLayoutFragment;
    private AddModeLayoutFragment AddModeLayoutFragment;
    private IoTDevicesHandlerFragment DevicesHandler;
    private IZoneSelection _callback;
    private ImageView _carMaskView;
    private ImageView _frontSeat;
    private ImageView _driverSeat;
    private ImageView _backSeat;
    private ImageView _frontSeatError;
    private ImageView _driverSeatError;
    private ImageView _backSeatError;
    private boolean popUpShown = false;

    private LayoutInflater _inflater;

    public Boolean _frontSeatSelected = false;
    public Boolean _backSeatSelected = false;
    public Boolean _driverSeatSelected = false;

    public DashboardFragment(MainActivity Owner, BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _inflater = inflater;
        _view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        SetupCarLayoutFragment();

        SetupButton();
        SetupEvents();
        _baseViewModel.SetDashboardFragment(this);

        return _view;
    }

    private void SetupButton() {
        _carMaskView = _view.findViewById(R.id.car_mask);
        _frontSeat = _view.findViewById(R.id.frontseat);
        _backSeat = _view.findViewById(R.id.backseat);
        _driverSeat = _view.findViewById(R.id.driverseat);

        _frontSeatError = _view.findViewById(R.id.frontseaterror);
        _backSeatError = _view.findViewById(R.id.backseaterror);
        _driverSeatError = _view.findViewById(R.id.driverseaterror);
    }

    private void SetupEvents() {
        _carMaskView.setOnTouchListener(this);
    }

    //setup default fragment with all values
    public void SetupCarLayoutFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        DefaultLayoutFragment = new DefaultLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, DefaultLayoutFragment).commit();
        _callback = DefaultLayoutFragment;
    }

    //Open volume fragment
    public void OpenVolumeFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        SoundLayoutFragment = new SoundLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, SoundLayoutFragment).commit();
        _callback = SoundLayoutFragment;
    }

    //Open temp fragment
    public void OpenTempFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        TempLayoutFragment = new TempLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, TempLayoutFragment).commit();
        _callback = TempLayoutFragment;
    }

    //Open ap fragment
    public void OpenAPFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        AirpLayoutFragment = new AirpLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, AirpLayoutFragment).commit();
        _callback = AirpLayoutFragment;
    }

    //Open mode fragment
    public void OpenModeFragment(Mode _currentMode) {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        ModeLayoutFragment = new ModeLayoutFragment(SettingsLayoutFragment, _owner, _baseViewModel, _currentMode);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, ModeLayoutFragment).commit();
    }

    //Open add mode fragment
    public void OpenAddModeFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        AddModeLayoutFragment = new AddModeLayoutFragment(SettingsLayoutFragment, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, AddModeLayoutFragment).commit();
    }

    //Open humidity fragment
    public void OpenHumidityFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        HumidityLayoutFragment = new HumidityLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, HumidityLayoutFragment).commit();
        _callback = HumidityLayoutFragment;
    }

    //Open lux fragment
    public void OpenLuxFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        LuxLayoutFragment = new LuxLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, LuxLayoutFragment).commit();
        _callback = LuxLayoutFragment;
    }

    //Open settings fragment
    public void OpenSettingsFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        SettingsLayoutFragment = new SettingsLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, SettingsLayoutFragment).commit();
        _callback = SettingsLayoutFragment;
    }

    //Open Devices Handler fragment
    public void OpenDHFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        DevicesHandler = new IoTDevicesHandlerFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, DevicesHandler).commit();
    }

    //onTouch event that is connected to the car image to read which seat is selected
    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();
        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                int touchColor = getHotspotColor(R.id.car_mask, evX, evY, this);
                if (closeMatch(Color.RED, touchColor)) {
                    if (_driverSeatSelected) {
                        _driverSeat.setVisibility(View.INVISIBLE);
                    } else {
                        _driverSeat.setVisibility(View.VISIBLE);
                    }
                    _driverSeatSelected = !_driverSeatSelected;
                } else if (closeMatch(Color.BLUE, touchColor)) {
                    if (_frontSeatSelected) {
                        _frontSeat.setVisibility(View.INVISIBLE);
                    } else {
                        _frontSeat.setVisibility(View.VISIBLE);
                    }
                    _frontSeatSelected = !_frontSeatSelected;
                } else if (closeMatch(Color.YELLOW, touchColor)) {
                    if (_backSeatSelected) {
                        _backSeat.setVisibility(View.INVISIBLE);
                    } else {
                        _backSeat.setVisibility(View.VISIBLE);
                    }
                    _backSeatSelected = !_backSeatSelected;
                } else {
                    _frontSeatSelected = false;
                    _backSeatSelected = false;
                    _driverSeatSelected = false;
                    _frontSeat.setVisibility(View.INVISIBLE);
                    _backSeat.setVisibility(View.INVISIBLE);
                    _driverSeat.setVisibility(View.INVISIBLE);
                }
                _callback.zoneIsSelected();
                break;
        }
        return true;
    }

    public void toggleError(Zone zone, boolean show) {
        switch (zone.getName()) {
            case DRIVER:
                if (show) {
                    _driverSeatError.setImageResource(R.drawable.driverseatprogressing);
                    _driverSeatError.setVisibility(View.VISIBLE);
                } else {
                    if(!popUpShown){
                        _driverSeatError.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case PASSENGER:
                if (show) {
                    _frontSeatError.setImageResource(R.drawable.frontseatprogressing);
                    _frontSeatError.setVisibility(View.VISIBLE);
                } else {
                    if(!popUpShown) {
                        _frontSeatError.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case BACK:
                if (show) {
                    _backSeatError.setImageResource(R.drawable.backseatprogressing);
                    _backSeatError.setVisibility(View.VISIBLE);
                } else {
                    if(!popUpShown) {
                        _backSeatError.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            default:
                //TODO: behavior for invalid zone name. this should never happen.
                break;

        }
    }

    public void CreatePopupView(final boolean DriverSeat, final boolean PassengerSeat, final boolean BackSeat, String messages, boolean OverrideButton, final Mode currentMode) {

        final View popupView = _inflater.inflate(R.layout.fragment_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = false;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //TODO if statement here
        if (!popUpShown) {


            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window token
            popupWindow.showAtLocation(_view, Gravity.CENTER, 0, 0);
            popUpShown = true;
            TextView txtMessage = popupView.findViewById(R.id.PopupViewMessage);
            if (messages == null || messages == "") {
                messages = "Oops, something went wrong!";
            }
            txtMessage.setText(messages);

            Button _overrideButton = popupView.findViewById(R.id.OverrideButton);
            _overrideButton.setVisibility(OverrideButton ? View.VISIBLE : View.GONE);
            if(OverrideButton){
                _overrideButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Do Something
                        if (DriverSeat) {
                            setValues(_baseViewModel.DriverProfile, currentMode);
                        }
                        if (PassengerSeat) {
                            setValues(_baseViewModel.PassengerProfile, currentMode);
                        }
                        if (BackSeat) {
                            setValues(_baseViewModel.BackProfile, currentMode);
                        }
                        popupWindow.dismiss();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                popUpShown = false;
                            }
                        }, 1000);
                    }
                });
            }
            // Getting a reference to button two and do something
            Button _okButton = popupView.findViewById(R.id.OkButton);
            _okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Do Something
                    popupWindow.dismiss();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popUpShown = false;
                        }
                    },1000);
                }
            });
        }
    }

    private void setValues(Profile p , Mode m){
        p.setIr(m.getLux().isEmpty() ?  null : m.getLux() );
        p.setTemperature(m.getTemp().isEmpty() ?  null :m.getTemp());
        p.setSound(m.getVolume().isEmpty() ?  null : m.getVolume());
        p.setPressure(m.getAirp().isEmpty() ?  null : m.getAirp());
        p.setHumidity(m.getHumidity().isEmpty() ?  null : m.getHumidity());
    }


    //seat selection in the car image is base on color
    //in the background of the car image there is img with 3 colors representing every single zone
    //check car_mask.png
    private int getHotspotColor(int hotspotId, int x, int y, Fragment g) {
        ImageView img = g.getView().findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    //check if color that has been selected is in the tolerance range
    private boolean closeMatch(int color1, int color2) {
        int tolerance = 50;
        if ((int) Math.abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false;
        if ((int) Math.abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false;
        if ((int) Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance)
            return false;
        return true;
    }

    public void CreateTempPopupView(boolean driver, boolean passenger, boolean back) {
        final View popupView = _inflater.inflate(R.layout.fragment_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = false;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        if (!popUpShown) {
            popupWindow.showAtLocation(_view, Gravity.CENTER, 0, 0);
            popUpShown = true;
            TextView txtMessage = popupView.findViewById(R.id.PopupViewMessage);
            String ZoneName = driver ? "driver": passenger ? "passenger" : "back" ;
            String messages = "Values has unexpectedly changed in the " + ZoneName + " zone!";
            txtMessage.setText(messages);

            _driverSeatError.setVisibility(driver ? View.VISIBLE : View.GONE);
            _driverSeatError.setImageResource(R.drawable.driverseatred);
            _frontSeatError.setVisibility(passenger ? View.VISIBLE : View.GONE);
            _frontSeatError.setImageResource(R.drawable.frontseatred);
            _backSeatError.setVisibility(back ? View.VISIBLE : View.GONE);
            _backSeatError.setImageResource(R.drawable.backseatred);

            Button _overrideButton = popupView.findViewById(R.id.OverrideButton);
            _overrideButton.setVisibility(View.GONE);
            Button _okButton = popupView.findViewById(R.id.OkButton);
            _okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Do Something

                    popupWindow.dismiss();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popUpShown = false;
                            _driverSeatError.setVisibility(View.GONE);
                            _frontSeatError.setVisibility(View.GONE);
                            _backSeatError.setVisibility(View.GONE);
                        }
                    },5000);
                }
            });
        }
    }
}