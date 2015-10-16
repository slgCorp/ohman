package tdt.minh095.ohman.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdt.minh095.ohman.R;

/**
 * Created by MyPC on 22/09/2015.
 */


public class FragmentRegister extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register, container, false);
    }
}
