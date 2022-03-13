package fr.oxodao.sshelter.ui;

import fr.oxodao.sshelter.Sshelter;
import fr.oxodao.sshelter.api.model.Machine;

import fr.oxodao.sshelter.TrayIcon;

import javax.swing.*;
import java.awt.*;

class EditPanel extends JPanel {
    private final EditMachinesFrame frame;
    private Machine machine;
    private JPanel panel;
    private JPanel buttonPanel;

    private final JLabel title = new JLabel();

    private final GridBagConstraints constraints;
    private final JTextField fullnameField = new JTextField();
    private final JTextField shortnameField = new JTextField();
    private final JTextField hostnameField = new JTextField();
    private final JSpinner portField = new PortSpinner();
    private final JTextField usernameField = new JTextField();
    private final JTextArea otherSettingsField = new JTextArea();
    private final ForwardedPortPane forwardedPortPane = new ForwardedPortPane();

    private final JButton deleteButton = new JButton("Delete");
    private final JButton saveButton = new JButton("Save");

    protected EditPanel(EditMachinesFrame frame, Machine machine) {
        super(new BorderLayout());
        this.frame = frame;
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.title.setFont(this.title.getFont().deriveFont(Font.BOLD, this.title.getFont().getSize() + 2));
        this.portField.setMinimumSize(new Dimension(50, this.portField.getPreferredSize().height));
        this.portField.setEditor(new JSpinner.NumberEditor(this.portField, "#"));

        this.panel = new JPanel(new GridBagLayout());
        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.setMachine(machine);

        this.otherSettingsField.setMinimumSize(new Dimension(200, 200));
        this.otherSettingsField.setMaximumSize(new Dimension(200, 200));

        this.constraints = new GridBagConstraints();
        this.constraints.anchor = GridBagConstraints.NORTHWEST;
        this.constraints.ipadx = 10;
        this.constraints.ipady = 5;
        this.constraints.weighty = 1;
        this.constraints.weightx = 1;

        var i = 0;

        this.add(new JLabel("Full name:"), 0, i, false, 1);
        this.add(this.fullnameField, 1, i, true, 2);

        i++;
        this.add(new JLabel("Short name:"), 0, i, false, 1);
        this.add(this.shortnameField, 1, i, true, 2);

        i++;
        this.add(new JLabel("Hostname / port:"), 0, i, false, 1);
        this.add(this.hostnameField, 1, i, true, 1);
        this.add(this.portField, 2, i, true, 1);

        i++;
        this.add(new JLabel("Username:"), 0, i, false, 1);
        this.add(this.usernameField, 1, i, true, 2);

        i++;
        this.add(new JLabel("Port forwarding:"), 0, i, true, 3);

        i++;
        this.add(this.forwardedPortPane, 0, i, true, 3);

        this.title.setHorizontalAlignment(JLabel.CENTER);
        this.title.setVerticalAlignment(JLabel.CENTER);
        this.title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        i++;
        this.add(new JLabel("Other settings:"), 0, i, true, 3);

        i++;
        this.constraints.fill = GridBagConstraints.BOTH;
        var otherSettingsPane = new JScrollPane(this.otherSettingsField);
        otherSettingsPane.setPreferredSize(new Dimension(200, 200));
        this.add(otherSettingsPane, 0, i, true, 3);

        var sc = new JScrollPane(this.panel);
        sc.setBorder(null);

        this.add(this.title, BorderLayout.NORTH);
        this.add(sc, BorderLayout.CENTER);
        this.add(this.buttonPanel, BorderLayout.SOUTH);

        this.deleteButton.addActionListener(e -> {
           var res = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this machine?", "Sshelter: Delete machine", JOptionPane.YES_NO_OPTION);
           if (res == JOptionPane.YES_OPTION) {
               try {
                   if (this.machine.id != null) {
                       Sshelter.api.Machines().delete(this.machine);
                       var machines = Sshelter.api.Machines().findAll().elements;
                       if (machines != null) {
                           TrayIcon.get().setMachines(machines);
                           this.frame.setMachines(machines);
                       }
                   }

                   this.setMachine(null);
               } catch (Exception ex) {
                   JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
               }
           }
        });

        this.saveButton.addActionListener(e -> {
            try {
                Sshelter.api.Machines().create(this.machine);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Sshelter: Error creating/updating machine", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void add(Component cmp, int x, int y, boolean fill, int weightx) {
        this.constraints.gridx = x;
        this.constraints.gridy = y;
        this.constraints.gridwidth = weightx;
        this.constraints.fill = fill ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;

        this.panel.add(cmp, this.constraints);
    }

    public void setMachine(Machine machine) {
        this.machine = machine != null ? machine : new Machine();

        this.title.setText(this.machine.id != null && this.machine.id.length() > 0 ? "Editing machine" : "Creating a machine");
        this.fullnameField.setText(this.machine.name);
        this.shortnameField.setText(this.machine.shortName);
        this.hostnameField.setText(this.machine.hostname);
        this.portField.setValue(this.machine.port);
        this.usernameField.setText(this.machine.username);
        this.otherSettingsField.setText(this.machine.otherSettings);
        this.forwardedPortPane.setForwardedPorts(this.machine.forwardedPorts);

        this.buttonPanel.removeAll();

        if (this.machine.id != null && this.machine.id.length() > 0) {
            this.buttonPanel.add(this.deleteButton);
        }
        this.buttonPanel.add(this.saveButton);
    }
}
