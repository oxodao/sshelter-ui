package fr.oxodao.sshelter.ui;

import fr.oxodao.sshelter.api.model.ForwardedPort;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ForwardedPortPane extends JPanel {

    private ArrayList<ForwardedPort> forwardedPorts;
    private JList<ForwardedPort> list = new JList<>();

    private JTextField localHostField = new JTextField();
    private PortSpinner localPortField = new PortSpinner();

    private boolean reversed = false;
    private JButton directionButton = new JButton("Mapped locally");

    private JTextField remoteHostField = new JTextField();
    private PortSpinner remotePortField = new PortSpinner();

    private JButton addButton = new JButton("New");
    private JButton removeButton = new JButton("Remove");
    private JButton saveButton = new JButton("Save");

    public ForwardedPortPane() {
        super();
        this.setLayout(new BorderLayout());

        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(new JScrollPane(list), BorderLayout.CENTER);
        leftPane.add(this.addButton, BorderLayout.SOUTH);

        leftPane.setPreferredSize(new Dimension(200, 80));

        this.add(leftPane, BorderLayout.WEST);

        JPanel rightPane = new JPanel(new GridLayout(6, 2));
        rightPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        rightPane.add(new JLabel("Local host:"));
        rightPane.add(this.localHostField);
        rightPane.add(new JLabel("Local port:"));
        rightPane.add(this.localPortField);

        rightPane.add(Box.createGlue());
        rightPane.add(this.directionButton);

        rightPane.add(new JLabel("Remote host:"));
        rightPane.add(this.remoteHostField);
        rightPane.add(new JLabel("Remote port:"));
        rightPane.add(this.remotePortField);

        rightPane.add(this.removeButton);
        rightPane.add(this.saveButton);

        this.add(rightPane, BorderLayout.CENTER);

        this.list.addListSelectionListener(e -> {
            this.setSelectedForwardedPort(this.list.getSelectedValue());
            this.removeButton.setEnabled(this.list.getSelectedIndex() != -1);
        });

        this.addButton.addActionListener(e -> {
            this.setSelectedForwardedPort(null);
            this.list.clearSelection();
        });

        this.saveButton.addActionListener(e -> {
            var isNew = this.list.getSelectedValue() == null;
            var fw = !isNew ? this.list.getSelectedValue() : new ForwardedPort();

            fw.localHostname = this.localHostField.getText().length() > 0 ? this.localHostField.getText() : null;
            fw.localPort = (int)this.localPortField.getValue();
            fw.reversed = this.reversed;
            fw.remoteHostname = this.remoteHostField.getText().length() > 0 ? this.remoteHostField.getText() : null;
            fw.remotePort = (int)this.remotePortField.getValue();

            if (isNew) {
                this.forwardedPorts.add(fw);
            } else {
                this.forwardedPorts.replaceAll(f -> f.equals(fw) ? fw : f);
            }

            this.setForwardedPorts(this.forwardedPorts.toArray(new ForwardedPort[0]));

            if (isNew) {
                this.list.setSelectedIndex(this.forwardedPorts.size() - 1);
            }
        });

        this.directionButton.addActionListener(e -> {
            this.reversed = !this.reversed;
            this.directionButton.setText(this.reversed ? "Mapped remotely" : "Mapped locally");
        });

        this.removeButton.addActionListener(e -> {
           this.forwardedPorts.remove(this.list.getSelectedValue());
           this.setForwardedPorts(this.forwardedPorts.toArray(new ForwardedPort[0]));
        });
    }

    public void setForwardedPorts(ForwardedPort[] forwardedPorts) {
        this.forwardedPorts = new ArrayList<>(Arrays.asList(forwardedPorts));
        this.list.setListData(forwardedPorts);

        this.removeButton.setEnabled(this.list.getSelectedIndex() != -1);
    }

    private void setSelectedForwardedPort(ForwardedPort forwardedPort) {
        if (forwardedPort == null) {
            forwardedPort = new ForwardedPort();
        }
        this.localHostField.setText(forwardedPort.localHostname);
        this.localPortField.setValue(forwardedPort.localPort);

        this.reversed = forwardedPort.reversed;
        this.directionButton.setText(this.reversed ? "Mapped remotely" : "Mapped locally");

        this.remoteHostField.setText(forwardedPort.remoteHostname);
        this.remotePortField.setValue(forwardedPort.remotePort);
    }

    public ForwardedPort[] getForwardedPorts() {
        return this.forwardedPorts.toArray(new ForwardedPort[0]);
    }
}
