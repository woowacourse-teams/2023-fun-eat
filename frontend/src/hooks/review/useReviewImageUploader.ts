import type { ChangeEventHandler } from 'react';
import { useState } from 'react';

const MAX_SIZE = 1024 * 1024;

const useReviewImageUploader = () => {
  const [reviewPreviewImage, setReviewPreviewImage] = useState('');
  const [reviewImageFile, setReviewImageFile] = useState<File | null>(null);

  const uploadReviewImage: ChangeEventHandler<HTMLInputElement> = (event) => {
    if (!event.target.files) {
      return;
    }

    if (event.target.files[0].size > MAX_SIZE) {
      alert('이미지 크기가 너무 큽니다. 1MB 이하의 이미지를 선택하세요.');
      event.target.value = '';
      return;
    }

    setReviewPreviewImage(URL.createObjectURL(event.target.files[0]));
    setReviewImageFile(event.target.files[0]);
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewPreviewImage);
    setReviewPreviewImage('');
  };

  return { reviewPreviewImage, setReviewPreviewImage, reviewImageFile, uploadReviewImage, deleteReviewImage };
};

export default useReviewImageUploader;
