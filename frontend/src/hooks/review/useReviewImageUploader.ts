import type { ChangeEventHandler } from 'react';
import { useState } from 'react';

const useReviewImageUploader = () => {
  const [reviewImage, setReviewImage] = useState('');

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

  return { reviewImage, uploadReviewImage, deleteReviewImage };
};

export default useReviewImageUploader;
