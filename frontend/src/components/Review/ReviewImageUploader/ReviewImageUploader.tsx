import { Button, Heading } from '@fun-eat/design-system';
import React, { useRef, useState } from 'react';
import styled from 'styled-components';

const ReviewImageUploader = () => {
  const imageUploadInputRef = useRef<HTMLInputElement>(null);
  return (
    <ReviewImageUploaderContainer>
      <UploadText as="h2">사진이 있다면 올려주세요.</UploadText>
      <ImageUploadLabel htmlFor="review-image">+</ImageUploadLabel>
      <input id="review-image" type="file" accept="image/*" ref={imageUploadInputRef} style={{ display: 'none' }} />
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
