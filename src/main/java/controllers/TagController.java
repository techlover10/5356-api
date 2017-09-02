package controllers;

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
@Produces(MediaType.APPLICATION_JSON)
public class TagController{
    final ReceiptDao receipts;

    public TagController(ReceiptDao receipts){
        this.receipts = receipts;
        }

    @PUT
    public void toggleTag(@PathParam("tag") String tagName, @Valid @NotNull int rid){
        receipts.toggleTag(rid, tagName);
    }

    @GET
    public List<ReceiptResponse> getTag(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptRecords = receipts.getByTag(tagName);
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}
