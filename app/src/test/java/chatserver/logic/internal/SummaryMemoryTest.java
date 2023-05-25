package chatserver.logic.internal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class SummaryMemoryTest {
    private static final Logger logger = Logger.getLogger(SummaryMemoryTest.class.getName());
    @Test
    public void summaryMemoryTest() throws Exception {
        SummaryMemory summaryMemory = new SummaryMemory();
        String preSummary = "lisa是小明的英语老师，小明上了lisa的8节英语课，他们的下一次课是下周一。";
        List<String> messages = Arrays.asList(
                "小明:你好，lisa老师，我的妈妈下周一要带我去泰山旅游，我们下周一的英语课我可能无法参加了",
                "lisa:Great! 听起来真让人激动，祝你玩的愉快！ 那么我们下周一的英语课改为什么时间上呢？",
                "小明:我大概周三会旅游回来，然后我需要休息一天，我们改为周五怎么样？",
                "lisa:周五可能不行，周五我得带小花去公园，顺便提一句，小花是我养的一条小狗。",
                "小明:好吧，那既然这样的话，我周四晚上怎么样？",
                "lisa:好的，就周四晚上"
        );
        var newSummary = summaryMemory.run(messages, preSummary);
        logger.info("New Summary: " + newSummary);
    }
}
