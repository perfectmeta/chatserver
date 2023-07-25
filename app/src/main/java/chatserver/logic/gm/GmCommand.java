package chatserver.logic.gm;

import chatserver.gen.GMCommand;
import chatserver.gen.GMResponse;
import chatserver.service.MessageService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class GmCommand {
    final MessageService service;

    public GmCommand(MessageService service) {
        this.service = service;
    }

    public void run(GMCommand command, StreamObserver<GMResponse> responseStreamObserver) {
        if (command.getCommand().equals("clean_history")) {
            cleanHistory(command, responseStreamObserver);
        } else {
            invalidCommand(command, responseStreamObserver);
        }
    }

    private void cleanHistory(GMCommand command, StreamObserver<GMResponse> responseStreamObserver) {
        if (command.getParametersCount() != 1) {
            responseStreamObserver.onNext(GMResponse.newBuilder()
                    .setInfo("need and only need 1 parameter but given %d".formatted(command.getParametersCount()))
                    .build());
            responseStreamObserver.onCompleted();
            return;
        }
        String roomId = command.getParameters(0);
        try {
            service.deleteMessageByRoomId(Long.parseLong(roomId));
        } catch (NumberFormatException e) {
            responseStreamObserver.onNext(GMResponse.newBuilder()
                    .setInfo("need a number parameter, but got %s".formatted(roomId))
                    .build());
            responseStreamObserver.onCompleted();
            return;
        }

        responseStreamObserver.onNext(GMResponse.newBuilder()
                .setInfo("success")
                .build());
        responseStreamObserver.onCompleted();
    }

    private void invalidCommand(GMCommand command, StreamObserver<GMResponse> responseStreamObserver) {
        responseStreamObserver.onNext(GMResponse.newBuilder()
                .setInfo("invalid command %s".formatted(command.getCommand()))
                .build());
        responseStreamObserver.onCompleted();
    }
}
