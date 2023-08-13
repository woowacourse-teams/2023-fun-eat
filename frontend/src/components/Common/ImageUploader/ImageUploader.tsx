import { Button } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import { useEnterKeyDown } from '@/hooks/common';

interface ReviewImageUploaderProps {
  previewImage: string;
  uploadImage: ChangeEventHandler<HTMLInputElement>;
  deleteImage: () => void;
}

const ImageUploader = ({ previewImage, uploadImage, deleteImage }: ReviewImageUploaderProps) => {
  const { inputRef, handleKeydown } = useEnterKeyDown();

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
          <input ref={inputRef} type="file" accept="image/*" onChange={uploadImage} />
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