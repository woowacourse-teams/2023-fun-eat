import { Button, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

interface ReviewImageUploaderProps {
  reviewImage: string;
  uploadReviewImage: ChangeEventHandler<HTMLInputElement>;
  uploadReviewImageFile: ChangeEventHandler<HTMLInputElement>;
  deleteReviewImage: () => void;
}

const ReviewImageUploader = ({
  reviewImage,
  uploadReviewImage,
  uploadReviewImageFile,
  deleteReviewImage,
}: ReviewImageUploaderProps) => {
  const theme = useTheme();

  const handleReviewImageUpload: ChangeEventHandler<HTMLInputElement> = (event) => {
    uploadReviewImage(event);
    uploadReviewImageFile(event);
  };

  return (
    <ReviewImageUploaderContainer>
      <Heading as="h2" size="xl">
        구매한 상품 사진이 있다면 올려주세요.
      </Heading>
      <Spacing size={2} />
      <Text color={theme.textColors.disabled}>(사진은 1장까지 업로드 할 수 있어요)</Text>
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
        <ImageUploadLabel>
          +
          <input type="file" accept="image/*" onChange={handleReviewImageUpload} />
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
