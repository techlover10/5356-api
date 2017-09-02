package controllers;

import api.ReceiptResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;

import javax.ws.rs.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags/{tag}")
public class TagController{
    final ReceiptDao receipts;
    final int rid;

    public TagController(ReceiptDao receipts, int rid){
        this.receipts = receipts;
        this.rid = rid;
        }

    @PUT
    public void toggleTag(@PathParam("tag") String tagName){
        receipts.toggleTag(this.rid, tagName);
    }

    @GET
    public List<ReceiptResponse> getTag(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptRecords = receipts.getByTag(tagName);
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}
