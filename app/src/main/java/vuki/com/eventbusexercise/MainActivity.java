package vuki.com.eventbusexercise;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import vuki.com.eventbusexercise.databinding.ActivityMainBinding;

/**
 * Main Activity for Event Bus exercise
 * On Button add fragments are added to stack.
 * Every fragment has button "job finished". Click on that button removes fragment from back stack.
 * His result is displayed on Main Activity label.
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    int currentFragment = 0;
    List<Fragment> fragments;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        fragments = new ArrayList<>();
        fragments.add( new FragmentA() );
        fragments.add( new FragmentB() );
        fragments.add( new FragmentA() );
        fragments.add( new FragmentC() );

        binding.add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                openFragments();
            }
        } );
    }

    private void openFragments() {
        int fragmentCounter = getFragmentManager().getBackStackEntryCount();
        if( fragmentCounter < fragments.size() ) {
            setFragment( fragments.get( fragmentCounter ) );
        }

    }

    private void setFragment( Fragment frag ) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations( android.R.animator.fade_in, android.R.animator.fade_out )
                .replace( binding.fragment.getId(), frag, frag.getClass().getSimpleName() )
                .addToBackStack( frag.getClass().getSimpleName() )
                .commit();
    }

    private void removeFragment() {
        android.app.FragmentManager fm = getFragmentManager();
        getFragmentManager().popBackStack( fm.getBackStackEntryAt( fm.getBackStackEntryCount() - 1 ).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE );
    }

    @Subscribe
    public void onFragmentChange( Module module ) {
        binding.result.setText( module.title );
        removeFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register( this );
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister( this );
        super.onStop();
    }

}
