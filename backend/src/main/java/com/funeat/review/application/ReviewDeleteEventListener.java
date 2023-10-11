package com.funeat.review.application;

import com.funeat.common.ImageUploader;
import com.funeat.common.exception.CommonException.S3DeleteFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ReviewDeleteEventListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ImageUploader imageUploader;

    public ReviewDeleteEventListener(final ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteReviewImageInS3(ReviewDeleteEvent event) {
        String image = event.getImage();
        try {
            imageUploader.delete(image);
        } catch (S3DeleteFailException e) {
            log.warn("S3 이미지 삭제가 실패했습니다. 이미지 경로 : {}", image); // TODO : 이미지 삭제 실패시 처리?
        }
    }
}
