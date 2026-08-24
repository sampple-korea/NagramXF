package tw.nekomimi.nekogram.helpers;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_iv;

import java.util.ArrayList;

public class TranslationEntityHandlingTest {

    @Test
    public void nullEntitiesProduceNoStyleRuns() {
        assertTrue(MediaDataController.getTextStyleRuns(null, "message", -1).isEmpty());
    }

    @Test
    public void translatedRichMessageKeepsEntitiesForOriginalText() {
        String originalText = "original";
        TLRPC.TL_message message = new TLRPC.TL_message();
        message.message = originalText;
        message.entities = new ArrayList<>();
        message.entities.add(new TLRPC.TL_messageEntityBold());
        message.translatedRichMessage = new TL_iv.RichMessage();

        MessageObject messageObject = new MessageObject(
            0,
            message,
            originalText,
            null,
            null,
            false,
            false,
            false,
            false
        );
        messageObject.translated = true;

        assertSame(message.entities, MessageHelper.getEntitiesForText(messageObject, originalText, false));
    }
}
