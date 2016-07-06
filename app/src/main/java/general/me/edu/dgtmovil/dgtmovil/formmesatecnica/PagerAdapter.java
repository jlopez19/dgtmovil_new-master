package general.me.edu.dgtmovil.dgtmovil.formmesatecnica;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import general.me.edu.dgtmovil.dgtmovil.formpreinscripcion.FormDosFragment;
import general.me.edu.dgtmovil.dgtmovil.formpreinscripcion.FormUnoFragment;

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
                ActaMesaUnoFragment tab1 = new ActaMesaUnoFragment();
                return tab1;
            case 1:
                ActaMesaDosFragment tab2 = new ActaMesaDosFragment();
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
