import { Button, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import { useState } from 'react';
import styled from 'styled-components';

const ReviewImageUploader = () => {
  const [reviewImage, setReviewImage] = useState('');
  const theme = useTheme();

  const uploadReviewImage: ChangeEventHandler<HTMLInputElement> = (event) => {
    if (!event.target.files) {
      return;
    }
    setReviewImage(URL.createObjectURL(event.target.files[0]));
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewImage);
    setReviewImage('');
  };

  return (
    <ReviewImageUploaderContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        구매한 상품 사진이 있다면 올려주세요.
      </Heading>
      <Spacing size={2} />
      <Text color={theme.textColors.disabled} tabIndex={0}>
        (사진은 1장까지 업로드 할 수 있어요)
      </Text>
      <Spacing size={20} />
      {reviewImage ? (
        <ReviewImageButtonWrapper>
          <img src={reviewImage} alt="업로드한 리뷰 사진" width={200} />
          <Button
            type="button"
            customWidth="80px"
            color="primary"
            weight="bold"
            variant="filled"
            onClick={deleteReviewImage}
          >
            삭제하기
          </Button>
        </ReviewImageButtonWrapper>
      ) : (
        <ImageUploadLabel tabIndex={0} aria-label="사진 업로드 버튼">
          +
          <input type="file" accept="image/*" onChange={uploadReviewImage} />
        </ImageUploadLabel>
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

  & > input {
    display: none;
  }
`;

const ReviewImageButtonWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;
