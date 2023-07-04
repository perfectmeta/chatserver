package chatserver.third.asr;

import chatserver.gen.TextStream;
import chatserver.third.asr.entity.stream.res.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Logger;

// 这个类，先简单实现下，以后再优化性能 !!!
public class Decoder {
    private final List<Text> resultList;
    private final Queue<Object> textStreams;
    private static final Logger logger = Logger.getLogger(Decoder.class.getName());

    public Decoder(Queue<Object> textStreams) {
        this.textStreams = textStreams;
        resultList = new ArrayList<>();
    }

    public void decode(Result result) {
        var text = toString(result);
        int removeNum = 0;
        if (Objects.equals(result.pgs(), "rpl")) {
            logger.info("Decoder rpl " + result.pgs());
            var start = result.rg().get(0);
            var end = result.rg().get(1);
            for (int i = start; i <= end; i++) {
                var t = resultList.get(i - 1);
                if (t.delete) {
                    continue;
                }
                removeNum += t.text.length();
                t.delete = true;
            }
            if (removeNum > 0) {
                textStreams.add(TextStream.newBuilder().setDelete(removeNum).build());
            }
            resultList.add(new Text(text));
            textStreams.add(TextStream.newBuilder().setText(text).build());
        } else {
            logger.info("Decoder no rpl " + result.pgs());
            resultList.add(new Text(text));
            textStreams.add(TextStream.newBuilder().setText(text).build());
        }
    }

    private String toString(Result result) {
        StringBuilder sb = new StringBuilder();
        for (var ws : result.ws()) {
            for (var cw : ws.cw()) {
                sb.append(cw.w());
            }
        }

        return sb.toString();
    }

    private static class Text {
        public Text(String text) {
            this.text = text;
        }

        public String text;

        boolean delete;
    }
}
