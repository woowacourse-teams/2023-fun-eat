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

    const imageFile = event.target.files[0];

    if (imageFile.size > MAX_SIZE) {
      alert('이미지 크기가 너무 커요. 1MB 이하의 이미지를 골라주세요.');
      event.target.value = '';
      return;
    }

    setReviewPreviewImage(URL.createObjectURL(imageFile));
    setReviewImageFile(imageFile);
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewPreviewImage);
    setReviewPreviewImage('');
  };

  return { reviewPreviewImage, setReviewPreviewImage, reviewImageFile, uploadReviewImage, deleteReviewImage };
};

export default useReviewImageUploader;
