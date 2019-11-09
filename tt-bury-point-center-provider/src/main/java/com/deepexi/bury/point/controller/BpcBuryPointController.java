package com.deepexi.bury.point.controller;

import com.deepexi.bury.point.domain.dto.BpcBuryPointDto;
import com.deepexi.bury.point.service.BpcBuryPointService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/bpcBuryPoints")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class BpcBuryPointController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BpcBuryPointService bpcBuryPointService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam BpcBuryPointDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(bpcBuryPointService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam BpcBuryPointDto eo) {
        return new Payload(bpcBuryPointService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(bpcBuryPointService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(BpcBuryPointDto eo) {
        return new Payload(bpcBuryPointService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, BpcBuryPointDto eo) {
        return new Payload(bpcBuryPointService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(bpcBuryPointService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(bpcBuryPointService.delete(pks));
    }
}
