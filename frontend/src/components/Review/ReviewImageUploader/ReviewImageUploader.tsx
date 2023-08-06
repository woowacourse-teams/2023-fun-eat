import { Button, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import useEnterKeyDown from '@/hooks/useEnterKeyDown';

interface ReviewImageUploaderProps {
  reviewPreviewImage: string;
  uploadReviewImage: ChangeEventHandler<HTMLInputElement>;
  deleteReviewImage: () => void;
}

const ReviewImageUploader = ({
  reviewPreviewImage,
  uploadReviewImage,
  deleteReviewImage,
}: ReviewImageUploaderProps) => {
  const { inputRef, handleKeydown } = useEnterKeyDown();
  const theme = useTheme();

  return (
    <ReviewImageUploaderContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        구매한 상품 사진이 있다면 올려주세요.
      </Heading>
      <Spacing size={2} />
      <Text color={theme.textColors.disabled} tabIndex={0}>
        (사진은 1MB 이하, 1장까지 업로드 할 수 있어요.)
      </Text>
      <Spacing size={20} />
      {reviewPreviewImage ? (
        <ReviewImageButtonWrapper>
          <img src={reviewPreviewImage} alt="업로드한 리뷰 사진" width={200} />
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
        <ImageUploadLabel tabIndex={0} onKeyDown={handleKeydown} aria-label="사진 업로드 버튼" aria-hidden>
          +
          <input ref={inputRef} type="file" accept="image/*" onChange={uploadReviewImage} />
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
