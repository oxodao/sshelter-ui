package fr.oxodao.sshelter.api;

import fr.oxodao.sshelter.api.model.hydra.HydraError;
import fr.oxodao.sshelter.api.model.hydra.HydraList;
import fr.oxodao.sshelter.api.model.Machine;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fr.oxodao.sshelter.api.SshelterApi.JSON;

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
            e.printStackTrace();
        }

        return null;
    }

    public Machine create(Machine machine) throws Exception {
        var body = RequestBody.create(SshelterApi.gson.toJson(machine), JSON);
        Response resp = this.api.client.newCall(this.api.prepare("POST", "/machines", body)).execute();

        if (resp.code() == 400 || resp.code() == 422) {
            HydraError error = SshelterApi.gson.fromJson(
                    resp.body().string(),
                    HydraError.class
            );

            throw new Exception(error.toString());
        }

        if (resp.code() != 201) {
            throw new Exception("Error while creating machine: " + resp.body().string());
        }

        return SshelterApi.gson.fromJson(
                resp.body().string(),
                Machine.class
        );
    }

    public Machine update(Machine machine) throws Exception {
        if (machine.id == null || machine.id.length() == 0) {
            throw new IllegalArgumentException("Machine id is null or empty");
        }

        var body = RequestBody.create(SshelterApi.gson.toJson(machine), JSON);
        Response resp = this.api.client.newCall(this.api.prepare("PUT", machine.id, body)).execute();

        if (resp.code() == 400 || resp.code() == 422) {
            HydraError error = SshelterApi.gson.fromJson(
                    resp.body().string(),
                    HydraError.class
            );

            throw new Exception(error.toString());
        }

        if (resp.code() != 200) {
            throw new Exception("Error while updating machine: " + resp.body().string());
        }

        return SshelterApi.gson.fromJson(
                resp.body().string(),
                Machine.class
        );
    }

    public Machine createOrUpdate(Machine machine) throws Exception {
        if (machine.id == null || machine.id.length() == 0) {
            return this.create(machine);
        } else {
            return this.update(machine);
        }
    }

    public void delete(Machine machine) throws Exception {
        if (machine.id == null || machine.id.length() == 0) {
            throw new IllegalArgumentException("Machine id is null or empty");
        }

        Response resp = this.api.client.newCall(this.api.prepare("DELETE", machine.id)).execute();
        if (resp.code() != 204) {
            throw new Exception("Error while deleting machine: " + resp.body().string());
        }
    }
}
