package dj.appmastery.main.modules.adapters;

import dj.appmastery.main.R;

/**
 * Created by User on 23-10-2016.
 */
public class SubTitlesAdapter extends TitlesAdapter{

    public SubTitlesAdapter(MenuSelectionListener listener) {
        super(listener);
    }

    @Override
    protected int getRootLayout() {
        //return super.getRootLayout();
        return R.layout.adapter_sub_menu;
    }
}
