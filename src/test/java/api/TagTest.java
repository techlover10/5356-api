package api;


import controllers.ReceiptController;
import controllers.TagController;
import generated.tables.records.ReceiptsRecord;
import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class TagTest {

    private final Validator validator = Validators.newValidator();

    @Test
    public void testEmptyTags() {
        ReceiptsRecord r = new ReceiptsRecord();
        r.setId(1);
        r.setAmount(BigDecimal.ONE);
        r.setTags("");
        ReceiptResponse resp = new ReceiptResponse(r);
        assertThat(validator.validate(resp), empty());
    }

    @Test
    public void testWithTags() {
        ReceiptsRecord r = new ReceiptsRecord();
        r.setId(1);
        r.setAmount(BigDecimal.ONE);
        r.setTags("TEST");
        ReceiptResponse resp = new ReceiptResponse(r);
        assertThat(validator.validate(resp), empty());
    }

    @Test
    public void testTagUpdate() {
        ReceiptsRecord r = new ReceiptsRecord();
        r.setId(1);
        r.setAmount(BigDecimal.ONE);
        r.setTags("TEST");
        ReceiptResponse resp = new ReceiptResponse(r);
        r.setTags("TEST TEST2");
        ReceiptResponse resp2 = new ReceiptResponse(r);
        assertThat(validator.validate(resp), empty());
        assertThat(validator.validate(resp2), empty());
    }
}