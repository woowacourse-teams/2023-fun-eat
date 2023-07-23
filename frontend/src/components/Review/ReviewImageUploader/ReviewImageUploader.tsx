import { Button, Heading, Spacing, Text } from '@fun-eat/design-system';
import React, { useState } from 'react';
import styled, { useTheme } from 'styled-components';

const ReviewImageUploader = () => {
  const [reviewImage, setReviewImage] = useState('');
  const theme = useTheme();

  const uploadReviewImage = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files) return;
    setReviewImage(URL.createObjectURL(event.target.files[0]));
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewImage);
    setReviewImage('');
  };

  return (
    <ReviewImageUploaderContainer>
      <UploadText as="h2">구매한 상품 사진이 있다면 올려주세요.</UploadText>
      <Text size="sm" color={theme.textColors.disabled}>
        사진은 1장까지 업로드 가능합니다.
      </Text>
      <Spacing size={20} />
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
