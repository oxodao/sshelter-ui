package fr.oxodao.sshelter.ui;

import javax.swing.*;

public class PortSpinner extends JSpinner {

    public PortSpinner() {
        super();
        this.setModel(new SpinnerNumberModel(0, 0, 65535, 1));
        this.setEditor(new JSpinner.NumberEditor(this, "#"));
    }

}
