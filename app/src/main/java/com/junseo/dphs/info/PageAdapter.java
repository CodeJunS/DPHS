package com.junseo.dphs.info;

/**
 * Created by Junseo on 16. 3. 18..
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.junseo.dphs.fragment.Page1;
import com.junseo.dphs.fragment.Page2;
import com.junseo.dphs.fragment.Page3;


/**
 * Created by Junseo on 2015-04-10.
 */
public class PageAdapter extends FragmentStatePagerAdapter {


    public View view;
    private Context context;

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "학교소개";
        } else if (position == 1) {
            return "학교상징";
        } else if (position == 2) {
            return "오시는길";
        } else {
            return null;
        }

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Page1(context);
            case 1:
                return new Page2(context);
            case 2:
                return new Page3(context);
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    /* @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schoolinfo, container, false);

        FloatingActionsMenu rightLabels = (FloatingActionsMenu) view.findViewById(R.id.multiple_action);

        view.findViewById(R.id.action_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:070-8620-4500"));
                SchoolInfo.context_info.startActivity(intent);
            }
        });
        view.findViewById(R.id.action_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:070-8620-4560"));
                SchoolInfo.context_info.startActivity(intent);
            }
        });
        return view;
    } */
}