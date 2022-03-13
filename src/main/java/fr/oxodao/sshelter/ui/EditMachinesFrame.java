package fr.oxodao.sshelter.ui;

import fr.oxodao.sshelter.api.model.Machine;

import javax.swing.*;
import java.awt.*;

public class EditMachinesFrame extends JFrame {

    private final EditPanel editPanel = new EditPanel(this, null);
    private final JList<Machine> machinesList;

    public EditMachinesFrame(Machine[] machines) {
        this.setTitle("Sshelter - Edit machines...");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.setMinimumSize(new Dimension(1000, 800));
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.machinesList = new JList<>(machines);

        JButton newButton = new JButton("New");
        JPanel leftPanel = new JPanel(new BorderLayout(0, 5));
        leftPanel.add(new JScrollPane(this.machinesList), BorderLayout.CENTER);
        leftPanel.add(newButton, BorderLayout.SOUTH);

        content.add(leftPanel, BorderLayout.WEST);
        content.add(this.editPanel, BorderLayout.CENTER);

        this.machinesList.addListSelectionListener(e -> this.editPanel.setMachine(this.machinesList.getSelectedValue()));
        newButton.addActionListener(e -> {
            this.editPanel.setMachine(null);
            this.machinesList.clearSelection();
        });

        this.setContentPane(content);
        this.setVisible(true);
    }

    public void setMachines(Machine[] machines) {
        this.machinesList.setListData(machines);
    }

}
