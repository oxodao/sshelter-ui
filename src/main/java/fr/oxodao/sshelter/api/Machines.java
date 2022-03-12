package fr.oxodao.sshelter.api;

import com.google.gson.reflect.TypeToken;
import fr.oxodao.sshelter.api.model.HydraList;
import fr.oxodao.sshelter.api.model.Machine;
import okhttp3.Response;

public class Machines {
    private final SshelterApi api;

    public Machines(SshelterApi api) {
        this.api = api;
    }

    public HydraList<Machine> findAll() {
        try {
            Response resp = this.api.client.newCall(this.api.prepare("GET", "/machines")).execute();
            return SshelterApi.gson.fromJson(
                    resp.body().string(),
                    HydraList.getType(Machine.class)
            );
        } catch (Exception e){
            System.out.println("Err: " + e.getMessage());
        }

        return null;
    }
}
