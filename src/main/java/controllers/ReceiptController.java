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

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;

    public ReceiptController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @POST
    public String createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        int rid = receipts.insert(receipt.merchant, receipt.amount);
        ReceiptsRecord r = receipts.getRecord(rid);
        String time;
        if (r != null) {
            time = receipts.getRecord(rid).getUploaded().toString();
        } else {
            time = "0";
        }
        return rid + ";" + time;

    }

    @GET
    public List<ReceiptResponse> getReceipts() {
        List<ReceiptsRecord> receiptRecords = receipts.getAllReceipts();
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}

