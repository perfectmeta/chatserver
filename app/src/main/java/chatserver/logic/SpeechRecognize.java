package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class SpeechRecognize {
    public StreamObserver<AudioStream> run(StreamObserver<TextStream> responseObserver) {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=");
        return null;
    }
}
