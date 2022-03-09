package com.gmail.damianmajcherq.tspd.production;

import com.gmail.damianmajcherq.tspd.ITSPDModule;
import com.gmail.damianmajcherq.tspd.MainModule;

public class ProductionModule implements ITSPDModule {


    @Override
    public void preStart(MainModule main) {
        SqlProduction sqp = new SqlProduction(main.GetSqlManagement());
        sqp.init();
    }

    @Override
    public void onStart(MainModule main) {

    }

    @Override
    public void initFrames(MainModule main) {



    }
}
