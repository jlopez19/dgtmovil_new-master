package general.me.edu.dgtmovil.dgtmovil.formactaforomunpal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import general.me.edu.dgtmovil.dgtmovil.formmesaseguridad.MesaSegDosFragment;
import general.me.edu.dgtmovil.dgtmovil.formmesaseguridad.MesaSegUnoFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ActaForoUnoFragment tab1 = new ActaForoUnoFragment();
                return tab1;
            case 1:
                ActaForoDosFragment tab2 = new ActaForoDosFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
