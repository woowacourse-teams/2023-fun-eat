import type { ChangeEventHandler } from 'react';
import { useState } from 'react';

const useReviewImageUploader = () => {
  const [reviewImage, setReviewImage] = useState('');
  const [reviewImageFile, setReviewImageFile] = useState<File | null>(null);

  const uploadReviewImage: ChangeEventHandler<HTMLInputElement> = (event) => {
    if (!event.target.files) {
      return;
    }
    setReviewImage(URL.createObjectURL(event.target.files[0]));
  };

  const uploadReviewImageFile: ChangeEventHandler<HTMLInputElement> = (event) => {
    if (!event.target.files) {
      return;
    }
    setReviewImageFile(event.target.files[0]);
  };

  const deleteReviewImage = () => {
    URL.revokeObjectURL(reviewImage);
    setReviewImage('');
  };

  return { reviewImage, reviewImageFile, uploadReviewImage, uploadReviewImageFile, deleteReviewImage };
};

export default useReviewImageUploader;
