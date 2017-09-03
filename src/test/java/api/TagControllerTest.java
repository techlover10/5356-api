package api;


import com.sun.org.apache.regexp.internal.RE;
import controllers.ReceiptController;
import controllers.TagController;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;
import static org.mockito.Mockito.*;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class TagControllerTest {

    @Test
    public void testReceiptCreate() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        int testLog = rTest.createReceipt(testRequest);

    }

    @Test
    public void testReceiptAddTag() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        TagController tTest = new TagController(daoTest);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        int testLog = rTest.createReceipt(testRequest);
        tTest.toggleTag("testTag", testLog);
        List<ReceiptResponse> tagList = tTest.getTag("testTag");
        List<ReceiptResponse> allList = rTest.getReceipts();
        assert(allList.equals(tagList));
    }

    @Test
    public void testReceiptMultipleTag() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        TagController tTest = new TagController(daoTest);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        int testLog = rTest.createReceipt(testRequest);
        tTest.toggleTag("testTag", testLog);
        tTest.toggleTag("testTag2", testLog);
        List<ReceiptResponse> tagList = tTest.getTag("testTag");
        List<ReceiptResponse> tagList2 = tTest.getTag("testTag2");
        List<ReceiptResponse> allList = rTest.getReceipts();
        assert(allList.equals(tagList));
        assert(allList.equals(tagList2));
    }

    @Test
    public void testRemoveTag() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        TagController tTest = new TagController(daoTest);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        int testLog = rTest.createReceipt(testRequest);
        tTest.toggleTag("testTag", testLog);
        tTest.toggleTag("testTag2", testLog);
        List<ReceiptResponse> tagList = tTest.getTag("testTag");
        tTest.toggleTag("testTag2", testLog);
        List<ReceiptResponse> tagList2 = tTest.getTag("testTag2");
        List<ReceiptResponse> allList = rTest.getReceipts();
        assert(allList.equals(tagList));
        assert(tagList2.isEmpty());
    }
}