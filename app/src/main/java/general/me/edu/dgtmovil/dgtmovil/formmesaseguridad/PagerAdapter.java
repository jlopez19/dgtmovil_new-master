package general.me.edu.dgtmovil.dgtmovil.formmesaseguridad;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import general.me.edu.dgtmovil.dgtmovil.formmesatecnica.ActaMesaDosFragment;
import general.me.edu.dgtmovil.dgtmovil.formmesatecnica.ActaMesaUnoFragment;

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
                MesaSegUnoFragment tab1 = new MesaSegUnoFragment();
                return tab1;
            case 1:
                MesaSegDosFragment tab2 = new MesaSegDosFragment();
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
