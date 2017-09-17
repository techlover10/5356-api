package api;


import controllers.ReceiptController;
import controllers.TagController;
import dao.ReceiptDao;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;


public class TagControllerTest {

    @Test
    public void testReceiptAddTag() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        TagController tTest = new TagController(daoTest);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        String testLogRaw = rTest.createReceipt(testRequest).split(";")[0];
        int testLog = Integer.parseInt(testLogRaw);
        tTest.toggleTag("testTag", testLog);
        verify(daoTest).toggleTag(testLog,"testTag");
    }

    @Test
    public void testReceiptMultipleTag() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        TagController tTest = new TagController(daoTest);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        String testLogRaw = rTest.createReceipt(testRequest).split(";")[0];
        int testLog = Integer.parseInt(testLogRaw);
        tTest.toggleTag("testTag", testLog);
        tTest.toggleTag("testTag2", testLog);

        verify(daoTest).toggleTag(testLog,"testTag");
        verify(daoTest).toggleTag(testLog,"testTag2");
    }

    @Test
    public void testRemoveTag() {
        ReceiptDao daoTest = mock(ReceiptDao.class);
        TagController tTest = new TagController(daoTest);
        ReceiptController rTest = new ReceiptController(daoTest);
        CreateReceiptRequest testRequest = new CreateReceiptRequest();
        testRequest.amount = BigDecimal.ONE;
        testRequest.merchant = "testMerchant";

        String testLogRaw = rTest.createReceipt(testRequest).split(";")[0];
        int testLog = Integer.parseInt(testLogRaw);
        tTest.toggleTag("testTag", testLog);
        tTest.toggleTag("testTag2", testLog);
        tTest.toggleTag("testTag2", testLog);
        verify(daoTest, times(2)).toggleTag(testLog, "testTag2");
    }
}
