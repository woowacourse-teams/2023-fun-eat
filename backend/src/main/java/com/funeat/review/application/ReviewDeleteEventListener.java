package com.funeat.review.application;

import com.funeat.common.ImageUploader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReviewDeleteEventListener {

    private final ImageUploader imageUploader;

    public ReviewDeleteEventListener(final ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteReviewImageInS3(ReviewDeleteEvent event) {
        String image = event.getImage();
        if (image != null) {
            imageUploader.delete(image);
        }
    }
}
