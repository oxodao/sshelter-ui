package fr.oxodao.sshelter;

public class ThreadSync extends Thread {

    private final TrayIcon ti;
    private boolean autosync;

    public ThreadSync(TrayIcon ti) {
        this.autosync = false;
        this.ti = ti;
    }

    public boolean getAutosync() {
        return this.autosync;
    }

    public void setAutosync(boolean autosync) {
        this.autosync = autosync;
        this.ti.autosyncMenuItem.setChecked(autosync);

        if (!this.isAlive() && autosync) {
            this.start();
        }
    }

    public void toggleAutosync() {
        this.setAutosync(!this.autosync);
    }

    @Override
    public void run() {
        while (this.autosync) {
            try {
                System.out.println("Sync...");

                var machines = Sshelter.api.Machines().findAll();
                this.ti.setMachines(machines.elements);

                Thread.sleep(30 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
