package dao;

import api.ReceiptResponse;
import generated.tables.Receipts;
import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;

public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT, RECEIPTS.TAGS)
                .values(merchantName, amount, "")
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public ReceiptsRecord getRecord(int rid){

        ReceiptsRecord receiptsRecord = dsl
                .selectFrom(RECEIPTS)
                .where(RECEIPTS.ID.equal(rid))
                .fetchOne();

        return receiptsRecord;

    }

    private boolean containsTag(ReceiptsRecord r, String tag){
        String tagString = r.getTags();
        ArrayList<String> tagSet = new ArrayList<>(Arrays.asList(tagString.split(" ")));
        return tagSet.contains(tag);
    }

    public void toggleTag(int rid, String tag){
        ReceiptsRecord r = getRecord(rid);
        if (containsTag(r, tag)){
            removeTag(r, tag);
            return;
        }
        addTag(r, tag);


    }

    private void addTag (ReceiptsRecord r, String newTag){
        String prevString = r.getTags();
        if (!prevString.equals("")){
            updateTags(r, prevString + ' ' + newTag);
            return;
        }
        updateTags(r, newTag);

    }

    private void removeTag (ReceiptsRecord r, String oldTag){
        String prevString = r.getTags();
        ArrayList<String> tagSet = new ArrayList<>(Arrays.asList(prevString.split(" ")));
        tagSet.remove(oldTag);
        String tagList = "";
        for (int i = 0; i < tagSet.size()-1; i++){
            tagList += tagSet.get(i) + " ";
        }
        if (tagSet.size() > 0){
           tagList += tagSet.get(tagSet.size() - 1);
        }

        updateTags(r, tagList);

    }

    private void updateTags (ReceiptsRecord r, String newString){
        // Update the tag with the new tag
        int rid = r.getId();
        dsl.update(RECEIPTS)
                .set(RECEIPTS.TAGS, newString)
                .where(RECEIPTS.ID.equal(rid))
                .execute();

    }


    public List<ReceiptsRecord> getAllReceipts() {
        return dsl.selectFrom(RECEIPTS).fetch();
    }

    public List<ReceiptsRecord> getByTag(String tag){
        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.TAGS.contains(tag)).fetch();
    }
}
