package com.jimboidin.patsays.Social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jimboidin.patsays.R;

public class SocialActivity extends AppCompatActivity implements LeaveSocialListener,
        LobbyListener {
    private final String TAG = "SocialActivity";
    private Boolean mInLobby;
    private String mHostName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        mInLobby = getIntent().getBooleanExtra("in_lobby", false);
        mHostName = getIntent().getStringExtra("host_name");


        SocialPagerAdapter socialPagerAdapter = new SocialPagerAdapter(getSupportFragmentManager(), getLifecycle());
        ViewPager2 pager = findViewById(R.id.social_pager);
        pager.setAdapter(socialPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.social_tabs);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout,
                pager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Friends");
                        break;
                    case 1:
                        tab.setText("Invitations");
                        break;
                    case 2:
                        tab.setText("Recent Players");
                        break;
                    default:
                        tab.setText("");
                }
            }
        });
        tabLayoutMediator.attach();
    }


    @Override
    public void onLeave() {
        finish();
    }


    @Override
    public Boolean askIsLobby() {
        return mInLobby;
    }

    @Override
    public String getHostName() {
        return mHostName;
    }

}

class SocialPagerAdapter  extends FragmentStateAdapter {

    public SocialPagerAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FriendsFragment();
            case 1:
                return new InvitationsFragment();
            case 2:
                return new RecentPlayersFragment();
        }
        throw new IllegalArgumentException("no item");
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}