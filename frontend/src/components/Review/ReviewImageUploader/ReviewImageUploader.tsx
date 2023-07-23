import { Button, Heading } from '@fun-eat/design-system';
import React, { useRef, useState } from 'react';
import styled from 'styled-components';

const ReviewImageUploader = () => {
  const imageUploadInputRef = useRef<HTMLInputElement>(null);
  const [reviewImage, setReviewImage] = useState('');

  const uploadReviewImage = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (!imageUploadInputRef.current) return;
    imageUploadInputRef.current.click();
    if (!event.target.files) return;
    setReviewImage(URL.createObjectURL(event.target.files[0]));
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewImage);
    setReviewImage('');
  };

  return (
    <ReviewImageUploaderContainer>
      <UploadText as="h2">사진이 있다면 올려주세요.</UploadText>
      {reviewImage ? (
        <ReviewImageButtonWrapper>
          <img src={reviewImage} />
          <Button variant="filled" color="primary" size="sm" onClick={deleteReviewImage}>
            삭제하기
          </Button>
        </ReviewImageButtonWrapper>
      ) : (
        <>
          <ImageUploadLabel htmlFor="review-image">+</ImageUploadLabel>
          <input
            id="review-image"
            type="file"
            accept="image/*"
            ref={imageUploadInputRef}
            onChange={uploadReviewImage}
            style={{ display: 'none' }}
          />
        </>
      )}
    </ReviewImageUploaderContainer>
  );
};

export default ReviewImageUploader;

const ReviewImageUploaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;

const UploadText = styled(Heading)`
  font-size: 1.8rem;
`;

const ImageUploadLabel = styled.label`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 92px;
  height: 95px;
  background: ${({ theme }) => theme.colors.gray1};
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: ${({ theme }) => theme.borderRadius.xs};
  cursor: pointer;
`;

const ReviewImageButtonWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;
