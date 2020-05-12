package se.healthrover.ui_activity_controller.error_handling;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;


import androidx.annotation.NonNull;

import se.healthrover.R;
import se.healthrover.car_service.CarManagement;
import se.healthrover.car_service.CarManagementImp;
import se.healthrover.entities.CarCommands;
import se.healthrover.entities.HealthRoverCar;
import se.healthrover.ui_activity_controller.CarSelect;

public class ActivityExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Activity activity;
    private CarManagement carManagement;
    private HealthRoverCar healthRoverCar;
    private static final int RESTART_TIME_OUT = 2;

    public ActivityExceptionHandler(Activity activity, HealthRoverCar healthRoverCar) {
        this.activity = activity;
        carManagement = new CarManagementImp();
        this.healthRoverCar = healthRoverCar;
    }
    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        Log.i("Error", "Crash message: " + e.getMessage());
        Intent intent = new Intent(activity, CarSelect.class);
        if (healthRoverCar != null){
            carManagement.moveCar(healthRoverCar, Integer.parseInt(CarCommands.NO_MOVEMENT.getCarCommands()), Integer.parseInt(CarCommands.NO_ANGLE.getCarCommands()), activity);
        }
        intent.putExtra(activity.getString(R.string.crashErrorIntent), activity.getString(R.string.crashErrorMessage));
        activity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(RESTART_TIME_OUT);
    }
}
