package com.gmail.damianmajcherq.tspd.production;

import java.awt.*;

public interface ILeafController {

    boolean controlType(Object o);

    int getCount(TreeBranchData parent);

    Object get(TreeBranchData parent , int index);

    Component getLeaf(Object data);

    boolean hashBranchAccess(TreeBranchData branch);

}
