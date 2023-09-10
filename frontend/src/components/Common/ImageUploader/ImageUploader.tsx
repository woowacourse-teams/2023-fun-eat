import { Button } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import { useEnterKeyDown } from '@/hooks/common';

const MAX_SIZE = 5 * 1024 * 1024;

interface ReviewImageUploaderProps {
  previewImage: string;
  uploadImage: (imageFile: File) => void;
  deleteImage: () => void;
}

const ImageUploader = ({ previewImage, uploadImage, deleteImage }: ReviewImageUploaderProps) => {
  const { inputRef, handleKeydown } = useEnterKeyDown();

  const handleImageUpload: ChangeEventHandler<HTMLInputElement> = (event) => {
    if (!event.target.files) {
      return;
    }

    const imageFile = event.target.files[0];

    if (imageFile.size > MAX_SIZE) {
      alert('이미지 크기가 너무 커요. 5MB 이하의 이미지를 골라주세요.');
      event.target.value = '';
      return;
    }

    uploadImage(imageFile);
  };

  return (
    <>
      {previewImage ? (
        <PreviewImageWrapper>
          <img src={previewImage} alt="업로드한 사진" width={200} />
          <Button type="button" customWidth="80px" color="primary" weight="bold" variant="filled" onClick={deleteImage}>
            삭제하기
          </Button>
        </PreviewImageWrapper>
      ) : (
        <ImageUploadLabel tabIndex={0} onKeyDown={handleKeydown} aria-label="사진 업로드 버튼" aria-hidden>
          +
          <input ref={inputRef} type="file" accept="image/*" onChange={handleImageUpload} />
        </ImageUploadLabel>
      )}
    </>
  );
};

export default ImageUploader;

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

const PreviewImageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;
