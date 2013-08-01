package org.lichsword.android.ui.designPanels.json;

import javax.swing.JPanel;

public abstract class AbstractModelPanel extends JPanel {

    private static final long serialVersionUID = 362502271095111875L;

    public abstract void setData(String jsonString);
}
