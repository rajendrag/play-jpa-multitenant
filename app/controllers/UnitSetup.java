package controllers;

import models.bo.UnitEntity;
import play.db.jpa.Transactional;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.UnitSetupService;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Delegates the calls to Service.
 * Note: @Transactional can not be used as Action compositions are called before RequestHandler and @Transactional tries
 * to bind the entity manager which fails as the tenant is not available yet.
 * Created by RP on 12/15/15.
 */
public class UnitSetup extends Controller {

    @Inject
    private UnitSetupService unitSetupService;


    public F.Promise<Result> index() {
        return list();
    }

    /**
     * Returns list of units.
     */
    public F.Promise<Result> list() {
        F.Promise<Set<UnitEntity>> entities = unitSetupService.findAll();
        return entities.map((Set<UnitEntity> e) -> {
            return ok(Json.toJson(e));
        });
    }
}
