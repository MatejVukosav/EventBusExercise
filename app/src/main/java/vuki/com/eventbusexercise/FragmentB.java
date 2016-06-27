package vuki.com.eventbusexercise;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import vuki.com.eventbusexercise.databinding.FragmentBBinding;

/**
 * Created by mvukosav on 27.6.2016..
 */
public class FragmentB extends Fragment {

    FragmentBBinding binding;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_b, container, false );

        binding.done.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                EventBus.getDefault().postSticky( new Module( "Fragment B finished his job" ) );
            }
        } );

        return binding.getRoot();
    }

}
