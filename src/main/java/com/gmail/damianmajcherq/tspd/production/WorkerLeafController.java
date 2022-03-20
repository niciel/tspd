package com.gmail.damianmajcherq.tspd.production;

import java.awt.*;

public class WorkerLeafController implements ILeafController {



    @Override
    public boolean controlType(Object o) {
        return false;
    }

    @Override
    public int getCount(TreeBranchData parent) {
        return 0;
    }

    public Component getLeaf(Object data){
        return null;
    }


    @Override
    public Object get(TreeBranchData parent, int index) {
        return null;
    }

    public boolean hashBranchAccess(TreeBranchData branch) {
        return true;
    }





}
