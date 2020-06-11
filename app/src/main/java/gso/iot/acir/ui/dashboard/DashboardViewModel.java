package gso.iot.acir.ui.dashboard;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;


public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
        mText.setValue(settings.getString("webServerAddress", ""));
    }

    public LiveData<String> getText() {
        return mText;
    }





}