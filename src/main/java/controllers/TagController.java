package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags/{tag}")
public class TagController{
    final String rid;

    public TagController(String rid){
        this.rid = rid;
        }

    @PUT
    public void toggleTag(@PathParam("tag") String tagName){
    }

    @GET
    public List<ReceiptResponse> getTag() {
        return null;
    }
}
