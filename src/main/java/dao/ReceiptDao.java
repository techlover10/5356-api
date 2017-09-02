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

    public boolean containsTag(int rid, String tag){
        ReceiptsRecord r = getRecord(rid);
        String tagString = r.getTags();
        ArrayList<String> tagSet = new ArrayList<>(Arrays.asList(tagString.split(" ")));
        return tagSet.contains(tag);
    }

    public void toggleTag(int rid, String tag){
        if (containsTag(rid, tag)){
            removeTag(rid, tag);
            return;
        }
        addTag(rid, tag);


    }

    private void addTag (int rid, String newTag){
        String prevString = getRecord(rid).getTags();
        if (!prevString.equals("")){
            updateTags(rid, prevString + ' ' + newTag);
            return;
        }
        updateTags(rid, newTag);

    }

    private void removeTag (int rid, String oldTag){
        String prevString = getRecord(rid).getTags();
        ArrayList<String> tagSet = new ArrayList<>(Arrays.asList(prevString.split(" ")));
        tagSet.remove(oldTag);
        String tagList = "";
        for (int i = 0; i < tagSet.size()-1; i++){
            tagList += tagSet.get(i) + " ";
        }
        if (tagSet.size() > 0){
           tagList += tagSet.get(tagSet.size() - 1);
        }

        updateTags(rid, tagList);

    }

    private void updateTags (int rid, String newString){
        // Update the tag with the new tag
        //System.out.println("before");
        //System.out.println(getRecord(rid).toString());
        dsl.update(RECEIPTS)
                .set(RECEIPTS.TAGS, newString)
                .where(RECEIPTS.ID.equal(rid))
                .execute();
        //System.out.println("after");
        //System.out.println(getRecord(rid).toString());

    }


    public List<ReceiptsRecord> getAllReceipts() {
        return dsl.selectFrom(RECEIPTS).fetch();
    }

    public List<ReceiptsRecord> getByTag(String tag){
        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.TAGS.contains(tag)).fetch();
    }
}
