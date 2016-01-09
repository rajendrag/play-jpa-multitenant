package service;

import common.AsyncJPA;
import models.bo.UnitEntity;
import play.db.jpa.JPA;
import play.libs.F.Promise;

import javax.inject.Singleton;
import java.util.Set;

import static play.libs.F.Promise.promise;

/**
 * Created by RP on 1/7/16.
 */
@Singleton
public class UnitSetupService {

    /*public Promise<Set<UnitEntity>> findAll() {
        try {
            return promise(() -> JPA.withTransaction(()-> {
                return UnitEntity.findAll(UnitEntity.class);
            }));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }*/

    public Promise<Set<UnitEntity>> findAll() {
        return AsyncJPA.doInAsync(() -> UnitEntity.findAll(UnitEntity.class));
    }
}
